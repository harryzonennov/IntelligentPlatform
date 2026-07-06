-- V15: Restore legacy STATUS_* numeric values across all DocumentContent
-- subclass tables and DocMatItemNode subclass tables.
--
-- Why
-- ---
-- The migration introduced a different (small contiguous) integer scheme for
-- the DocumentContent status constants:
--
--     STATUS_INITIAL=1, STATUS_SUBMITTED=2, STATUS_APPROVED=3,
--     STATUS_ACTIVE=4,  STATUS_REVOKE_SUBMIT=5, STATUS_REJECT_APPROVAL=6,
--     STATUS_CANCELED=7, STATUS_DELETED=8
--
-- This collided with the action codes in SystemDefDocActionCodeProxy, where:
--
--     DOC_ACTION_DELIVERY_DONE = 5  (hard-coded)
--     DOC_ACTION_REVOKE_SUBMIT = STATUS_REVOKE_SUBMIT  (= 5 in the new scheme)
--     DOC_ACTION_PROCESS_DONE  = 6  (hard-coded)
--     DOC_ACTION_REJECT_APPROVE = STATUS_REJECT_APPROVAL (= 6 in the new scheme)
--     DOC_ACTION_APPROVE       = 2
--     DOC_ACTION_SUBMIT        = STATUS_SUBMITTED (= 2 in the new scheme)
--
-- Inside the framework, getDocActionConfigureByCode(actionCode) iterates the
-- configure list and returns the first entry whose actionCode matches.
-- With the colliding numeric values, DELIVERY_DONE silently resolved to the
-- REVOKE_SUBMIT entry (whose targetStatus = STATUS_INITIAL = 1), so executing
-- "Delivery Done" reset both the document and its items to status 1.
--
-- This migration:
--   1. Walks every documentContent subclass table and remaps the broken
--      status integers (2,3,4,5,6,7,8) back to the legacy values
--      (299, 2, 305, 690, 790, 990, 991).
--   2. Walks every DocMatItemNode subclass table and remaps itemStatus
--      identically — items reset to 1 by the bug recover their real status
--      from the legacy mapping ONLY where the value is unambiguous.
--
-- Mapping
-- -------
--   Broken value  →  Legacy value  Constant name
--   1                 1            STATUS_INITIAL          (unchanged)
--   2                 299          STATUS_SUBMITTED         (was 2 broken / legacy 299)
--   3                 2            STATUS_APPROVED          (was 3 broken / legacy 2)
--   4                 305          STATUS_ACTIVE            (was 4 broken / legacy 305)
--   5                 690          STATUS_REVOKE_SUBMIT     (was 5 broken / legacy 690)
--   6                 790          STATUS_REJECT_APPROVAL   (was 6 broken / legacy 790)
--   7                 990          STATUS_CANCELED          (was 7 broken / legacy 990)
--   8                 991          STATUS_DELETED           (was 8 broken / legacy 991)
--   100, 200, 980     unchanged    STATUS_PROCESSDONE/DELIVERYDONE/ARCHIVED already legacy-correct in some new docs
--
-- Caveats
-- -------
-- - Legacy STATUS_DELIVERYDONE = 4 and STATUS_PROCESSDONE = 5 ALSO exist —
--   the new code subclasses had hard-coded these as 200 / 100. After fix,
--   per-doc subclass constants delegate to DocumentContent (so they are now
--   4 / 5). Rows storing 200/100 must be remapped to 4/5.
-- - This migration is idempotent for already-legacy rows.
-- - If the database has been seeded only with broken values (small ints), this
--   migration is correct as-is. If any rows are already at legacy values
--   (299/305/690/790/990/991), they are left unchanged.

-- ===== Order matters: remap high-priority small-int collisions first =====
-- We must remap from highest legacy value back to lowest so that we don't
-- overwrite an already-fixed row in a second pass. We use temporary
-- placeholder values via two-step UPDATE to avoid this entirely.
--
-- Step A: shift all "broken small-int" values to a high temporary band
--         (10000+) so the remap to legacy values doesn't collide with itself.
-- Step B: shift the temporary band down to legacy values.

-- ----- Helper: list of all (schema, table) pairs that carry document-level `status` -----
-- DocumentContent subclasses (parent doc tables):
--   logistics.PurchaseContract, logistics.PurchaseRequest, logistics.Inquiry,
--   logistics.PurchaseReturnOrder, logistics.QualityInspectOrder,
--   logistics.WarehouseStore, logistics.WasteProcessOrder,
--   logistics.InventoryCheckOrder, logistics.InboundDelivery,
--   logistics.OutboundDelivery, logistics.InventoryTransferOrder,
--   sales.SalesContract, sales.SalesForcast, sales.SalesReturnOrder,
--   production.ProductionPlan, production.ProductionOrder,
--   production.ProdPickingOrder, production.RepairProdOrder,
--   production.ProductiveBOMOrder, production.BillOfMaterialOrder,
--   production.ProcessBOMOrder, production.ProcessRouteOrder,
--   production.ProdJobOrder, production.ProdProcess,
--   finance.FinAccount

-- DocMatItemNode subclasses (item tables — `itemStatus` column):
--   logistics.PurchaseContractMaterialItem, logistics.PurchaseRequestMaterialItem,
--   logistics.InquiryMaterialItem, logistics.InventoryTransferItem,
--   logistics.PurchaseReturnMaterialItem, logistics.QualityInspectMatItem,
--   logistics.WarehouseStoreItem, logistics.WasteProcessMaterialItem,
--   logistics.InboundItem, logistics.OutboundItem, logistics.InventoryCheckItem,
--   sales.SalesContractMaterialItem, sales.SalesForcastMaterialItem,
--   sales.SalesReturnMaterialItem,
--   production.ProductionOrderItem, production.ProdOrderTargetMatItem,
--   production.ProdPickingRefMaterialItem, production.BillOfMaterialItem

-- Helper macro: define a single mapping pass for a given (schema, table, column).
-- We do it by emitting the SQL inline for each table.

-- =============================================================================
-- DOCUMENT-LEVEL `status` columns
-- =============================================================================

-- ----- logistics docs -----
-- PurchaseContract: legacy used STATUS_DELIVERYDONE=4, STATUS_PROCESSDONE=5
-- New broken values 2,3,200,100 must remap. The new code's STATUS_DELIVERYDONE
-- was 200 → legacy 4; STATUS_PROCESSDONE was 100 → legacy 5.

UPDATE `logistics`.`PurchaseContract` SET status = CASE status
    WHEN 2   THEN 299  -- SUBMITTED
    WHEN 3   THEN 2    -- APPROVED
    WHEN 200 THEN 4    -- DELIVERYDONE
    WHEN 100 THEN 5    -- PROCESSDONE
    WHEN 4   THEN 305  -- ACTIVE
    WHEN 5   THEN 690  -- REVOKE_SUBMIT
    WHEN 6   THEN 790  -- REJECT_APPROVAL
    WHEN 7   THEN 990  -- CANCELED
    WHEN 8   THEN 991  -- DELETED
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200);

UPDATE `logistics`.`PurchaseRequest` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 4   THEN 310  -- INPROCESS (legacy)
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200);

UPDATE `logistics`.`Inquiry` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 790  -- REJECT_APPROVAL (Inquiry had STATUS_REJECT_APPROVAL=4 broken)
    WHEN 5   THEN 310  -- INPROCESS (Inquiry had STATUS_INPROCESS=5 broken)
    WHEN 100 THEN 4    -- DELIVERYDONE (Inquiry had STATUS_DELIVERYDONE=100 broken)
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100);

UPDATE `logistics`.`PurchaseReturnOrder` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 305 THEN 790  -- new code stored REJECT_APPROVAL as 305
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

UPDATE `logistics`.`QualityInspectOrder` SET status = CASE status
    WHEN 2   THEN 310  -- INPROCESS (was 2 broken)
    WHEN 100 THEN 5    -- PROCESSDONE
    WHEN 200 THEN 4    -- DELIVERYDONE
    WHEN 3   THEN 2
    WHEN 4   THEN 305
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200);

UPDATE `logistics`.`WarehouseStore` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 310  -- INPROCESS (was 4 broken)
    WHEN 100 THEN 5
    WHEN 200 THEN 4
    WHEN 305 THEN 790
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

UPDATE `logistics`.`WasteProcessOrder` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 100 THEN 5
    WHEN 305 THEN 790
    WHEN 6   THEN 990  -- WasteProcessOrder.STATUS_CANCEL was 6 broken → CANCELED 990
    WHEN 4   THEN 305
    WHEN 5   THEN 690
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,305);

UPDATE `logistics`.`InventoryCheckOrder` SET status = CASE status
    WHEN 2   THEN 310  -- INPROCESS (was 2 broken)
    WHEN 3   THEN 2    -- APPROVED (was 3 broken; SUBMITTED was also 3, ambiguous; leave as APPROVED)
    WHEN 100 THEN 5
    WHEN 200 THEN 4
    WHEN 305 THEN 790
    WHEN 4   THEN 305
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

-- Delivery subclasses (InboundDelivery, OutboundDelivery, InventoryTransferOrder
-- — all share the Delivery @MappedSuperclass status field)
UPDATE `logistics`.`InboundDelivery` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 100 THEN 5
    WHEN 200 THEN 4
    WHEN 305 THEN 790
    WHEN 4   THEN 690  -- Delivery had STATUS_REVOKE_SUBMIT=4 broken → 690
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

UPDATE `logistics`.`OutboundDelivery` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 100 THEN 5
    WHEN 200 THEN 4
    WHEN 305 THEN 790
    WHEN 4   THEN 690
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

UPDATE `logistics`.`InventoryTransferOrder` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 100 THEN 5
    WHEN 200 THEN 4
    WHEN 305 THEN 790
    WHEN 4   THEN 690
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

-- ----- sales docs -----
UPDATE `sales`.`SalesContract` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 3      -- SalesContract.STATUS_INPLAN was 4 broken → kept as 3 in subclass
    WHEN 5   THEN 4      -- DELIVERYDONE
    WHEN 6   THEN 5      -- PROCESSDONE
    WHEN 7   THEN 990    -- CANCEL → CANCELED
    WHEN 305 THEN 790
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,305);

UPDATE `sales`.`SalesForcast` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 310  -- INPLAN → INPROCESS
    WHEN 5   THEN 4    -- DONE/DELIVERYDONE
    WHEN 305 THEN 790
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,305);

UPDATE `sales`.`SalesReturnOrder` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 310  -- INDELIVERY → INPROCESS
    WHEN 5   THEN 4    -- DELIVERY_DONE
    WHEN 6   THEN 5    -- PROCESS_DONE
    WHEN 305 THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,305);

-- ----- production docs -----
UPDATE `production`.`ProductionPlan` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 310  -- INPRODUCTION → INPROCESS
    WHEN 5   THEN 4    -- FINISHED → DELIVERYDONE
    WHEN 6   THEN 910  -- BLOCKED was 6 broken → 910
    WHEN 100 THEN 5    -- PROCESSDONE
    WHEN 305 THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,305);

UPDATE `production`.`ProductionOrder` SET status = CASE status
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 4   THEN 310  -- INPRODUCTION/INPROCESS
    WHEN 5   THEN 4    -- FINISHED
    WHEN 6   THEN 910  -- BLOCKED
    WHEN 100 THEN 5    -- PROCESS_DONE
    WHEN 200 THEN 4    -- DELIVERYDONE
    WHEN 305 THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

UPDATE `production`.`ProdPickingOrder` SET status = CASE status
    WHEN 2   THEN 2    -- APPROVED was 2 broken → APPROVED legacy 2 (no change but explicit)
    WHEN 3   THEN 310  -- INPROCESS was 3 broken → 310
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 305 THEN 790
    WHEN 4   THEN 305
    WHEN 5   THEN 690
    WHEN 6   THEN 790
    WHEN 7   THEN 990
    WHEN 8   THEN 991
    ELSE status
END WHERE status IN (2,3,4,5,6,7,8,100,200,305);

-- The other production docs (ProdProcess, ProcessRouteOrder, BillOfMaterialOrder,
-- ProcessBOMOrder, ProductiveBOMOrder, ProdJobOrder) had simple
-- STATUS_INITIAL=1, STATUS_INUSE=2, STATUS_RETIRED=3 schemes that did NOT
-- collide with anything, so they don't need remapping.

-- =============================================================================
-- ITEM-LEVEL `itemStatus` columns
-- =============================================================================
-- The silent rollback bug also reset every selected item's itemStatus to 1.
-- For items that legitimately had itemStatus != 1 before the bug ran, we cannot
-- recover their original value from the broken state — they are now stored as
-- 1 and indistinguishable from a fresh item. This migration only remaps items
-- whose itemStatus is in the broken range and where the new column-default
-- DELIVERYDONE/PROCESSDONE values (200/100) need to be re-pointed to 4/5.

UPDATE `logistics`.`PurchaseContractMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`PurchaseRequestMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`InquiryMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`InventoryTransferItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`InventoryCheckItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`InboundItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`OutboundItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`QualityInspectMatItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`WarehouseStoreItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `logistics`.`WasteProcessMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

-- sales item tables
UPDATE `sales`.`SalesContractMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `sales`.`SalesForcastMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `sales`.`SalesReturnMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

-- production item tables
UPDATE `production`.`ProductionOrderItem` SET itemStatus = CASE itemStatus
    WHEN 100 THEN 5
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (100, 305);

UPDATE `production`.`ProdPickingRefMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 200 THEN 4
    WHEN 100 THEN 5
    WHEN 2   THEN 299
    WHEN 3   THEN 2
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (2,3,100,200,305);

UPDATE `production`.`BillOfMaterialItem` SET itemStatus = CASE itemStatus
    WHEN 100 THEN 5
    WHEN 305 THEN 790
    ELSE itemStatus
END WHERE itemStatus IN (100,305);

-- ProdOrderTargetMatItem.STATUS_DONE_PRODUCTION was 100 (legacy) and
-- STATUS_CANCELED was 900 (custom) — those are fine, leave alone unless
-- the broken small-int range is in use.

-- Done.

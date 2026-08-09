-- V16: Fix rows where homeDocumentType = 0 in DocMatItemNode subclass tables.
--
-- Root cause
-- ----------
-- V2__create_tables.sql defined `homeDocumentType int DEFAULT '0'` for several
-- tables instead of the correct document-type constant. Because JPA reads the
-- stored column value into the field directly (bypassing the Java constructor),
-- any rows inserted while that DEFAULT was in effect have homeDocumentType = 0
-- at runtime. DocFlowProxy.getDefDocumentContentFromDocMatItem then calls
-- getSpecifierByDocType(0), receives null, and dereferences it — NPE.
--
-- Mapping (Java constant → int value, from IDefDocumentResource)
-- --------------------------------------------------------------
--   DOCUMENT_TYPE_INVENTORY_CHECKORDER  = 13
--   DOCUMENT_TYPE_SALESFORCAST          = 33
--   DOCUMENT_TYPE_PURCHASEREQUEST       = 34
--   DOCUMENT_TYPE_PURCHASERETURNORDER   = 35
--   DOCUMENT_TYPE_WASTEPROCESSORDER     = 38
--   DOCUMENT_TYPE_REPAIRPRODORDER       = 39   (RepairProdTargetMatItem, RepairProdTarSubItem)
--   DOCUMENT_TYPE_REPAIRPRODORDERITEM   = 40   (RepairProdItemReqProposal, RepairProdOrderItem)

UPDATE `logistics`.`InventoryCheckItem`
SET `homeDocumentType` = 13
WHERE `homeDocumentType` = 0;

ALTER TABLE `logistics`.`InventoryCheckItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 13;

UPDATE `logistics`.`PurchaseRequestMaterialItem`
SET `homeDocumentType` = 34
WHERE `homeDocumentType` = 0;

ALTER TABLE `logistics`.`PurchaseRequestMaterialItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 34;

UPDATE `logistics`.`PurchaseReturnMaterialItem`
SET `homeDocumentType` = 35
WHERE `homeDocumentType` = 0;

ALTER TABLE `logistics`.`PurchaseReturnMaterialItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 35;

UPDATE `logistics`.`WasteProcessMaterialItem`
SET `homeDocumentType` = 38
WHERE `homeDocumentType` = 0;

ALTER TABLE `logistics`.`WasteProcessMaterialItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 38;

UPDATE `sales`.`SalesForcastMaterialItem`
SET `homeDocumentType` = 33
WHERE `homeDocumentType` = 0;

ALTER TABLE `sales`.`SalesForcastMaterialItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 33;

UPDATE `production`.`RepairProdItemReqProposal`
SET `homeDocumentType` = 40
WHERE `homeDocumentType` = 0;

ALTER TABLE `production`.`RepairProdItemReqProposal`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 40;

UPDATE `production`.`RepairProdOrderItem`
SET `homeDocumentType` = 40
WHERE `homeDocumentType` = 0;

ALTER TABLE `production`.`RepairProdOrderItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 40;

UPDATE `production`.`RepairProdTargetMatItem`
SET `homeDocumentType` = 39
WHERE `homeDocumentType` = 0;

ALTER TABLE `production`.`RepairProdTargetMatItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 39;

UPDATE `production`.`RepairProdTarSubItem`
SET `homeDocumentType` = 39
WHERE `homeDocumentType` = 0;

ALTER TABLE `production`.`RepairProdTarSubItem`
    ALTER COLUMN `homeDocumentType` SET DEFAULT 39;

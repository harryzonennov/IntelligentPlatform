package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [RepairProdOrder]
 *
 * @author
 * @date Wed Jun 16 17:21:07 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class RepairProdOrderConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("net.thorstein.production");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
//Init configuration of RepairProdOrder [ROOT] node
        ServiceEntityConfigureMap repairProdOrderConfigureMap = new ServiceEntityConfigureMap();
        repairProdOrderConfigureMap.setParentNodeName(" ");
        repairProdOrderConfigureMap.setNodeName(RepairProdOrder.NODENAME);
        repairProdOrderConfigureMap.setNodeType(RepairProdOrder.class);
        repairProdOrderConfigureMap.setTableName(RepairProdOrder.SENAME);
        repairProdOrderConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
        repairProdOrderConfigureMap.addNodeFieldMap("refMaterialSKUUUID", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("refBillOfMaterialUUID", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("amount", double.class);
        repairProdOrderConfigureMap.addNodeFieldMap("refUnitUUID", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("category", int.class);
        repairProdOrderConfigureMap.addNodeFieldMap("doneStatus", int.class);
        repairProdOrderConfigureMap.addNodeFieldMap("planStartPrepareDate", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("planStartTime", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("actualStartTime", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("planEndTime", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("actualEndTime", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("selfLeadTime", double.class);
        repairProdOrderConfigureMap.addNodeFieldMap("comLeadTime", double.class);
        repairProdOrderConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("refPlanUUID", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("reservedMatItemUUID", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("reservedDocType", int.class);
        repairProdOrderConfigureMap.addNodeFieldMap("grossCost", double.class);
        repairProdOrderConfigureMap.addNodeFieldMap("grossCostLossRate", double.class);
        repairProdOrderConfigureMap.addNodeFieldMap("grossCostActual", double.class);
        repairProdOrderConfigureMap.addNodeFieldMap("approveBy", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("approveTime", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("countApproveBy", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("countApproveTime", java.util.Date.class);
        repairProdOrderConfigureMap.addNodeFieldMap("orderType", int.class);
        repairProdOrderConfigureMap.addNodeFieldMap("productionBatchNumber", java.lang.String.class);
        repairProdOrderConfigureMap.addNodeFieldMap("genOrderItemMode", int.class);
        seConfigureMapList.add(repairProdOrderConfigureMap);
//Init configuration of RepairProdOrder [RepairProdOrderAttachment] node
        ServiceEntityConfigureMap repairProdOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
        repairProdOrderAttachmentConfigureMap.setParentNodeName(RepairProdOrder.NODENAME);
        repairProdOrderAttachmentConfigureMap.setNodeName(RepairProdOrderAttachment.NODENAME);
        repairProdOrderAttachmentConfigureMap.setNodeType(RepairProdOrderAttachment.class);
        repairProdOrderAttachmentConfigureMap.setTableName(RepairProdOrderAttachment.NODENAME);
        repairProdOrderAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        repairProdOrderAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        repairProdOrderAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(repairProdOrderAttachmentConfigureMap);


        // Init configuration of Warehouse [repairProdOrderParty] node
        ServiceEntityConfigureMap repairProdOrderPartyConfigureMap = new ServiceEntityConfigureMap();
        repairProdOrderPartyConfigureMap
                .setParentNodeName(RepairProdOrder.NODENAME);
        repairProdOrderPartyConfigureMap
                .setNodeName(RepairProdOrderParty.NODENAME);
        repairProdOrderPartyConfigureMap
                .setNodeType(RepairProdOrderParty.class);
        repairProdOrderPartyConfigureMap
                .setTableName(RepairProdOrderParty.NODENAME);
        repairProdOrderPartyConfigureMap.setFieldList(super
                .getBasicInvolvePartyMap());
        seConfigureMapList.add(repairProdOrderPartyConfigureMap);
//Init configuration of RepairProdOrder [RepairProdOrderItem] node
        ServiceEntityConfigureMap repairProdOrderItemConfigureMap = new ServiceEntityConfigureMap();
        repairProdOrderItemConfigureMap.setParentNodeName(RepairProdOrder.NODENAME);
        repairProdOrderItemConfigureMap.setNodeName(RepairProdOrderItem.NODENAME);
        repairProdOrderItemConfigureMap.setNodeType(RepairProdOrderItem.class);
        repairProdOrderItemConfigureMap.setTableName(RepairProdOrderItem.NODENAME);
        repairProdOrderItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        repairProdOrderItemConfigureMap.addNodeFieldMap("itemCostLossRate", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("itemCostActual", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("amountWithLossRate", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("actualAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("refBOMItemUUID", java.lang.String.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("status", int.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("planStartPrepareDate", java.util.Date.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("actualStartDate", java.util.Date.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("planStartDate", java.util.Date.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("actualEndDate", java.util.Date.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("planEndDate", java.util.Date.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("comLeadTime", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("selfLeadTime", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("itemCostNoTax", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("unitCostNoTax", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("taxRate", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("inStockAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("inProcessAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("toPickAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("availableAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("lackInPlanAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("pickedAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("suppliedAmount", double.class);
        repairProdOrderItemConfigureMap.addNodeFieldMap("pickStatus", int.class);
        seConfigureMapList.add(repairProdOrderItemConfigureMap);
//Init configuration of ProductionOrder [RepairProdItemReqProposal] node
        ServiceEntityConfigureMap repairProdItemReqProposalConfigureMap = new ServiceEntityConfigureMap();
        repairProdItemReqProposalConfigureMap.setParentNodeName(RepairProdOrderItem.NODENAME);
        repairProdItemReqProposalConfigureMap.setNodeName(RepairProdItemReqProposal.NODENAME);
        repairProdItemReqProposalConfigureMap.setNodeType(RepairProdItemReqProposal.class);
        repairProdItemReqProposalConfigureMap.setTableName(RepairProdItemReqProposal.NODENAME);
        repairProdItemReqProposalConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("itemIndex", int.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("refWarehouseUUID", java.lang.String.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("refBOMItemUUID", java.lang.String.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("documentType", int.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("storeAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("storeUnitUUID", java.lang.String.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("refStoreItemUUID", java.lang.String.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("planStartDate", java.util.Date.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("actualStartDate", java.util.Date.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("planStartPrepareDate", java.util.Date.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("actualStartPrepareDate", java.util.Date.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("planEndDate", java.util.Date.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("actualEndDate", java.util.Date.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("selfLeadTime", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("comLeadTime", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("inStockAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("inProcessAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("toPickAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("availableAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("lackInPlanAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("pickedAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("suppliedAmount", double.class);
        repairProdItemReqProposalConfigureMap.addNodeFieldMap("pickStatus", int.class);
        seConfigureMapList.add(repairProdItemReqProposalConfigureMap);
//Init configuration of RepairProdOrder [RepairProdSupplyWarehouse] node
        ServiceEntityConfigureMap repairProdSupplyWarehouseConfigureMap = new ServiceEntityConfigureMap();
        repairProdSupplyWarehouseConfigureMap.setParentNodeName(RepairProdOrder.NODENAME);
        repairProdSupplyWarehouseConfigureMap.setNodeName(RepairProdSupplyWarehouse.NODENAME);
        repairProdSupplyWarehouseConfigureMap.setNodeType(RepairProdSupplyWarehouse.class);
        repairProdSupplyWarehouseConfigureMap.setTableName(RepairProdSupplyWarehouse.NODENAME);
        repairProdSupplyWarehouseConfigureMap.setFieldList(super.getBasicReferenceFieldMap());
        seConfigureMapList.add(repairProdSupplyWarehouseConfigureMap);
//Init configuration of RepairProdOrder [RepairProdTargetMatItem] node
        ServiceEntityConfigureMap repairProdTargetMatItemConfigureMap = new ServiceEntityConfigureMap();
        repairProdTargetMatItemConfigureMap.setParentNodeName(RepairProdOrder.NODENAME);
        repairProdTargetMatItemConfigureMap.setNodeName(RepairProdTargetMatItem.NODENAME);
        repairProdTargetMatItemConfigureMap.setNodeType(RepairProdTargetMatItem.class);
        repairProdTargetMatItemConfigureMap.setTableName(RepairProdTargetMatItem.NODENAME);
        repairProdTargetMatItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        repairProdTargetMatItemConfigureMap.addNodeFieldMap("processIndex", int.class);
        repairProdTargetMatItemConfigureMap.addNodeFieldMap("refSerialId", java.lang.String.class);
        repairProdTargetMatItemConfigureMap.addNodeFieldMap("status", int.class);
        repairProdTargetMatItemConfigureMap.addNodeFieldMap("processIndex", int.class);
        repairProdTargetMatItemConfigureMap.addNodeFieldMap("refSerialId", java.lang.String.class);
        seConfigureMapList.add(repairProdTargetMatItemConfigureMap);
//Init configuration of RepairProdOrder [RepairProdTargetItemAttachment] node
        ServiceEntityConfigureMap repairProdTargetItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
        repairProdTargetItemAttachmentConfigureMap.setParentNodeName(RepairProdTargetMatItem.NODENAME);
        repairProdTargetItemAttachmentConfigureMap.setNodeName(RepairProdTargetItemAttachment.NODENAME);
        repairProdTargetItemAttachmentConfigureMap.setNodeType(RepairProdTargetItemAttachment.class);
        repairProdTargetItemAttachmentConfigureMap.setTableName(RepairProdTargetItemAttachment.NODENAME);
        repairProdTargetItemAttachmentConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        repairProdTargetItemAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
        repairProdTargetItemAttachmentConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(repairProdTargetItemAttachmentConfigureMap);
//Init configuration of RepairProdOrder [RepairProdTarSubItem] node
        ServiceEntityConfigureMap repairProdTarSubItemConfigureMap = new ServiceEntityConfigureMap();
        repairProdTarSubItemConfigureMap.setParentNodeName(RepairProdTargetMatItem.NODENAME);
        repairProdTarSubItemConfigureMap.setNodeName(RepairProdTarSubItem.NODENAME);
        repairProdTarSubItemConfigureMap.setNodeType(RepairProdTarSubItem.class);
        repairProdTarSubItemConfigureMap.setTableName(RepairProdTarSubItem.NODENAME);
        repairProdTarSubItemConfigureMap.setFieldList(super.getBasicDocMatItemMap());
        repairProdTarSubItemConfigureMap.addNodeFieldMap("layer", int.class);
        repairProdTarSubItemConfigureMap.addNodeFieldMap("refParentItemUUID", java.lang.String.class);
        repairProdTarSubItemConfigureMap.addNodeFieldMap("refBOMItemUUID", java.lang.String.class);
        repairProdTarSubItemConfigureMap.addNodeFieldMap("refWocUUID", java.lang.String.class);
        repairProdTarSubItemConfigureMap.addNodeFieldMap("processIndex", int.class);
        repairProdTarSubItemConfigureMap.addNodeFieldMap("refSerialId", java.lang.String.class);
        seConfigureMapList.add(repairProdTarSubItemConfigureMap);


        ServiceEntityConfigureMap productionOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
        productionOrderActionNodeConfigureMap.setParentNodeName(RepairProdOrder.NODENAME);
        productionOrderActionNodeConfigureMap.setNodeName(RepairProdOrderActionNode.NODENAME);
        productionOrderActionNodeConfigureMap.setNodeType(RepairProdOrderActionNode.class);
        productionOrderActionNodeConfigureMap.setTableName(RepairProdOrderActionNode.NODENAME);
        productionOrderActionNodeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        productionOrderActionNodeConfigureMap.addNodeFieldMap("processIndex", int.class);
        productionOrderActionNodeConfigureMap.addNodeFieldMap("flatNodeSwitch", int.class);
        productionOrderActionNodeConfigureMap.addNodeFieldMap("docActionCode", int.class);
        productionOrderActionNodeConfigureMap.addNodeFieldMap("executionTime", java.util.Date.class);
        productionOrderActionNodeConfigureMap.addNodeFieldMap("executedByUUID", java.lang.String.class);
        seConfigureMapList.add(productionOrderActionNodeConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }


}

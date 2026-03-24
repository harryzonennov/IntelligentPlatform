package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;
/**
 * Configure Proxy CLASS FOR Service Entity [ProductionOrder]
 *
 * @author
 * @date Wed Sep 30 22:51:10 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProductionOrderConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProductionOrder [ROOT] node
		ServiceEntityConfigureMap productionOrderConfigureMap = new ServiceEntityConfigureMap();
		productionOrderConfigureMap.setParentNodeName(" ");
		productionOrderConfigureMap.setNodeName(ProductionOrder.NODENAME);
		productionOrderConfigureMap.setNodeType(ProductionOrder.class);
		productionOrderConfigureMap.setTableName(ProductionOrder.SENAME);
		productionOrderConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		productionOrderConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("refBillOfMaterialUUID",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("amount", double.class);
		productionOrderConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("category", int.class);
		productionOrderConfigureMap.addNodeFieldMap("doneStatus", int.class);
		productionOrderConfigureMap.addNodeFieldMap("planStartPrepareDate",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("planStartTime",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("actualStartTime",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("planEndTime",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("actualEndTime",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("selfLeadTime",
				double.class);
		productionOrderConfigureMap
				.addNodeFieldMap("comLeadTime", double.class);
		productionOrderConfigureMap.addNodeFieldMap("refWocUUID",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("refPlanUUID",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("reservedMatItemUUID",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("reservedDocType",
				int.class);
		productionOrderConfigureMap.addNodeFieldMap("grossCost", double.class);
		productionOrderConfigureMap.addNodeFieldMap("grossCostLossRate",
				double.class);
		productionOrderConfigureMap.addNodeFieldMap("grossCostActual",
				double.class);
		productionOrderConfigureMap.addNodeFieldMap("approveBy",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("approveTime",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("countApproveBy",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("countApproveTime",
				java.util.Date.class);
		productionOrderConfigureMap.addNodeFieldMap("orderType", int.class);
		productionOrderConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		productionOrderConfigureMap.addNodeFieldMap("genOrderItemMode", int.class);
		seConfigureMapList.add(productionOrderConfigureMap);
		// Init configuration of ProductionOrder [ProductionOrderAttachment]
		// node
		ServiceEntityConfigureMap productionOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
		productionOrderAttachmentConfigureMap
				.setParentNodeName(ProductionOrder.NODENAME);
		productionOrderAttachmentConfigureMap
				.setNodeName(ProductionOrderAttachment.NODENAME);
		productionOrderAttachmentConfigureMap
				.setNodeType(ProductionOrderAttachment.class);
		productionOrderAttachmentConfigureMap
				.setTableName(ProductionOrderAttachment.NODENAME);
		productionOrderAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		productionOrderAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		productionOrderAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(productionOrderAttachmentConfigureMap);
		// Init configuration of ProductionOrder [ProductionOrderItem] node
		ServiceEntityConfigureMap productionOrderItemConfigureMap = new ServiceEntityConfigureMap();
		productionOrderItemConfigureMap
				.setParentNodeName(ProductionOrder.NODENAME);
		productionOrderItemConfigureMap
				.setNodeName(ProductionOrderItem.NODENAME);
		productionOrderItemConfigureMap.setNodeType(ProductionOrderItem.class);
		productionOrderItemConfigureMap
				.setTableName(ProductionOrderItem.NODENAME);
		productionOrderItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		productionOrderItemConfigureMap.addNodeFieldMap("itemCostLossRate",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("itemCostActual",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("amountWithLossRate",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("actualAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		productionOrderItemConfigureMap.addNodeFieldMap("status", int.class);
		productionOrderItemConfigureMap.addNodeFieldMap("planStartPrepareDate",
				java.util.Date.class);
		productionOrderItemConfigureMap.addNodeFieldMap("actualStartDate",
				java.util.Date.class);
		productionOrderItemConfigureMap.addNodeFieldMap("planStartDate",
				java.util.Date.class);
		productionOrderItemConfigureMap.addNodeFieldMap("actualEndDate",
				java.util.Date.class);
		productionOrderItemConfigureMap.addNodeFieldMap("planEndDate",
				java.util.Date.class);
		productionOrderItemConfigureMap.addNodeFieldMap("comLeadTime",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("selfLeadTime",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("itemCostNoTax",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("unitCostNoTax",
				double.class);
		productionOrderItemConfigureMap
				.addNodeFieldMap("taxRate", double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("inStockAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("toPickAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("inProcessAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("suppliedAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("pickStatus",
				int.class);
		productionOrderItemConfigureMap.addNodeFieldMap("availableAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("lackInPlanAmount",
				double.class);
		productionOrderItemConfigureMap.addNodeFieldMap("pickedAmount",
				double.class);
		seConfigureMapList.add(productionOrderItemConfigureMap);
		// Init configuration of ProductionOrder [ProdOrderItemReqProposal] node
		ServiceEntityConfigureMap prodOrderItemReqProposalConfigureMap = new ServiceEntityConfigureMap();
		prodOrderItemReqProposalConfigureMap
				.setParentNodeName(ProductionOrderItem.NODENAME);
		prodOrderItemReqProposalConfigureMap
				.setNodeName(ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposalConfigureMap
				.setNodeType(ProdOrderItemReqProposal.class);
		prodOrderItemReqProposalConfigureMap
				.setTableName(ProdOrderItemReqProposal.NODENAME);
		prodOrderItemReqProposalConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("itemIndex",
				int.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap(
				"refWarehouseUUID", java.lang.String.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("documentType",
				int.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("storeAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("storeUnitUUID",
				java.lang.String.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("refStoreItemUUID",
				java.lang.String.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("planStartDate",
				java.util.Date.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("actualStartDate",
				java.util.Date.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap(
				"planStartPrepareDate", java.util.Date.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap(
				"actualStartPrepareDate", java.util.Date.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("planEndDate",
				java.util.Date.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("actualEndDate",
				java.util.Date.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("selfLeadTime",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("comLeadTime",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("inStockAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("toPickAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("inProcessAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("suppliedAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("pickStatus",
				int.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("availableAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("lackInPlanAmount",
				double.class);
		prodOrderItemReqProposalConfigureMap.addNodeFieldMap("pickedAmount",
				double.class);
		seConfigureMapList.add(prodOrderItemReqProposalConfigureMap);
		// Init configuration of ProductionOrder [ProdOrderSupplyWarehouse] node
		ServiceEntityConfigureMap prodOrderSupplyWarehouseConfigureMap = new ServiceEntityConfigureMap();
		prodOrderSupplyWarehouseConfigureMap
				.setParentNodeName(ProductionOrder.NODENAME);
		prodOrderSupplyWarehouseConfigureMap
				.setNodeName(ProdOrderSupplyWarehouse.NODENAME);
		prodOrderSupplyWarehouseConfigureMap
				.setNodeType(ProdOrderSupplyWarehouse.class);
		prodOrderSupplyWarehouseConfigureMap
				.setTableName(ProdOrderSupplyWarehouse.NODENAME);
		prodOrderSupplyWarehouseConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(prodOrderSupplyWarehouseConfigureMap);

		// Init configuration of Warehouse [productionOrderParty] node
		ServiceEntityConfigureMap productionOrderPartyConfigureMap = new ServiceEntityConfigureMap();
		productionOrderPartyConfigureMap
				.setParentNodeName(ProductionOrder.NODENAME);
		productionOrderPartyConfigureMap
				.setNodeName(ProductionOrderParty.NODENAME);
		productionOrderPartyConfigureMap
				.setNodeType(ProductionOrderParty.class);
		productionOrderPartyConfigureMap
				.setTableName(ProductionOrderParty.NODENAME);
		productionOrderPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(productionOrderPartyConfigureMap);

		// Init configuration of ProductionOrder [ProdOrderReport] node
		ServiceEntityConfigureMap prodOrderReportConfigureMap = new ServiceEntityConfigureMap();
		prodOrderReportConfigureMap.setParentNodeName(ProductionOrder.NODENAME);
		prodOrderReportConfigureMap.setNodeName(ProdOrderReport.NODENAME);
		prodOrderReportConfigureMap.setNodeType(ProdOrderReport.class);
		prodOrderReportConfigureMap.setTableName(ProdOrderReport.NODENAME);
		prodOrderReportConfigureMap
				.setFieldList(super.getBasicSENodeFieldMap());
		prodOrderReportConfigureMap.addNodeFieldMap("reportStatus", int.class);
		prodOrderReportConfigureMap
				.addNodeFieldMap("reportCategory", int.class);
		prodOrderReportConfigureMap.addNodeFieldMap("reportTime",
				java.util.Date.class);
		prodOrderReportConfigureMap.addNodeFieldMap("reportedBy",
				java.lang.String.class);
		prodOrderReportConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		prodOrderReportConfigureMap
				.addNodeFieldMap("grossAmount", double.class);
		prodOrderReportConfigureMap.addNodeFieldMap("grossPrice", double.class);
		prodOrderReportConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		prodOrderReportConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		seConfigureMapList.add(prodOrderReportConfigureMap);

		ServiceEntityConfigureMap prodOrderReportAttachmentConfigureMap = new ServiceEntityConfigureMap();
		prodOrderReportAttachmentConfigureMap
				.setParentNodeName(ProdOrderReport.NODENAME);
		prodOrderReportAttachmentConfigureMap
				.setNodeName(ProdOrderReportAttachment.NODENAME);
		prodOrderReportAttachmentConfigureMap
				.setNodeType(ProdOrderReportAttachment.class);
		prodOrderReportAttachmentConfigureMap
				.setTableName(ProdOrderReportAttachment.NODENAME);
		prodOrderReportAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		prodOrderReportAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		prodOrderReportAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(prodOrderReportAttachmentConfigureMap);

		// Init configuration of Warehouse [productionOrderItemParty] node
		ServiceEntityConfigureMap productionOrderItemPartyConfigureMap = new ServiceEntityConfigureMap();
		productionOrderItemPartyConfigureMap
				.setParentNodeName(ProductionOrderItem.NODENAME);
		productionOrderItemPartyConfigureMap
				.setNodeName(ProductionOrderItemParty.NODENAME);
		productionOrderItemPartyConfigureMap
				.setNodeType(ProductionOrderItemParty.class);
		productionOrderItemPartyConfigureMap
				.setTableName(ProductionOrderItemParty.NODENAME);
		productionOrderItemPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(productionOrderItemPartyConfigureMap);

		// Init configuration of ProductionOrder [ProdOrderReportItem] node
		ServiceEntityConfigureMap prodOrderReportItemConfigureMap = new ServiceEntityConfigureMap();
		prodOrderReportItemConfigureMap
				.setParentNodeName(ProdOrderReport.NODENAME);
		prodOrderReportItemConfigureMap
				.setNodeName(ProdOrderReportItem.NODENAME);
		prodOrderReportItemConfigureMap.setNodeType(ProdOrderReportItem.class);
		prodOrderReportItemConfigureMap
				.setTableName(ProdOrderReportItem.NODENAME);
		prodOrderReportItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodOrderReportItemConfigureMap.addNodeFieldMap("processIndex",
				int.class);
		prodOrderReportItemConfigureMap.addNodeFieldMap("refTemplateSKUUUID",
				java.lang.String.class);
		prodOrderReportItemConfigureMap.addNodeFieldMap("refSerialId",
				java.lang.String.class);
		prodOrderReportItemConfigureMap.addNodeFieldMap("refInboundUUID",
				java.lang.String.class);
		seConfigureMapList.add(prodOrderReportItemConfigureMap);
		// Init configuration of ProductionOrder [ProdOrderTargetMatItem] node
		ServiceEntityConfigureMap prodOrderTargetMatItemConfigureMap = new ServiceEntityConfigureMap();
		prodOrderTargetMatItemConfigureMap
				.setParentNodeName(ProductionOrder.NODENAME);
		prodOrderTargetMatItemConfigureMap
				.setNodeName(ProdOrderTargetMatItem.NODENAME);
		prodOrderTargetMatItemConfigureMap
				.setNodeType(ProdOrderTargetMatItem.class);
		prodOrderTargetMatItemConfigureMap
				.setTableName(ProdOrderTargetMatItem.NODENAME);
		prodOrderTargetMatItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodOrderTargetMatItemConfigureMap.addNodeFieldMap("processIndex", int.class);
		prodOrderTargetMatItemConfigureMap.addNodeFieldMap("refSerialId",
				java.lang.String.class);
		seConfigureMapList.add(prodOrderTargetMatItemConfigureMap);
		// Init configuration of ProductionOrder [prodOrderTargetItemAttachment]
		// node
		ServiceEntityConfigureMap prodOrderTargetItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		prodOrderTargetItemAttachmentConfigureMap
				.setParentNodeName(ProdOrderTargetMatItem.NODENAME);
		prodOrderTargetItemAttachmentConfigureMap
				.setNodeName(ProdOrderTargetItemAttachment.NODENAME);
		prodOrderTargetItemAttachmentConfigureMap
				.setNodeType(ProdOrderTargetItemAttachment.class);
		prodOrderTargetItemAttachmentConfigureMap
				.setTableName(ProdOrderTargetItemAttachment.NODENAME);
		prodOrderTargetItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		prodOrderTargetItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		prodOrderTargetItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(prodOrderTargetItemAttachmentConfigureMap);
		// Init configuration of ProductionOrder [ProdOrderTarSubItem] node
		ServiceEntityConfigureMap prodOrderTarSubItemConfigureMap = new ServiceEntityConfigureMap();
		prodOrderTarSubItemConfigureMap
				.setParentNodeName(ProdOrderTargetMatItem.NODENAME);
		prodOrderTarSubItemConfigureMap
				.setNodeName(ProdOrderTarSubItem.NODENAME);
		prodOrderTarSubItemConfigureMap.setNodeType(ProdOrderTarSubItem.class);
		prodOrderTarSubItemConfigureMap
				.setTableName(ProdOrderTarSubItem.NODENAME);
		prodOrderTarSubItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodOrderTarSubItemConfigureMap.addNodeFieldMap("layer", int.class);
		prodOrderTarSubItemConfigureMap.addNodeFieldMap("refParentItemUUID",
				java.lang.String.class);
		prodOrderTarSubItemConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		prodOrderTarSubItemConfigureMap.addNodeFieldMap("refWocUUID",
				java.lang.String.class);
		prodOrderTarSubItemConfigureMap.addNodeFieldMap("processIndex", int.class);
		prodOrderTarSubItemConfigureMap.addNodeFieldMap("refSerialId",
				java.lang.String.class);
		seConfigureMapList.add(prodOrderTarSubItemConfigureMap);

		ServiceEntityConfigureMap productionOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
		productionOrderActionNodeConfigureMap.setParentNodeName(ProductionOrder.NODENAME);
		productionOrderActionNodeConfigureMap.setNodeName(ProductionOrderActionNode.NODENAME);
		productionOrderActionNodeConfigureMap.setNodeType(ProductionOrderActionNode.class);
		productionOrderActionNodeConfigureMap.setTableName(ProductionOrderActionNode.NODENAME);
		productionOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(productionOrderActionNodeConfigureMap);

		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProductionPlan]
 *
 * @author
 * @date Wed Sep 30 23:24:37 CST 2020
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProductionPlanConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProductionPlan [ROOT] node
		ServiceEntityConfigureMap productionPlanConfigureMap = new ServiceEntityConfigureMap();
		productionPlanConfigureMap.setParentNodeName(" ");
		productionPlanConfigureMap.setNodeName(ProductionPlan.NODENAME);
		productionPlanConfigureMap.setNodeType(ProductionPlan.class);
		productionPlanConfigureMap.setTableName(ProductionPlan.SENAME);
		productionPlanConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		productionPlanConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				java.lang.String.class);
		productionPlanConfigureMap.addNodeFieldMap("refBillOfMaterialUUID",
				java.lang.String.class);
		productionPlanConfigureMap.addNodeFieldMap("amount", double.class);
		productionPlanConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		productionPlanConfigureMap.addNodeFieldMap("category", int.class);
		productionPlanConfigureMap.addNodeFieldMap("planStartPrepareDate",
				java.util.Date.class);
		productionPlanConfigureMap.addNodeFieldMap("planStartTime",
				java.util.Date.class);
		productionPlanConfigureMap.addNodeFieldMap("actualStartTime",
				java.util.Date.class);
		productionPlanConfigureMap.addNodeFieldMap("planEndTime",
				java.util.Date.class);
		productionPlanConfigureMap.addNodeFieldMap("actualEndTime",
				java.util.Date.class);
		productionPlanConfigureMap
				.addNodeFieldMap("selfLeadTime", double.class);
		productionPlanConfigureMap.addNodeFieldMap("comLeadTime", double.class);
		productionPlanConfigureMap.addNodeFieldMap("approveBy",
				java.lang.String.class);
		productionPlanConfigureMap.addNodeFieldMap("approveTime",
				java.util.Date.class);
		productionPlanConfigureMap.addNodeFieldMap("countApproveBy",
				java.lang.String.class);
		productionPlanConfigureMap.addNodeFieldMap("countApproveTime",
				java.util.Date.class);
		productionPlanConfigureMap.addNodeFieldMap("refMainProdOrderUUID",
				java.lang.String.class);
		productionPlanConfigureMap.addNodeFieldMap("initTimeMode", int.class);
		productionPlanConfigureMap.addNodeFieldMap("productionBatchNumber",
				java.lang.String.class);
		seConfigureMapList.add(productionPlanConfigureMap);
		// Init configuration of ProductionPlan [ProductionPlanAttachment] node
		ServiceEntityConfigureMap productionPlanAttachmentConfigureMap = new ServiceEntityConfigureMap();
		productionPlanAttachmentConfigureMap
				.setParentNodeName(ProductionPlan.NODENAME);
		productionPlanAttachmentConfigureMap
				.setNodeName(ProductionPlanAttachment.NODENAME);
		productionPlanAttachmentConfigureMap
				.setNodeType(ProductionPlanAttachment.class);
		productionPlanAttachmentConfigureMap
				.setTableName(ProductionPlanAttachment.NODENAME);
		productionPlanAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		productionPlanAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		productionPlanAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(productionPlanAttachmentConfigureMap);

		// Init configuration of Warehouse [productionPlanParty] node
		ServiceEntityConfigureMap productionPlanPartyConfigureMap = new ServiceEntityConfigureMap();
		productionPlanPartyConfigureMap
				.setParentNodeName(ProductionPlan.NODENAME);
		productionPlanPartyConfigureMap
				.setNodeName(ProductionPlanParty.NODENAME);
		productionPlanPartyConfigureMap
				.setNodeType(ProductionPlanParty.class);
		productionPlanPartyConfigureMap
				.setTableName(ProductionPlanParty.NODENAME);
		productionPlanPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(productionPlanPartyConfigureMap);

		// Init configuration of ProductionPlan [ProductionPlanItem] node
		ServiceEntityConfigureMap productionPlanItemConfigureMap = new ServiceEntityConfigureMap();
		productionPlanItemConfigureMap
				.setParentNodeName(ProductionPlan.NODENAME);
		productionPlanItemConfigureMap.setNodeName(ProductionPlanItem.NODENAME);
		productionPlanItemConfigureMap.setNodeType(ProductionPlanItem.class);
		productionPlanItemConfigureMap
				.setTableName(ProductionPlanItem.NODENAME);
		productionPlanItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		productionPlanItemConfigureMap.addNodeFieldMap("itemCostLossRate",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("itemCostActual",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("amountWithLossRate",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("actualAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		productionPlanItemConfigureMap.addNodeFieldMap("status", int.class);
		productionPlanItemConfigureMap.addNodeFieldMap("planStartPrepareDate",
				java.util.Date.class);
		productionPlanItemConfigureMap.addNodeFieldMap("actualStartDate",
				java.util.Date.class);
		productionPlanItemConfigureMap.addNodeFieldMap("planStartDate",
				java.util.Date.class);
		productionPlanItemConfigureMap.addNodeFieldMap("actualEndDate",
				java.util.Date.class);
		productionPlanItemConfigureMap.addNodeFieldMap("planEndDate",
				java.util.Date.class);
		productionPlanItemConfigureMap.addNodeFieldMap("comLeadTime",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("selfLeadTime",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("itemCostNoTax",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("unitCostNoTax",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("taxRate", double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("inStockAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("inProcessAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("suppliedAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("pickStatus",
				int.class);
		productionPlanItemConfigureMap.addNodeFieldMap("toPickAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("availableAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("lackInPlanAmount",
				double.class);
		productionPlanItemConfigureMap.addNodeFieldMap("pickedAmount",
				double.class);
		seConfigureMapList.add(productionPlanItemConfigureMap);

		// Init configuration of Warehouse [productionPlanItemParty] node
		ServiceEntityConfigureMap productionPlanItemPartyConfigureMap = new ServiceEntityConfigureMap();
		productionPlanItemPartyConfigureMap
				.setParentNodeName(ProductionPlanItem.NODENAME);
		productionPlanItemPartyConfigureMap
				.setNodeName(ProductionPlanItemParty.NODENAME);
		productionPlanItemPartyConfigureMap
				.setNodeType(ProductionPlanItemParty.class);
		productionPlanItemPartyConfigureMap
				.setTableName(ProductionPlanItemParty.NODENAME);
		productionPlanItemPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(productionPlanItemPartyConfigureMap);

		// Init configuration of ProductionPlan [ProdPlanItemReqProposal] node
		ServiceEntityConfigureMap prodPlanItemReqProposalConfigureMap = new ServiceEntityConfigureMap();
		prodPlanItemReqProposalConfigureMap
				.setParentNodeName(ProductionPlanItem.NODENAME);
		prodPlanItemReqProposalConfigureMap
				.setNodeName(ProdPlanItemReqProposal.NODENAME);
		prodPlanItemReqProposalConfigureMap
				.setNodeType(ProdPlanItemReqProposal.class);
		prodPlanItemReqProposalConfigureMap
				.setTableName(ProdPlanItemReqProposal.NODENAME);
		prodPlanItemReqProposalConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("itemIndex",
				int.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("documentType",
				int.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("storeAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("storeUnitUUID",
				java.lang.String.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("refStoreItemUUID",
				java.lang.String.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("planStartDate",
				java.util.Date.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("actualStartDate",
				java.util.Date.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap(
				"planStartPrepareDate", java.util.Date.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap(
				"actualStartPrepareDate", java.util.Date.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("planEndDate",
				java.util.Date.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("actualEndDate",
				java.util.Date.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("selfLeadTime",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("comLeadTime",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("inStockAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("toPickAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("inProcessAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("suppliedAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("availableAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("pickStatus",
				int.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("lackInPlanAmount",
				double.class);
		prodPlanItemReqProposalConfigureMap.addNodeFieldMap("pickedAmount",
				double.class);
		seConfigureMapList.add(prodPlanItemReqProposalConfigureMap);
		// Init configuration of ProductionPlan [ProdPlanSupplyWarehouse] node
		ServiceEntityConfigureMap prodPlanSupplyWarehouseConfigureMap = new ServiceEntityConfigureMap();
		prodPlanSupplyWarehouseConfigureMap
				.setParentNodeName(ProductionPlan.NODENAME);
		prodPlanSupplyWarehouseConfigureMap
				.setNodeName(ProdPlanSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseConfigureMap
				.setNodeType(ProdPlanSupplyWarehouse.class);
		prodPlanSupplyWarehouseConfigureMap
				.setTableName(ProdPlanSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(prodPlanSupplyWarehouseConfigureMap);
		// Init configuration of ProductionPlan [ProdPlanTargetMatItem] node
		ServiceEntityConfigureMap prodPlanTargetMatItemConfigureMap = new ServiceEntityConfigureMap();
		prodPlanTargetMatItemConfigureMap
				.setParentNodeName(ProductionPlan.NODENAME);
		prodPlanTargetMatItemConfigureMap
				.setNodeName(ProdPlanTargetMatItem.NODENAME);
		prodPlanTargetMatItemConfigureMap
				.setNodeType(ProdPlanTargetMatItem.class);
		prodPlanTargetMatItemConfigureMap
				.setTableName(ProdPlanTargetMatItem.NODENAME);
		prodPlanTargetMatItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodPlanTargetMatItemConfigureMap.addNodeFieldMap("processIndex", int.class);
		prodPlanTargetMatItemConfigureMap.addNodeFieldMap("refSerialId",
				java.lang.String.class);
		seConfigureMapList.add(prodPlanTargetMatItemConfigureMap);
		// Init configuration of ProductionPlan [ProdPlanTarSubItem] node
		ServiceEntityConfigureMap prodPlanTarSubItemConfigureMap = new ServiceEntityConfigureMap();
		prodPlanTarSubItemConfigureMap
				.setParentNodeName("ProdOrderTargetMatItem");
		prodPlanTarSubItemConfigureMap.setNodeName(ProdPlanTarSubItem.NODENAME);
		prodPlanTarSubItemConfigureMap.setNodeType(ProdPlanTarSubItem.class);
		prodPlanTarSubItemConfigureMap
				.setTableName(ProdPlanTarSubItem.NODENAME);
		prodPlanTarSubItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("layer", int.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refParentItemUUID",
				java.lang.String.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refWocUUID",
				java.lang.String.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("layer", int.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refParentItemUUID",
				java.lang.String.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refBOMItemUUID",
				java.lang.String.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refWocUUID",
				java.lang.String.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("processIndex", int.class);
		prodPlanTarSubItemConfigureMap.addNodeFieldMap("refSerialId",
				java.lang.String.class);
		seConfigureMapList.add(prodPlanTarSubItemConfigureMap);

		ServiceEntityConfigureMap productionPlanActionNodeConfigureMap = new ServiceEntityConfigureMap();
		productionPlanActionNodeConfigureMap.setParentNodeName(ProductionPlan.NODENAME);
		productionPlanActionNodeConfigureMap.setNodeName(ProductionPlanActionNode.NODENAME);
		productionPlanActionNodeConfigureMap.setNodeType(ProductionPlanActionNode.class);
		productionPlanActionNodeConfigureMap.setTableName(ProductionPlanActionNode.NODENAME);
		productionPlanActionNodeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		productionPlanActionNodeConfigureMap.addNodeFieldMap("processIndex", int.class);
		productionPlanActionNodeConfigureMap.addNodeFieldMap("flatNodeSwitch", int.class);
		productionPlanActionNodeConfigureMap.addNodeFieldMap("docActionCode", int.class);
		productionPlanActionNodeConfigureMap.addNodeFieldMap("executionTime", java.util.Date.class);
		productionPlanActionNodeConfigureMap.addNodeFieldMap("executedByUUID", java.lang.String.class);
		seConfigureMapList.add(productionPlanActionNodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

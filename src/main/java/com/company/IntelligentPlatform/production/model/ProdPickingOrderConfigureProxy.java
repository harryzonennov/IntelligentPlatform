package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [ProdPickingOrder]
 *
 * @author
 * @date Mon May 27 14:22:12 CST 2019
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ProdPickingOrderConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.production");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of ProdPickingOrder [ROOT] node
		ServiceEntityConfigureMap prodPickingOrderConfigureMap = new ServiceEntityConfigureMap();
		prodPickingOrderConfigureMap.setParentNodeName(" ");
		prodPickingOrderConfigureMap.setNodeName(ProdPickingOrder.NODENAME);
		prodPickingOrderConfigureMap.setNodeType(ProdPickingOrder.class);
		prodPickingOrderConfigureMap.setTableName(ProdPickingOrder.SENAME);
		prodPickingOrderConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		prodPickingOrderConfigureMap.addNodeFieldMap("category", int.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("processType", int.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("approveBy", String.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("approveDate", Date.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("approveType", int.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("processBy", String.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("processDate", Date.class);
		prodPickingOrderConfigureMap.addNodeFieldMap("grossCost",
				double.class);
		seConfigureMapList.add(prodPickingOrderConfigureMap);

		// Init configuration of Warehouse [prodPickingOrderParty] node
		ServiceEntityConfigureMap prodPickingOrderPartyConfigureMap = new ServiceEntityConfigureMap();
		prodPickingOrderPartyConfigureMap
				.setParentNodeName(ProdPickingOrder.NODENAME);
		prodPickingOrderPartyConfigureMap
				.setNodeName(ProdPickingOrderParty.NODENAME);
		prodPickingOrderPartyConfigureMap
				.setNodeType(ProdPickingOrderParty.class);
		prodPickingOrderPartyConfigureMap
				.setTableName(ProdPickingOrderParty.NODENAME);
		prodPickingOrderPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(prodPickingOrderPartyConfigureMap);

		// Init configuration of ProdPickingOrder [ProdPickingRefOrderItem] node
		ServiceEntityConfigureMap prodPickingRefOrderItemConfigureMap = new ServiceEntityConfigureMap();
		prodPickingRefOrderItemConfigureMap
				.setParentNodeName(ProdPickingOrder.NODENAME);
		prodPickingRefOrderItemConfigureMap
				.setNodeName(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemConfigureMap
				.setNodeType(ProdPickingRefOrderItem.class);
		prodPickingRefOrderItemConfigureMap
				.setTableName(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefOrderItemConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		prodPickingRefOrderItemConfigureMap.addNodeFieldMap("processIndex",
				int.class);
		prodPickingRefOrderItemConfigureMap.addNodeFieldMap("refProdOrderUUID",
				java.lang.String.class);
		prodPickingRefOrderItemConfigureMap.addNodeFieldMap("amount",
				double.class);
		prodPickingRefOrderItemConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		prodPickingRefOrderItemConfigureMap.addNodeFieldMap("orderCost",
				double.class);
		seConfigureMapList.add(prodPickingRefOrderItemConfigureMap);
		// Init configuration of ProdPickingOrder [ProdPickingRefMaterialItem]
		// node
		ServiceEntityConfigureMap prodPickingRefMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		prodPickingRefMaterialItemConfigureMap
				.setParentNodeName(ProdPickingRefOrderItem.NODENAME);
		prodPickingRefMaterialItemConfigureMap
				.setNodeName(ProdPickingRefMaterialItem.NODENAME);
		prodPickingRefMaterialItemConfigureMap
				.setNodeType(ProdPickingRefMaterialItem.class);
		prodPickingRefMaterialItemConfigureMap
				.setTableName(ProdPickingRefMaterialItem.NODENAME);
		prodPickingRefMaterialItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"refNextOrderUUID", java.lang.String.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"refInboundItemUUID", java.lang.String.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"refOutboundItemUUID", java.lang.String.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"refWarehouseUUID", int.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"refProdOrderItemUUID", java.lang.String.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"itemProcessType", int.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("itemPriceNoTax",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("unitPriceNoTax",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("taxRate",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("refBillOfMaterialUUID",
				String.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"planStartPrepareDate", Date.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"planStartTime", Date.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"planEndTime", Date.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap(
				"documentType", int.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("inStockAmount",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("toPickAmount",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("inProcessAmount",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("suppliedAmount",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("availableAmount",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("lackInPlanAmount",
				double.class);
		prodPickingRefMaterialItemConfigureMap.addNodeFieldMap("pickedAmount",
				double.class);
		seConfigureMapList.add(prodPickingRefMaterialItemConfigureMap);

		ServiceEntityConfigureMap prodPickingOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
		prodPickingOrderActionNodeConfigureMap.setParentNodeName(ProdPickingOrder.NODENAME);
		prodPickingOrderActionNodeConfigureMap.setNodeName(ProdPickingOrderActionNode.NODENAME);
		prodPickingOrderActionNodeConfigureMap.setNodeType(ProdPickingOrderActionNode.class);
		prodPickingOrderActionNodeConfigureMap.setTableName(ProdPickingOrderActionNode.NODENAME);
		prodPickingOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(prodPickingOrderActionNodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

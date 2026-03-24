package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [PurchaseOrder]
 *
 * @author
 * @date Tue Dec 01 16:20:19 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class PurchaseOrderConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of PurchaseOrder [ROOT] node
		ServiceEntityConfigureMap purchaseOrderConfigureMap = new ServiceEntityConfigureMap();
		purchaseOrderConfigureMap.setParentNodeName("");
		purchaseOrderConfigureMap.setNodeName(PurchaseOrder.NODENAME);
		purchaseOrderConfigureMap.setNodeType(PurchaseOrder.class);
		purchaseOrderConfigureMap.setTableName(PurchaseOrder.SENAME);
		purchaseOrderConfigureMap
				.setFieldList(super.getBasicDocumentFieldMap());
		purchaseOrderConfigureMap
				.addNodeFieldMap("grossNetPrice", double.class);
		purchaseOrderConfigureMap
		.addNodeFieldMap("refInWarehouseUUID", String.class);
		purchaseOrderConfigureMap
		.addNodeFieldMap("refInboundDeliveryUUID", String.class);
		purchaseOrderConfigureMap.addNodeFieldMap("taxRate",
				double.class);
		seConfigureMapList.add(purchaseOrderConfigureMap);
		// Init configuration of PurchaseOrder [PurchaseOrderParty] node
		ServiceEntityConfigureMap purchaseOrderPartyConfigureMap = new ServiceEntityConfigureMap();
		purchaseOrderPartyConfigureMap
				.setParentNodeName(PurchaseOrder.NODENAME);
		purchaseOrderPartyConfigureMap.setNodeName(PurchaseOrderParty.NODENAME);
		purchaseOrderPartyConfigureMap.setNodeType(PurchaseOrderParty.class);
		purchaseOrderPartyConfigureMap
				.setTableName(PurchaseOrderParty.NODENAME);
		purchaseOrderPartyConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		purchaseOrderPartyConfigureMap.addNodeFieldMap("partyRole", int.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("postcode",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("cityName",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("townZone",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("subArea",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("contactPerson",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("contactMobile",
				java.lang.String.class);
		purchaseOrderPartyConfigureMap.addNodeFieldMap("address",
				java.lang.String.class);
		seConfigureMapList.add(purchaseOrderPartyConfigureMap);
		// Init configuration of PurchaseOrder [PurchaseOrderItem] node
		ServiceEntityConfigureMap purchaseOrderItemConfigureMap = new ServiceEntityConfigureMap();
		purchaseOrderItemConfigureMap.setParentNodeName(PurchaseOrder.NODENAME);
		purchaseOrderItemConfigureMap.setNodeName(PurchaseOrderItem.NODENAME);
		purchaseOrderItemConfigureMap.setNodeType(PurchaseOrderItem.class);
		purchaseOrderItemConfigureMap.setTableName(PurchaseOrderItem.NODENAME);
		purchaseOrderItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		purchaseOrderItemConfigureMap.addNodeFieldMap("itemStatus", int.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("refUnitUUID",
				java.lang.String.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("netValue", double.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("refInWarehouseUUID",
				java.lang.String.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("refInDeliveryItemUUID",
				java.lang.String.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("refFinAccountUUID",
				java.lang.String.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("availableCheckStatus",
				int.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("weight", double.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("volume", double.class);
		purchaseOrderItemConfigureMap
				.addNodeFieldMap("producerName", String.class);
		purchaseOrderItemConfigureMap.addNodeFieldMap("productionBatchNumber",
				String.class);
		purchaseOrderItemConfigureMap
				.addNodeFieldMap("productionDate", Date.class);
		seConfigureMapList.add(purchaseOrderItemConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [InboundDelivery]
 *
 * @author
 * @date Sun Dec 29 16:16:23 CST 2013
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class InboundDeliveryConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of InboundDelivery [ROOT] node
		ServiceEntityConfigureMap inboundDeliveryConfigureMap = new ServiceEntityConfigureMap();
		inboundDeliveryConfigureMap.setParentNodeName(" ");
		inboundDeliveryConfigureMap.setNodeName("ROOT");
		inboundDeliveryConfigureMap.setNodeType(InboundDelivery.class);
		inboundDeliveryConfigureMap.setTableName("InboundDelivery");
		inboundDeliveryConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		inboundDeliveryConfigureMap.addNodeFieldMap("refSiteUUID",
				java.lang.String.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("refWarehouseAreaUUID",
				java.lang.String.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("grossInboundFee",
				double.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("grossPrice", double.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("shippingTime",
				java.util.Date.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("shippingPoint",
				java.lang.String.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("freightChargeType",
				int.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("freightCharge",
				double.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("planCategory", int.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("planExecuteDate",
				java.util.Date.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("productionBatchNumber",
				String.class);
		inboundDeliveryConfigureMap.addNodeFieldMap("purchaseBatchNumber",
				String.class);
		seConfigureMapList.add(inboundDeliveryConfigureMap);

		// Init configuration of Warehouse [inboundDeliveryParty] node
		ServiceEntityConfigureMap inboundDeliveryPartyConfigureMap = new ServiceEntityConfigureMap();
		inboundDeliveryPartyConfigureMap
				.setParentNodeName(InboundDelivery.NODENAME);
		inboundDeliveryPartyConfigureMap
				.setNodeName(InboundDeliveryParty.NODENAME);
		inboundDeliveryPartyConfigureMap
				.setNodeType(InboundDeliveryParty.class);
		inboundDeliveryPartyConfigureMap
				.setTableName(InboundDeliveryParty.NODENAME);
		inboundDeliveryPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(inboundDeliveryPartyConfigureMap);

		// Init configuration of QualityInspectOrder [QualityInsOrderAttachment]
		// node
		ServiceEntityConfigureMap inboundDeliveryAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inboundDeliveryAttachmentConfigureMap
				.setParentNodeName(InboundDelivery.NODENAME);
		inboundDeliveryAttachmentConfigureMap
				.setNodeName(InboundDeliveryAttachment.NODENAME);
		inboundDeliveryAttachmentConfigureMap
				.setNodeType(InboundDeliveryAttachment.class);
		inboundDeliveryAttachmentConfigureMap
				.setTableName(InboundDeliveryAttachment.NODENAME);
		inboundDeliveryAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inboundDeliveryAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inboundDeliveryAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(inboundDeliveryAttachmentConfigureMap);

		//Init configuration of InboundDelivery [InboundDeliveryActionNode] node
		ServiceEntityConfigureMap inboundDeliveryActionNodeConfigureMap = new ServiceEntityConfigureMap();
		inboundDeliveryActionNodeConfigureMap.setParentNodeName(InboundDelivery.NODENAME);
		inboundDeliveryActionNodeConfigureMap.setNodeName(InboundDeliveryActionNode.NODENAME);
		inboundDeliveryActionNodeConfigureMap.setNodeType(InboundDeliveryActionNode.class);
		inboundDeliveryActionNodeConfigureMap.setTableName(InboundDeliveryActionNode.NODENAME);
		inboundDeliveryActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(inboundDeliveryActionNodeConfigureMap);

		// Init configuration of InboundDelivery [InboundItem] node
		ServiceEntityConfigureMap inboundItemConfigureMap = new ServiceEntityConfigureMap();
		inboundItemConfigureMap.setParentNodeName("ROOT");
		inboundItemConfigureMap.setNodeName("InboundItem");
		inboundItemConfigureMap
				.setNodeType(InboundItem.class);
		inboundItemConfigureMap.setTableName("InboundItem");
		inboundItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		inboundItemConfigureMap
				.addNodeFieldMap("volume", double.class);
		inboundItemConfigureMap
				.addNodeFieldMap("weight", double.class);
		inboundItemConfigureMap.addNodeFieldMap("actualAmount",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("actualVolume",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("actualWeight",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("declaredValue",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("productionDate",
				Date.class);
		inboundItemConfigureMap.addNodeFieldMap("refUnitName",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("refUnitNodeInstID",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap(
				"refWarehouseAreaUUID", String.class);
		inboundItemConfigureMap.addNodeFieldMap("refShelfNumberID",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("producerName",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("inboundFee",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("currencyCode",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("refStoreItemUUID",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("itemPriceNoTax",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("unitPriceNoTax",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("taxRate",
				double.class);
		inboundItemConfigureMap.addNodeFieldMap("packageStandard",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("refMaterialSKUName",
				String.class);
		inboundItemConfigureMap.addNodeFieldMap("refMaterialSKUId",
				String.class);
		seConfigureMapList.add(inboundItemConfigureMap);

		// Init configuration of  [inboundItemParty] node
		ServiceEntityConfigureMap inboundItemPartyConfigureMap = new ServiceEntityConfigureMap();
		inboundItemPartyConfigureMap
				.setParentNodeName(InboundItem.NODENAME);
		inboundItemPartyConfigureMap
				.setNodeName(InboundItemParty.NODENAME);
		inboundItemPartyConfigureMap
				.setNodeType(InboundItemParty.class);
		inboundItemPartyConfigureMap
				.setTableName(InboundItemParty.NODENAME);
		inboundItemPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(inboundItemPartyConfigureMap);

		ServiceEntityConfigureMap inboundItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inboundItemAttachmentConfigureMap
				.setParentNodeName(InboundDelivery.NODENAME);
		inboundItemAttachmentConfigureMap
				.setNodeName(InboundItemAttachment.NODENAME);
		inboundItemAttachmentConfigureMap
				.setNodeType(InboundItemAttachment.class);
		inboundItemAttachmentConfigureMap
				.setTableName(InboundItemAttachment.NODENAME);
		inboundItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inboundItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inboundItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(inboundItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [OutboundDelivery]
 *
 * @author
 * @date Sun Dec 29 16:15:52 CST 2013
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class OutboundDeliveryConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of OutboundDelivery [ROOT] node
		ServiceEntityConfigureMap outboundDeliveryConfigureMap = new ServiceEntityConfigureMap();
		outboundDeliveryConfigureMap.setParentNodeName(" ");
		outboundDeliveryConfigureMap.setNodeName("ROOT");
		outboundDeliveryConfigureMap.setNodeType(OutboundDelivery.class);
		outboundDeliveryConfigureMap.setTableName("OutboundDelivery");
		outboundDeliveryConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		outboundDeliveryConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("refWarehouseAreaUUID",
				java.lang.String.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("grossOutboundFee",
				double.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("grossStorageFee",
				double.class);
		outboundDeliveryConfigureMap
				.addNodeFieldMap("grossPrice", double.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("shippingTime",
				java.util.Date.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("shippingPoint",
				java.lang.String.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("freightChargeType",
				int.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("freightCharge",
				double.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("planCategory", int.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("planExecuteDate",
				java.util.Date.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("productionBatchNumber",
				String.class);
		outboundDeliveryConfigureMap.addNodeFieldMap("purchaseBatchNumber",
				String.class);
		seConfigureMapList.add(outboundDeliveryConfigureMap);


		// Init configuration of Warehouse [outboundDeliveryParty] node
		ServiceEntityConfigureMap outboundDeliveryPartyConfigureMap = new ServiceEntityConfigureMap();
		outboundDeliveryPartyConfigureMap
				.setParentNodeName(OutboundDelivery.NODENAME);
		outboundDeliveryPartyConfigureMap
				.setNodeName(OutboundDeliveryParty.NODENAME);
		outboundDeliveryPartyConfigureMap
				.setNodeType(OutboundDeliveryParty.class);
		outboundDeliveryPartyConfigureMap
				.setTableName(OutboundDeliveryParty.NODENAME);
		outboundDeliveryPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(outboundDeliveryPartyConfigureMap);

		// Init configuration of QualityInspectOrder [QualityInsOrderAttachment]
		// node
		ServiceEntityConfigureMap outboundDeliveryAttachmentConfigureMap = new ServiceEntityConfigureMap();
		outboundDeliveryAttachmentConfigureMap
				.setParentNodeName(OutboundDelivery.NODENAME);
		outboundDeliveryAttachmentConfigureMap
				.setNodeName(OutboundDeliveryAttachment.NODENAME);
		outboundDeliveryAttachmentConfigureMap
				.setNodeType(OutboundDeliveryAttachment.class);
		outboundDeliveryAttachmentConfigureMap
				.setTableName(OutboundDeliveryAttachment.NODENAME);
		outboundDeliveryAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		outboundDeliveryAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		outboundDeliveryAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(outboundDeliveryAttachmentConfigureMap);

		//Init configuration of OutboundDelivery [OutboundDeliveryActionNode] node
		ServiceEntityConfigureMap outboundDeliveryActionNodeConfigureMap = new ServiceEntityConfigureMap();
		outboundDeliveryActionNodeConfigureMap.setParentNodeName(OutboundDelivery.NODENAME);
		outboundDeliveryActionNodeConfigureMap.setNodeName(OutboundDeliveryActionNode.NODENAME);
		outboundDeliveryActionNodeConfigureMap.setNodeType(OutboundDeliveryActionNode.class);
		outboundDeliveryActionNodeConfigureMap.setTableName(OutboundDeliveryActionNode.NODENAME);
		outboundDeliveryActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(outboundDeliveryActionNodeConfigureMap);

		// Init configuration of OutboundDelivery [OutboundItem] node
		ServiceEntityConfigureMap outboundItemConfigureMap = new ServiceEntityConfigureMap();
		outboundItemConfigureMap.setParentNodeName("ROOT");
		outboundItemConfigureMap.setNodeName("OutboundItem");
		outboundItemConfigureMap
				.setNodeType(OutboundItem.class);
		outboundItemConfigureMap.setTableName("OutboundItem");
		outboundItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		outboundItemConfigureMap.addNodeFieldMap("volume",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("weight",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("actualAmount",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("actualVolume",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("actualWeight",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("declaredValue",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("productionDate",
				Date.class);
		outboundItemConfigureMap.addNodeFieldMap("refUnitName",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap("refUnitNodeInstID",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap(
				"refWarehouseAreaUUID", String.class);
		outboundItemConfigureMap.addNodeFieldMap("refShelfNumberID",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap("producerName",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap("outboundFee",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("storageFee",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("refStoreItemUUID",
				String.class);
		outboundItemConfigureMap
				.addNodeFieldMap("storeDay", int.class);
		outboundItemConfigureMap.addNodeFieldMap("itemPriceNoTax",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("unitPriceNoTax",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("currencyCode",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap("taxRate",
				double.class);
		outboundItemConfigureMap.addNodeFieldMap("packageStandard",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap("refMaterialSKUName",
				String.class);
		outboundItemConfigureMap.addNodeFieldMap("refMaterialSKUId",
				String.class);
		seConfigureMapList.add(outboundItemConfigureMap);

		// Init configuration of  [outboundItemParty] node
		ServiceEntityConfigureMap outboundItemPartyConfigureMap = new ServiceEntityConfigureMap();
		outboundItemPartyConfigureMap
				.setParentNodeName(OutboundItem.NODENAME);
		outboundItemPartyConfigureMap
				.setNodeName(OutboundItemParty.NODENAME);
		outboundItemPartyConfigureMap
				.setNodeType(OutboundItemParty.class);
		outboundItemPartyConfigureMap
				.setTableName(OutboundItemParty.NODENAME);
		outboundItemPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(outboundItemPartyConfigureMap);

		ServiceEntityConfigureMap outboundItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		outboundItemAttachmentConfigureMap
				.setParentNodeName(OutboundDelivery.NODENAME);
		outboundItemAttachmentConfigureMap
				.setNodeName(OutboundItemAttachment.NODENAME);
		outboundItemAttachmentConfigureMap
				.setNodeType(OutboundItemAttachment.class);
		outboundItemAttachmentConfigureMap
				.setTableName(OutboundItemAttachment.NODENAME);
		outboundItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		outboundItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		outboundItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(outboundItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

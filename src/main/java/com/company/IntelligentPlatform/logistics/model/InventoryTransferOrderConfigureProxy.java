package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [InventoryTransferOrder]
 *
 * @author
 * @date Fri Nov 27 11:18:45 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class InventoryTransferOrderConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of InventoryTransferOrder [ROOT] node
		ServiceEntityConfigureMap inventoryTransferOrderConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferOrderConfigureMap.setParentNodeName(" ");
		inventoryTransferOrderConfigureMap
				.setNodeName(InventoryTransferOrder.NODENAME);
		inventoryTransferOrderConfigureMap
				.setNodeType(InventoryTransferOrder.class);
		inventoryTransferOrderConfigureMap
				.setTableName(InventoryTransferOrder.SENAME);
		inventoryTransferOrderConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		inventoryTransferOrderConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap(
				"refWarehouseAreaUUID", java.lang.String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap("grossOutboundFee",
				double.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap("grossStorageFee",
				double.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap(
				"refInboundWarehouseUUID", java.lang.String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap(
				"refInboundWarehouseAreaUUID", java.lang.String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap(
				"refInboundDeliveryUUID", java.lang.String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap(
				"refOutboundDeliveryUUID", java.lang.String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap("planCategory",
				int.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap("planExecuteDate",
				java.util.Date.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap("productionBatchNumber",
				String.class);
		inventoryTransferOrderConfigureMap.addNodeFieldMap("purchaseBatchNumber",
				String.class);
		seConfigureMapList.add(inventoryTransferOrderConfigureMap);

		ServiceEntityConfigureMap inventoryTransferOrderAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferOrderAttachmentConfigureMap
				.setParentNodeName(InventoryTransferOrder.NODENAME);
		inventoryTransferOrderAttachmentConfigureMap
				.setNodeName(InventoryTransferOrderAttachment.NODENAME);
		inventoryTransferOrderAttachmentConfigureMap
				.setNodeType(InventoryTransferOrderAttachment.class);
		inventoryTransferOrderAttachmentConfigureMap
				.setTableName(InventoryTransferOrderAttachment.NODENAME);
		inventoryTransferOrderAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inventoryTransferOrderAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inventoryTransferOrderAttachmentConfigureMap.addNodeFieldMap(
				"fileType", java.lang.String.class);
		seConfigureMapList.add(inventoryTransferOrderAttachmentConfigureMap);

		// Init configuration of Warehouse [inventoryTransferOrderParty] node
		ServiceEntityConfigureMap inventoryTransferOrderPartyConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferOrderPartyConfigureMap
				.setParentNodeName(InboundDelivery.NODENAME);
		inventoryTransferOrderPartyConfigureMap
				.setNodeName(InventoryTransferOrderParty.NODENAME);
		inventoryTransferOrderPartyConfigureMap
				.setNodeType(InventoryTransferOrderParty.class);
		inventoryTransferOrderPartyConfigureMap
				.setTableName(InventoryTransferOrderParty.NODENAME);
		inventoryTransferOrderPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(inventoryTransferOrderPartyConfigureMap);

		// Init configuration of  [inventoryTransferItemParty] node
		ServiceEntityConfigureMap inventoryTransferItemPartyConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferItemPartyConfigureMap
				.setParentNodeName(InboundItem.NODENAME);
		inventoryTransferItemPartyConfigureMap
				.setNodeName(InventoryTransferItemParty.NODENAME);
		inventoryTransferItemPartyConfigureMap
				.setNodeType(InventoryTransferItemParty.class);
		inventoryTransferItemPartyConfigureMap
				.setTableName(InventoryTransferItemParty.NODENAME);
		inventoryTransferItemPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(inventoryTransferItemPartyConfigureMap);

		//Init configuration of InventoryTransferOrder [InventoryTransferOrderActionNode] node
		ServiceEntityConfigureMap inventoryTransferOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferOrderActionNodeConfigureMap.setParentNodeName(InventoryTransferOrder.NODENAME);
		inventoryTransferOrderActionNodeConfigureMap.setNodeName(InventoryTransferOrderActionNode.NODENAME);
		inventoryTransferOrderActionNodeConfigureMap.setNodeType(InventoryTransferOrderActionNode.class);
		inventoryTransferOrderActionNodeConfigureMap.setTableName(InventoryTransferOrderActionNode.NODENAME);
		inventoryTransferOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(inventoryTransferOrderActionNodeConfigureMap);

		// Init configuration of InventoryTransferOrder
		ServiceEntityConfigureMap inventoryTransferItemConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferItemConfigureMap
				.setParentNodeName(InventoryTransferOrder.NODENAME);
		inventoryTransferItemConfigureMap
				.setNodeName(InventoryTransferItem.NODENAME);
		inventoryTransferItemConfigureMap
				.setNodeType(InventoryTransferItem.class);
		inventoryTransferItemConfigureMap
				.setTableName(InventoryTransferItem.NODENAME);
		inventoryTransferItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		inventoryTransferItemConfigureMap.addNodeFieldMap("volume",
				double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap("weight",
				double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"actualAmount", double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"actualVolume", double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"actualWeight", double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"declaredValue", double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"productionDate", java.util.Date.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refUnitName", String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refUnitNodeInstID", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refWarehouseAreaUUID", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refShelfNumberID", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"producerName", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"outboundFee", double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"storageFee", double.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refOutboundItemUUID", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refInboundItemUUID", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"refStoreItemUUID", java.lang.String.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap(
				"itemStatus", int.class);
		inventoryTransferItemConfigureMap.addNodeFieldMap("storeDay",
				int.class);
		seConfigureMapList.add(inventoryTransferItemConfigureMap);

		ServiceEntityConfigureMap inventoryTransferAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inventoryTransferAttachmentConfigureMap
				.setParentNodeName(InventoryTransferOrder.NODENAME);
		inventoryTransferAttachmentConfigureMap
				.setNodeName(InventoryTransferItemAttachment.NODENAME);
		inventoryTransferAttachmentConfigureMap
				.setNodeType(InventoryTransferItemAttachment.class);
		inventoryTransferAttachmentConfigureMap
				.setTableName(InventoryTransferItemAttachment.NODENAME);
		inventoryTransferAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inventoryTransferAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inventoryTransferAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(inventoryTransferAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

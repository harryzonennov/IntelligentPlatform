package com.company.IntelligentPlatform.logistics.model;

import org.springframework.stereotype.Repository;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Configure Proxy CLASS FOR Service Entity [Warehouse]
 *
 * @author
 * @date Sat Nov 14 15:20:29 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class WarehouseStoreConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of WarehouseStore [ROOT] node
		ServiceEntityConfigureMap warehouseStoreConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreConfigureMap.setParentNodeName(" ");
		warehouseStoreConfigureMap.setNodeName(WarehouseStore.NODENAME);
		warehouseStoreConfigureMap.setNodeType(WarehouseStore.class);
		warehouseStoreConfigureMap.setTableName(WarehouseStore.SENAME);
		warehouseStoreConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
		warehouseStoreConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		warehouseStoreConfigureMap.addNodeFieldMap("refWarehouseAreaUUID",
				java.lang.String.class);
		warehouseStoreConfigureMap.addNodeFieldMap("grossPrice",
				double.class);
		warehouseStoreConfigureMap.addNodeFieldMap("grossPriceDisplay",
				double.class);

		seConfigureMapList.add(warehouseStoreConfigureMap);

		// Init configuration of Warehouse [warehouseStoreParty] node
		ServiceEntityConfigureMap warehouseStorePartyConfigureMap = new ServiceEntityConfigureMap();
		warehouseStorePartyConfigureMap
				.setParentNodeName(WarehouseStore.NODENAME);
		warehouseStorePartyConfigureMap
				.setNodeName(WarehouseStoreParty.NODENAME);
		warehouseStorePartyConfigureMap
				.setNodeType(WarehouseStoreParty.class);
		warehouseStorePartyConfigureMap
				.setTableName(WarehouseStoreParty.NODENAME);
		warehouseStorePartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(warehouseStorePartyConfigureMap);

		// Init configuration of Warehouse [WarehouseStoreItem] node
		ServiceEntityConfigureMap warehouseStoreItemConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreItemConfigureMap.setParentNodeName(WarehouseStore.NODENAME);
		warehouseStoreItemConfigureMap.setNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemConfigureMap.setNodeType(WarehouseStoreItem.class);
		warehouseStoreItemConfigureMap
				.setTableName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		warehouseStoreItemConfigureMap.addNodeFieldMap("productionDate",
				java.util.Date.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("productionBatchNumber",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("refUnitName",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("inboundDate",
				java.util.Date.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("outboundDate",
				java.util.Date.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("refWarehouseUUID",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("refWarehouseAreaUUID",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("refShelfNumberId",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("volume", double.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("weight", double.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("declaredValue",
				double.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap(
				"refMaterialTemplateUUID", String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("refMaterialSKUId",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("refMaterialSKUName",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("productionPlace",
				String.class);
		warehouseStoreItemConfigureMap.addNodeFieldMap("packageStandard",
				String.class);
		seConfigureMapList.add(warehouseStoreItemConfigureMap);
		ServiceEntityConfigureMap warehouseStoreAttachmentConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreAttachmentConfigureMap
				.setParentNodeName(WarehouseStore.NODENAME);
		warehouseStoreAttachmentConfigureMap
				.setNodeName(WarehouseStoreAttachment.NODENAME);
		warehouseStoreAttachmentConfigureMap
				.setNodeType(WarehouseStoreAttachment.class);
		warehouseStoreAttachmentConfigureMap
				.setTableName(WarehouseStoreAttachment.NODENAME);
		warehouseStoreAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		warehouseStoreAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		warehouseStoreAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(warehouseStoreAttachmentConfigureMap);

		//Init configuration of OutboundDelivery [OutboundDeliveryActionNode] node
		ServiceEntityConfigureMap warehouseStoreActionNodeConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreActionNodeConfigureMap.setParentNodeName(WarehouseStore.NODENAME);
		warehouseStoreActionNodeConfigureMap.setNodeName(WarehouseStoreActionNode.NODENAME);
		warehouseStoreActionNodeConfigureMap.setNodeType(WarehouseStoreActionNode.class);
		warehouseStoreActionNodeConfigureMap.setTableName(WarehouseStoreActionNode.NODENAME);
		warehouseStoreActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(warehouseStoreActionNodeConfigureMap);

		// Init configuration of Warehouse [WarehouseStoreItemLog] node
		ServiceEntityConfigureMap warehouseStoreItemLogConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreItemLogConfigureMap
				.setParentNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemLogConfigureMap
				.setNodeName(WarehouseStoreItemLog.NODENAME);
		warehouseStoreItemLogConfigureMap
				.setNodeType(WarehouseStoreItemLog.class);
		warehouseStoreItemLogConfigureMap
				.setTableName(WarehouseStoreItemLog.NODENAME);
		warehouseStoreItemLogConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("volume",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("weight",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("amount",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("refUnitUUID",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("refUnitName",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("updatedVolume",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("updatedWeight",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("updatedAmount",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("updatedRefUnitUUID",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("updatedRefUnitName",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("documentType",
				int.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("documentUUID",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("declaredValue",
				double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap(
				"updatedDeclaredValue", double.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("refMaterialSKUUUID",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("refMaterialSKUId",
				String.class);
		warehouseStoreItemLogConfigureMap.addNodeFieldMap("refMaterialSKUName",
				String.class);
		seConfigureMapList.add(warehouseStoreItemLogConfigureMap);

		// Init configuration of Warehouse [warehouseStoreItemParty] node
		ServiceEntityConfigureMap warehouseStoreItemPartyConfigureMap = new ServiceEntityConfigureMap();
		warehouseStoreItemPartyConfigureMap
				.setParentNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemPartyConfigureMap
				.setNodeName(WarehouseStoreItemParty.NODENAME);
		warehouseStoreItemPartyConfigureMap
				.setNodeType(WarehouseStoreItemParty.class);
		warehouseStoreItemPartyConfigureMap
				.setTableName(WarehouseStoreItemParty.NODENAME);
		warehouseStoreItemPartyConfigureMap.setFieldList(super
				.getBasicInvolvePartyMap());
		seConfigureMapList.add(warehouseStoreItemPartyConfigureMap);

		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

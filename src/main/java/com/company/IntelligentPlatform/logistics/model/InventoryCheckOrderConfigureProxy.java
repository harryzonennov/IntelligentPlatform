package com.company.IntelligentPlatform.logistics.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [InventoryCheckOrder]
 *
 * @author
 * @date Thu Sep 17 16:24:18 CST 2015
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class InventoryCheckOrderConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.logistics");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of InventoryCheckOrder [ROOT] node
		ServiceEntityConfigureMap inventoryCheckOrderConfigureMap = new ServiceEntityConfigureMap();
		inventoryCheckOrderConfigureMap.setParentNodeName(" ");
		inventoryCheckOrderConfigureMap
				.setNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckOrderConfigureMap.setNodeType(InventoryCheckOrder.class);
		inventoryCheckOrderConfigureMap
				.setTableName(InventoryCheckOrder.SENAME);
		inventoryCheckOrderConfigureMap.setFieldList(super
				.getBasicDocumentFieldMap());
		inventoryCheckOrderConfigureMap.addNodeFieldMap("refWarehouseUUID",
				java.lang.String.class);
		inventoryCheckOrderConfigureMap.addNodeFieldMap("refWarehouseAreaUUID",
				java.lang.String.class);
		inventoryCheckOrderConfigureMap.addNodeFieldMap("grossUpdateValue",
				double.class);
		inventoryCheckOrderConfigureMap.addNodeFieldMap("grossCheckResult",
				int.class);
		seConfigureMapList.add(inventoryCheckOrderConfigureMap);

		// Init configuration of Material [OrganizationAttachment] node
		ServiceEntityConfigureMap inventoryCheckAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inventoryCheckAttachmentConfigureMap
				.setParentNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckAttachmentConfigureMap
				.setNodeName(InventoryCheckAttachment.NODENAME);
		inventoryCheckAttachmentConfigureMap
				.setNodeType(InventoryCheckAttachment.class);
		inventoryCheckAttachmentConfigureMap
				.setTableName(InventoryCheckAttachment.NODENAME);
		inventoryCheckAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inventoryCheckAttachmentConfigureMap.addNodeFieldMap("fileType",
				String.class);
		inventoryCheckAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		seConfigureMapList.add(inventoryCheckAttachmentConfigureMap);

		//Init configuration of InventoryCheckOrder [InventoryCheckOrderActionNode] node
		ServiceEntityConfigureMap inventoryCheckOrderActionNodeConfigureMap = new ServiceEntityConfigureMap();
		inventoryCheckOrderActionNodeConfigureMap.setParentNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckOrderActionNodeConfigureMap.setNodeName(InventoryCheckOrderActionNode.NODENAME);
		inventoryCheckOrderActionNodeConfigureMap.setNodeType(InventoryCheckOrderActionNode.class);
		inventoryCheckOrderActionNodeConfigureMap.setTableName(InventoryCheckOrderActionNode.NODENAME);
		inventoryCheckOrderActionNodeConfigureMap.setFieldList(super.getBasicActionCodeNodeMap());
		seConfigureMapList.add(inventoryCheckOrderActionNodeConfigureMap);

		// Init configuration of InventoryCheckOrder [InventoryCheckItem] node
		ServiceEntityConfigureMap inventoryCheckItemConfigureMap = new ServiceEntityConfigureMap();
		inventoryCheckItemConfigureMap
				.setParentNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckItemConfigureMap.setNodeName(InventoryCheckItem.NODENAME);
		inventoryCheckItemConfigureMap.setNodeType(InventoryCheckItem.class);
		inventoryCheckItemConfigureMap
				.setTableName(InventoryCheckItem.NODENAME);
		inventoryCheckItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		inventoryCheckItemConfigureMap.addNodeFieldMap(
				"refWarehouseStoreItemUUID", java.lang.String.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("declaredValue",
				double.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("resultAmount",
				double.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("resultUnitUUID",
				java.lang.String.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("resultDeclaredValue",
				double.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("inventCheckResult",
				int.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("updateAmount",
				double.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("updateDeclaredValue",
				double.class);
		inventoryCheckItemConfigureMap.addNodeFieldMap("updateUnitUUID",
				java.lang.String.class);
		seConfigureMapList.add(inventoryCheckItemConfigureMap);

		ServiceEntityConfigureMap inventoryCheckItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		inventoryCheckItemAttachmentConfigureMap
				.setParentNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckItemAttachmentConfigureMap
				.setNodeName(InventoryCheckItemAttachment.NODENAME);
		inventoryCheckItemAttachmentConfigureMap
				.setNodeType(InventoryCheckItemAttachment.class);
		inventoryCheckItemAttachmentConfigureMap
				.setTableName(InventoryCheckItemAttachment.NODENAME);
		inventoryCheckItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		inventoryCheckItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		inventoryCheckItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(inventoryCheckItemAttachmentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}

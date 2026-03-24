package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InventoryCheckItemManager;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItem;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckItemAttachment;
import com.company.IntelligentPlatform.logistics.model.InventoryCheckOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class InventoryCheckItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected InventoryCheckItemManager inventoryCheckItemManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InventoryCheckItemAttachment.SENAME,
				InventoryCheckItemAttachment.NODENAME,
				InventoryCheckItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inventoryCheckItemExtensionUnion = new ServiceUIModelExtensionUnion();
		inventoryCheckItemExtensionUnion
				.setNodeInstId(InventoryCheckItem.NODENAME);
		inventoryCheckItemExtensionUnion
				.setNodeName(InventoryCheckItem.NODENAME);

		// UI Model Configure of node:[InventoryCheckItem]
		UIModelNodeMapConfigure inventoryCheckItemMap = new UIModelNodeMapConfigure();
		inventoryCheckItemMap.setSeName(InventoryCheckItem.SENAME);
		inventoryCheckItemMap.setNodeName(InventoryCheckItem.NODENAME);
		inventoryCheckItemMap.setNodeInstID(InventoryCheckItem.NODENAME);
		inventoryCheckItemMap.setHostNodeFlag(true);
		Class<?>[] inventoryCheckItemConvToUIParas = {
				InventoryCheckItem.class, InventoryCheckItemUIModel.class };
		inventoryCheckItemMap
				.setConvToUIMethodParas(inventoryCheckItemConvToUIParas);
		inventoryCheckItemMap
				.setConvToUIMethod(InventoryCheckItemManager.METHOD_ConvInventoryCheckItemToUI);
		inventoryCheckItemMap.setLogicManager(inventoryCheckItemManager);
		Class<?>[] InventoryCheckItemConvUIToParas = {
				InventoryCheckItemUIModel.class, InventoryCheckItem.class };
		inventoryCheckItemMap
				.setConvUIToMethodParas(InventoryCheckItemConvUIToParas);
		inventoryCheckItemMap
				.setConvUIToMethod(InventoryCheckItemManager.METHOD_ConvUIToInventoryCheckItem);
		uiModelNodeMapList.add(inventoryCheckItemMap);
		
		// UI Model Configure of node:[Inventory Check Order]
		UIModelNodeMapConfigure inventoryCheckOrderMap = new UIModelNodeMapConfigure();
		inventoryCheckOrderMap.setSeName(InventoryCheckOrder.SENAME);
		inventoryCheckOrderMap.setNodeName(InventoryCheckOrder.NODENAME);
		inventoryCheckOrderMap.setNodeInstID(InventoryCheckOrder.SENAME);
		inventoryCheckOrderMap
				.setBaseNodeInstID(InventoryCheckItem.NODENAME);
		inventoryCheckOrderMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] inventoryCheckOrderConvToUIParas = {
				InventoryCheckOrder.class, InventoryCheckItemUIModel.class };
		inventoryCheckOrderMap
				.setConvToUIMethodParas(inventoryCheckOrderConvToUIParas);
		inventoryCheckOrderMap.setLogicManager(inventoryCheckItemManager);
		inventoryCheckOrderMap
				.setConvToUIMethod(InventoryCheckItemManager.METHOD_ConvCheckOrderToItemUI);
		uiModelNodeMapList.add(inventoryCheckOrderMap);

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefMaterialNodeMapConfigureList(
								InventoryCheckItem.NODENAME));

		// UI Model Configure of node:[WarehouseStoreItem]
		UIModelNodeMapConfigure warehouseStoreItemMap = new UIModelNodeMapConfigure();
		warehouseStoreItemMap.setSeName(WarehouseStoreItem.SENAME);
		warehouseStoreItemMap.setNodeName(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap.setNodeInstID(WarehouseStoreItem.NODENAME);
		warehouseStoreItemMap.setBaseNodeInstID(InventoryCheckItem.NODENAME);
		warehouseStoreItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		warehouseStoreItemMap.setServiceEntityManager(warehouseStoreManager);
		List<SearchConfigConnectCondition> warehouseStoreItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition warehouseStoreItemCondition0 = new SearchConfigConnectCondition();
		warehouseStoreItemCondition0
				.setSourceFieldName("refWarehouseStoreItemUUID");
		warehouseStoreItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		warehouseStoreItemConditionList.add(warehouseStoreItemCondition0);
		warehouseStoreItemMap
				.setConnectionConditions(warehouseStoreItemConditionList);
		Class<?>[] warehouseStoreItemConvToUIParas = {
				WarehouseStoreItem.class, InventoryCheckItemUIModel.class };
		warehouseStoreItemMap
				.setConvToUIMethodParas(warehouseStoreItemConvToUIParas);
		warehouseStoreItemMap
				.setConvToUIMethod(InventoryCheckItemManager.METHOD_ConvWarehouseStoreItemToUI);
		warehouseStoreItemMap.setLogicManager(inventoryCheckItemManager);
		uiModelNodeMapList.add(warehouseStoreItemMap);
		inventoryCheckItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inventoryCheckItemExtensionUnion);
		return resultList;
	}

}

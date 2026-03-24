package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseStoreSetting;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class WarehouseStoreSettingUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WarehouseManager warehouseManager;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion warehouseStoreSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		warehouseStoreSettingExtensionUnion.setNodeInstId(WarehouseStoreSetting.NODENAME);
		warehouseStoreSettingExtensionUnion.setNodeName(WarehouseStoreSetting.NODENAME);

		// UI Model Configure of node:[WarehouseStoreSetting]
		UIModelNodeMapConfigure warehouseStoreSettingMap = new UIModelNodeMapConfigure();
		warehouseStoreSettingMap.setSeName(WarehouseStoreSetting.SENAME);
		warehouseStoreSettingMap.setNodeName(WarehouseStoreSetting.NODENAME);
		warehouseStoreSettingMap.setNodeInstID(WarehouseStoreSetting.NODENAME);
		warehouseStoreSettingMap.setHostNodeFlag(true);
		Class<?>[] warehouseStoreSettingConvToUIParas = { WarehouseStoreSetting.class,
				WarehouseStoreSettingUIModel.class };
		warehouseStoreSettingMap.setConvToUIMethodParas(warehouseStoreSettingConvToUIParas);
		warehouseStoreSettingMap
				.setConvToUIMethod(WarehouseManager.METHOD_ConvWarehouseStoreSettingToUI);
		Class<?>[] warehouseStoreSettingConvUIToParas = { WarehouseStoreSettingUIModel.class,
				WarehouseStoreSetting.class };
		warehouseStoreSettingMap.setConvUIToMethodParas(warehouseStoreSettingConvUIToParas);
		warehouseStoreSettingMap
				.setConvUIToMethod(WarehouseManager.METHOD_ConvUIToWarehouseStoreSetting);
		uiModelNodeMapList.add(warehouseStoreSettingMap);
		
		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure materialStockKeepUnitMap = new UIModelNodeMapConfigure();
		materialStockKeepUnitMap.setSeName(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		materialStockKeepUnitMap.setNodeInstID(MaterialStockKeepUnit.SENAME);
		materialStockKeepUnitMap.setBaseNodeInstID(WarehouseStoreSetting.NODENAME);
		materialStockKeepUnitMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		materialStockKeepUnitMap.setServiceEntityManager(materialStockKeepUnitManager);
		List<SearchConfigConnectCondition> materialStockKeepUnitConditionList  = new ArrayList<>();
		SearchConfigConnectCondition materialStockKeepUnitCondition0  = new SearchConfigConnectCondition();
		materialStockKeepUnitCondition0.setSourceFieldName("refMaterialSKUUUID");
		materialStockKeepUnitCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		materialStockKeepUnitConditionList.add(materialStockKeepUnitCondition0);
		materialStockKeepUnitMap.setConnectionConditions(materialStockKeepUnitConditionList);
		Class<?>[] materialStockKeepUnitConvToUIParas = {MaterialStockKeepUnit.class, WarehouseStoreSettingUIModel.class};
		materialStockKeepUnitMap.setConvToUIMethodParas(materialStockKeepUnitConvToUIParas);
		materialStockKeepUnitMap.setConvToUIMethod(WarehouseManager.METHOD_ConvMaterialSKUToStoreSettingUI);
		uiModelNodeMapList.add(materialStockKeepUnitMap);

		// -->[Warehouse]
		UIModelNodeMapConfigure warehouseUIModelMap = new UIModelNodeMapConfigure();
		warehouseUIModelMap.setSeName(Warehouse.SENAME);
		warehouseUIModelMap.setNodeName(Warehouse.NODENAME);
		warehouseUIModelMap.setNodeInstID(Warehouse.SENAME);
		warehouseUIModelMap.setBaseNodeInstID(WarehouseStoreSetting.NODENAME);
		warehouseUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		warehouseUIModelMap.setEditNodeFlag(false);
		Class<?>[] warehouseConvToUIParas = {
				Warehouse.class, WarehouseStoreSettingUIModel.class};
		warehouseUIModelMap
				.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseUIModelMap
				.setConvToUIMethod(WarehouseManager.METHOD_ConvWarehouseToStoreSettingUI);		
		uiModelNodeMapList.add(warehouseUIModelMap);		

		warehouseStoreSettingExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(warehouseStoreSettingExtensionUnion);
		return resultList;
	}

}

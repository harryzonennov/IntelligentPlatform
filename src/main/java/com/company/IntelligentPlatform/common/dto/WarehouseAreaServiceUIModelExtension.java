package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.WarehouseAreaManager;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.model.WarehouseArea;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class WarehouseAreaServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WarehouseManager warehouseManager;

	@Autowired
	protected WarehouseAreaManager warehouseAreaManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion warehouseAreaExtensionUnion = new ServiceUIModelExtensionUnion();
		warehouseAreaExtensionUnion.setNodeInstId(WarehouseArea.NODENAME);
		warehouseAreaExtensionUnion.setNodeName(WarehouseArea.NODENAME);

		// UI Model Configure of node:[WarehouseArea]
		UIModelNodeMapConfigure warehouseAreaMap = new UIModelNodeMapConfigure();
		warehouseAreaMap.setSeName(WarehouseArea.SENAME);
		warehouseAreaMap.setNodeName(WarehouseArea.NODENAME);
		warehouseAreaMap.setNodeInstID(WarehouseArea.NODENAME);
		warehouseAreaMap.setHostNodeFlag(true);
		Class<?>[] warehouseAreaConvToUIParas = { WarehouseArea.class,
				WarehouseAreaUIModel.class };
		warehouseAreaMap.setLogicManager(warehouseAreaManager);
		warehouseAreaMap.setConvToUIMethodParas(warehouseAreaConvToUIParas);
		warehouseAreaMap
				.setConvToUIMethod(WarehouseAreaManager.METHOD_ConvWarehouseAreaToUI);
		Class<?>[] WarehouseAreaConvUIToParas = { WarehouseAreaUIModel.class,
				WarehouseArea.class };
		warehouseAreaMap.setConvUIToMethodParas(WarehouseAreaConvUIToParas);
		warehouseAreaMap
				.setConvUIToMethod(WarehouseAreaManager.METHOD_ConvUIToWarehouseArea);
		uiModelNodeMapList.add(warehouseAreaMap);
		warehouseAreaExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);


		// -->[Warehouse]
		UIModelNodeMapConfigure warehouseUIModelMap = new UIModelNodeMapConfigure();
		warehouseUIModelMap.setSeName(Warehouse.SENAME);
		warehouseUIModelMap.setNodeName(Warehouse.NODENAME);
		warehouseUIModelMap.setNodeInstID(Warehouse.SENAME);
		warehouseUIModelMap.setBaseNodeInstID(WarehouseArea.NODENAME);
		warehouseUIModelMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		warehouseUIModelMap.setEditNodeFlag(false);
		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				WarehouseAreaUIModel.class };
		warehouseUIModelMap.setLogicManager(warehouseAreaManager);
		warehouseUIModelMap.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseUIModelMap
				.setConvToUIMethod(WarehouseAreaManager.METHOD_ConvWarehouseToAreaUI);
		uiModelNodeMapList.add(warehouseUIModelMap);

		resultList.add(warehouseAreaExtensionUnion);
		return resultList;
	}

}

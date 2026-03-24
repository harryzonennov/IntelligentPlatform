package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.service.RepairProdOrderManager;
import com.company.IntelligentPlatform.production.model.RepairProdSupplyWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairProdSupplyWarehouseServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WarehouseManager warehouseManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPlanSupplyWarehouseExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPlanSupplyWarehouseExtensionUnion
				.setNodeInstId(RepairProdSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseExtensionUnion
				.setNodeName(RepairProdSupplyWarehouse.NODENAME);

		// UI Model Configure of node:[RepairProdSupplyWarehouse]
		UIModelNodeMapConfigure prodPlanSupplyWarehouseMap = new UIModelNodeMapConfigure();
		prodPlanSupplyWarehouseMap.setSeName(RepairProdSupplyWarehouse.SENAME);
		prodPlanSupplyWarehouseMap
				.setNodeName(RepairProdSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseMap
				.setNodeInstID(RepairProdSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseMap.setHostNodeFlag(true);
		Class<?>[] prodPlanSupplyWarehouseConvToUIParas = {
				RepairProdSupplyWarehouse.class,
				RepairProdSupplyWarehouseUIModel.class };
		prodPlanSupplyWarehouseMap
				.setConvToUIMethodParas(prodPlanSupplyWarehouseConvToUIParas);
		prodPlanSupplyWarehouseMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvRepairProdSupplyWarehouseToUI);
		Class<?>[] RepairProdSupplyWarehouseConvUIToParas = {
				RepairProdSupplyWarehouseUIModel.class,
				RepairProdSupplyWarehouse.class };
		prodPlanSupplyWarehouseMap
				.setConvUIToMethodParas(RepairProdSupplyWarehouseConvUIToParas);
		prodPlanSupplyWarehouseMap
				.setConvUIToMethod(RepairProdOrderManager.METHOD_ConvUIToRepairProdSupplyWarehouse);
		uiModelNodeMapList.add(prodPlanSupplyWarehouseMap);

		// UI Model Configure of node:[Warehouse]
		UIModelNodeMapConfigure warehouseMap = new UIModelNodeMapConfigure();
		warehouseMap.setSeName(Warehouse.SENAME);
		warehouseMap.setNodeName(Warehouse.NODENAME);
		warehouseMap.setNodeInstID(Warehouse.SENAME);
		warehouseMap.setBaseNodeInstID(RepairProdSupplyWarehouse.NODENAME);
		warehouseMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		warehouseMap.setServiceEntityManager(warehouseManager);
		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				RepairProdSupplyWarehouseUIModel.class };
		warehouseMap.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseMap
				.setConvToUIMethod(RepairProdOrderManager.METHOD_ConvWarehouseToUI);
		uiModelNodeMapList.add(warehouseMap);
		prodPlanSupplyWarehouseExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPlanSupplyWarehouseExtensionUnion);
		return resultList;
	}

}

package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdPlanSupplyWarehouseUIModel;
import com.company.IntelligentPlatform.production.service.ProductionPlanManager;
import com.company.IntelligentPlatform.production.model.ProdPlanSupplyWarehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class ProdPlanSupplyWarehouseServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected WarehouseManager warehouseManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodPlanSupplyWarehouseExtensionUnion = new ServiceUIModelExtensionUnion();
		prodPlanSupplyWarehouseExtensionUnion
				.setNodeInstId(ProdPlanSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseExtensionUnion
				.setNodeName(ProdPlanSupplyWarehouse.NODENAME);

		// UI Model Configure of node:[ProdPlanSupplyWarehouse]
		UIModelNodeMapConfigure prodPlanSupplyWarehouseMap = new UIModelNodeMapConfigure();
		prodPlanSupplyWarehouseMap.setSeName(ProdPlanSupplyWarehouse.SENAME);
		prodPlanSupplyWarehouseMap
				.setNodeName(ProdPlanSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseMap
				.setNodeInstID(ProdPlanSupplyWarehouse.NODENAME);
		prodPlanSupplyWarehouseMap.setHostNodeFlag(true);
		Class<?>[] prodPlanSupplyWarehouseConvToUIParas = {
				ProdPlanSupplyWarehouse.class,
				ProdPlanSupplyWarehouseUIModel.class };
		prodPlanSupplyWarehouseMap
				.setConvToUIMethodParas(prodPlanSupplyWarehouseConvToUIParas);
		prodPlanSupplyWarehouseMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvProdPlanSupplyWarehouseToUI);
		Class<?>[] ProdPlanSupplyWarehouseConvUIToParas = {
				ProdPlanSupplyWarehouseUIModel.class,
				ProdPlanSupplyWarehouse.class };
		prodPlanSupplyWarehouseMap
				.setConvUIToMethodParas(ProdPlanSupplyWarehouseConvUIToParas);
		prodPlanSupplyWarehouseMap
				.setConvUIToMethod(ProductionPlanManager.METHOD_ConvUIToProdPlanSupplyWarehouse);
		uiModelNodeMapList.add(prodPlanSupplyWarehouseMap);

		// UI Model Configure of node:[Warehouse]
		UIModelNodeMapConfigure warehouseMap = new UIModelNodeMapConfigure();
		warehouseMap.setSeName(Warehouse.SENAME);
		warehouseMap.setNodeName(Warehouse.NODENAME);
		warehouseMap.setNodeInstID(Warehouse.SENAME);
		warehouseMap.setBaseNodeInstID(ProdPlanSupplyWarehouse.NODENAME);
		warehouseMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		warehouseMap.setServiceEntityManager(warehouseManager);
		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				ProdPlanSupplyWarehouseUIModel.class };
		warehouseMap.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseMap
				.setConvToUIMethod(ProductionPlanManager.METHOD_ConvWarehouseToUI);
		uiModelNodeMapList.add(warehouseMap);
		prodPlanSupplyWarehouseExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodPlanSupplyWarehouseExtensionUnion);
		return resultList;
	}

}

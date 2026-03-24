package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdOrderSupplyWarehouseUIModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class ProdOrderSupplyWarehouseServiceUIModelExtension extends
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
		ServiceUIModelExtensionUnion prodOrderSupplyWarehouseExtensionUnion = new ServiceUIModelExtensionUnion();
		prodOrderSupplyWarehouseExtensionUnion
				.setNodeInstId(ProdOrderSupplyWarehouse.NODENAME);
		prodOrderSupplyWarehouseExtensionUnion
				.setNodeName(ProdOrderSupplyWarehouse.NODENAME);

		// UI Model Configure of node:[ProdOrderSupplyWarehouse]
		UIModelNodeMapConfigure prodOrderSupplyWarehouseMap = new UIModelNodeMapConfigure();
		prodOrderSupplyWarehouseMap.setSeName(ProdOrderSupplyWarehouse.SENAME);
		prodOrderSupplyWarehouseMap
				.setNodeName(ProdOrderSupplyWarehouse.NODENAME);
		prodOrderSupplyWarehouseMap
				.setNodeInstID(ProdOrderSupplyWarehouse.NODENAME);
		prodOrderSupplyWarehouseMap.setHostNodeFlag(true);
		Class<?>[] prodOrderSupplyWarehouseConvToUIParas = {
				ProdOrderSupplyWarehouse.class,
				ProdOrderSupplyWarehouseUIModel.class };
		prodOrderSupplyWarehouseMap
				.setConvToUIMethodParas(prodOrderSupplyWarehouseConvToUIParas);
		prodOrderSupplyWarehouseMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvProdOrderSupplyWarehouseToUI);
		Class<?>[] ProdOrderSupplyWarehouseConvUIToParas = {
				ProdOrderSupplyWarehouseUIModel.class,
				ProdOrderSupplyWarehouse.class };
		prodOrderSupplyWarehouseMap
				.setConvUIToMethodParas(ProdOrderSupplyWarehouseConvUIToParas);
		prodOrderSupplyWarehouseMap
				.setConvUIToMethod(ProductionOrderManager.METHOD_ConvUIToProdOrderSupplyWarehouse);
		uiModelNodeMapList.add(prodOrderSupplyWarehouseMap);

		// UI Model Configure of node:[Warehouse]
		UIModelNodeMapConfigure warehouseMap = new UIModelNodeMapConfigure();
		warehouseMap.setSeName(Warehouse.SENAME);
		warehouseMap.setNodeName(Warehouse.NODENAME);
		warehouseMap.setNodeInstID(Warehouse.SENAME);
		warehouseMap.setBaseNodeInstID(ProdOrderSupplyWarehouse.NODENAME);
		warehouseMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		warehouseMap.setServiceEntityManager(warehouseManager);
		Class<?>[] warehouseConvToUIParas = { Warehouse.class,
				ProdOrderSupplyWarehouseUIModel.class };
		warehouseMap.setConvToUIMethodParas(warehouseConvToUIParas);
		warehouseMap
				.setConvToUIMethod(ProductionOrderManager.METHOD_ConvWarehouseToUI);
		uiModelNodeMapList.add(warehouseMap);
		prodOrderSupplyWarehouseExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodOrderSupplyWarehouseExtensionUnion);
		return resultList;
	}

}

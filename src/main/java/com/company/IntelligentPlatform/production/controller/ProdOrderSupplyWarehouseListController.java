package com.company.IntelligentPlatform.production.controller;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.dto.ProdOrderSupplyWarehouseUIModel;
import com.company.IntelligentPlatform.production.service.ProductionOrderManager;
import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "prodOrderSupplyWarehouseListController")
@RequestMapping(value = "/prodOrderSupplyWarehouse")
public class ProdOrderSupplyWarehouseListController extends SEListController {

	public static final String AOID_RESOURCE = ProductionOrderEditorController.AOID_RESOURCE;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected WarehouseManager warehouseManager;

	public void convProdOrderSupplyWarehouseToUI(
			ProdOrderSupplyWarehouse prodOrderSupplyWarehouse,
			ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel) {
		if (prodOrderSupplyWarehouse != null) {
			prodOrderSupplyWarehouseUIModel.setUuid(prodOrderSupplyWarehouse
					.getUuid());
			prodOrderSupplyWarehouseUIModel
					.setRootNodeUUID(prodOrderSupplyWarehouse.getRootNodeUUID());
			prodOrderSupplyWarehouseUIModel
					.setRefUUID(prodOrderSupplyWarehouse.getRefUUID());
		}
	}

	public void convWarehouseToUI(Warehouse warehouse,
			ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel) {
		if (warehouse != null) {
			prodOrderSupplyWarehouseUIModel
					.setRefWarehouseId(warehouse.getId());
			prodOrderSupplyWarehouseUIModel.setRefWarehouseName(warehouse
					.getName());
			prodOrderSupplyWarehouseUIModel.setAddress(warehouse.getAddress());
			prodOrderSupplyWarehouseUIModel.setTelephone(warehouse.getTelephone());
		}
	}

	protected List<ProdOrderSupplyWarehouseUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		List<ProdOrderSupplyWarehouseUIModel> prodOrderSupplyWarehouseList = new ArrayList<ProdOrderSupplyWarehouseUIModel>();
		for (ServiceEntityNode rawNode : rawList) {
			ProdOrderSupplyWarehouseUIModel prodOrderSupplyWarehouseUIModel = new ProdOrderSupplyWarehouseUIModel();
			ProdOrderSupplyWarehouse prodOrderSupplyWarehouse = (ProdOrderSupplyWarehouse) rawNode;
			convProdOrderSupplyWarehouseToUI(prodOrderSupplyWarehouse,
					prodOrderSupplyWarehouseUIModel);
			Warehouse warehouse = (Warehouse) warehouseManager.getEntityNodeByKey(
					prodOrderSupplyWarehouse.getRefUUID(),
					IServiceEntityNodeFieldConstant.UUID,
					Warehouse.NODENAME, prodOrderSupplyWarehouse.getClient(), null);
			convWarehouseToUI(warehouse, prodOrderSupplyWarehouseUIModel);
			prodOrderSupplyWarehouseList.add(prodOrderSupplyWarehouseUIModel);
		}
		return prodOrderSupplyWarehouseList;
	}

}

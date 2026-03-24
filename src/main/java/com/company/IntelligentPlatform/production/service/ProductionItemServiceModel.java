package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProductionPlanItemServiceModel;
import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;
import com.company.IntelligentPlatform.production.model.ProductionPlan;
import com.company.IntelligentPlatform.production.model.ProductionPlanAttachment;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductionItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductionPlan.NODENAME, nodeInstId = ProductionPlan.SENAME)
	protected ProductionPlan productionPlan;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME)
	protected List<ProductionPlanItemServiceModel> productionPlanItemList = new ArrayList<ProductionPlanItemServiceModel>();

	@IServiceModuleFieldConfig(nodeName = ProdOrderSupplyWarehouse.NODENAME, nodeInstId = ProdOrderSupplyWarehouse.NODENAME)
	protected List<ServiceEntityNode> prodOrderSupplyWarehouseList = new ArrayList<ServiceEntityNode>();

	@IServiceModuleFieldConfig(nodeName = ProductionPlanAttachment.NODENAME, nodeInstId = ProductionPlanAttachment.NODENAME)
	protected List<ServiceEntityNode> prodOrderAttachmentList = new ArrayList<ServiceEntityNode>();

	public List<ProductionPlanItemServiceModel> getProductionPlanItemList() {
		return this.productionPlanItemList;
	}

	public void setProductionPlanItemList(
			List<ProductionPlanItemServiceModel> productionPlanItemList) {
		this.productionPlanItemList = productionPlanItemList;
	}

	public List<ServiceEntityNode> getProdOrderSupplyWarehouseList() {
		return this.prodOrderSupplyWarehouseList;
	}

	public void setProdOrderSupplyWarehouseList(
			List<ServiceEntityNode> prodOrderSupplyWarehouseList) {
		this.prodOrderSupplyWarehouseList = prodOrderSupplyWarehouseList;
	}

	public ProductionPlan getProductionPlan() {
		return this.productionPlan;
	}

	public void setProductionPlan(ProductionPlan productionPlan) {
		this.productionPlan = productionPlan;
	}

	public List<ServiceEntityNode> getProdOrderAttachmentList() {
		return prodOrderAttachmentList;
	}

	public void setProdOrderAttachmentList(
			List<ServiceEntityNode> prodOrderAttachmentList) {
		this.prodOrderAttachmentList = prodOrderAttachmentList;
	}

}

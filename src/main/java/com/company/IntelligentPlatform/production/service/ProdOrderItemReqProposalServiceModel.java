package com.company.IntelligentPlatform.production.service;

import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdOrderItemReqProposalServiceModel extends ServiceModule {
	
	@IServiceModuleFieldConfig(nodeName = ProductionOrderItem.NODENAME, nodeInstId = ProductionOrderItem.NODENAME)
	protected List<ProductionOrderItemServiceModel> productionOrderItemList;
	
	@IServiceModuleFieldConfig(nodeName = ProdOrderItemReqProposal.NODENAME, nodeInstId = ProdOrderItemReqProposal.NODENAME)
	protected ProdOrderItemReqProposal prodOrderItemReqProposal;


	public List<ProductionOrderItemServiceModel> getProductionOrderItemList() {
		return productionOrderItemList;
	}

	public void setProductionOrderItemList(
			List<ProductionOrderItemServiceModel> productionOrderItemList) {
		this.productionOrderItemList = productionOrderItemList;
	}

	public ProdOrderItemReqProposal getProdOrderItemReqProposal() {
		return prodOrderItemReqProposal;
	}

	public void setProdOrderItemReqProposal(
			ProdOrderItemReqProposal prodOrderItemReqProposal) {
		this.prodOrderItemReqProposal = prodOrderItemReqProposal;
	}


}

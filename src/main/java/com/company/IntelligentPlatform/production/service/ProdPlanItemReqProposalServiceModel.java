package com.company.IntelligentPlatform.production.service;

import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdPlanItemReqProposalServiceModel extends ServiceModule {
	
	@IServiceModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME)
	protected List<ProductionPlanItemServiceModel> subProductionPlanItemList;
	
	@IServiceModuleFieldConfig(nodeName = ProdPlanItemReqProposal.NODENAME, nodeInstId = ProdPlanItemReqProposal.NODENAME)
	protected ProdPlanItemReqProposal prodPlanItemReqProposal;

	public List<ProductionPlanItemServiceModel> getSubProductionPlanItemList() {
		return subProductionPlanItemList;
	}

	public void setSubProductionPlanItemList(
			List<ProductionPlanItemServiceModel> subProductionPlanItemList) {
		this.subProductionPlanItemList = subProductionPlanItemList;
	}

	public ProdPlanItemReqProposal getProdPlanItemReqProposal() {
		return prodPlanItemReqProposal;
	}

	public void setProdPlanItemReqProposal(
			ProdPlanItemReqProposal prodPlanItemReqProposal) {
		this.prodPlanItemReqProposal = prodPlanItemReqProposal;
	}

}

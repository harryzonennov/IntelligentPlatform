package com.company.IntelligentPlatform.production.dto;

import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdPlanItemReqProposalServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdPlanItemReqProposal.NODENAME, nodeInstId = ProdPlanItemReqProposal.NODENAME)
	protected ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME)
	protected List<ProductionPlanItemServiceUIModel> productionPlanItemUIModelList;

	public ProdPlanItemReqProposalUIModel getProdPlanItemReqProposalUIModel() {
		return prodPlanItemReqProposalUIModel;
	}

	public void setProdPlanItemReqProposalUIModel(
			ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel) {
		this.prodPlanItemReqProposalUIModel = prodPlanItemReqProposalUIModel;
	}

	public List<ProductionPlanItemServiceUIModel> getProductionPlanItemUIModelList() {
		return productionPlanItemUIModelList;
	}

	public void setProductionPlanItemUIModelList(
			List<ProductionPlanItemServiceUIModel> productionPlanItemUIModelList) {
		this.productionPlanItemUIModelList = productionPlanItemUIModelList;
	}

}

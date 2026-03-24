package com.company.IntelligentPlatform.production.dto;

import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdOrderItemReqProposalServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderItemReqProposal.NODENAME, nodeInstId = ProdOrderItemReqProposal.NODENAME)
	protected ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItem.NODENAME, nodeInstId = ProductionOrderItem.NODENAME)
	protected List<ProductionOrderItemServiceUIModel> productionOrderItemUIModelList;

	public ProdOrderItemReqProposalUIModel getProdOrderItemReqProposalUIModel() {
		return prodOrderItemReqProposalUIModel;
	}

	public void setProdOrderItemReqProposalUIModel(
			ProdOrderItemReqProposalUIModel prodOrderItemReqProposalUIModel) {
		this.prodOrderItemReqProposalUIModel = prodOrderItemReqProposalUIModel;
	}

	public List<ProductionOrderItemServiceUIModel> getProductionOrderItemUIModelList() {
		return productionOrderItemUIModelList;
	}

	public void setProductionOrderItemUIModelList(
			List<ProductionOrderItemServiceUIModel> productionOrderItemUIModelList) {
		this.productionOrderItemUIModelList = productionOrderItemUIModelList;
	}

}

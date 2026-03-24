package com.company.IntelligentPlatform.production.dto;


import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrderItemParty;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProductionOrderItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItem.NODENAME, nodeInstId = ProductionOrderItem.NODENAME)
	protected ProductionOrderItemUIModel productionOrderItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProdOrderItemReqProposal.NODENAME, nodeInstId = ProdOrderItemReqProposal.NODENAME)
	protected List<ProdOrderItemReqProposalServiceUIModel> prodOrderItemReqProposalUIModelList = new ArrayList<>();


	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId = ProductionOrderItemParty.PARTY_NODEINST_PUR_ORG)
	protected ProductionOrderItemPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId =
			ProductionOrderItemParty.PARTY_NODEINST_PROD_ORG)
	protected ProductionOrderItemPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId =
			ProductionOrderItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected ProductionOrderItemPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId =
			ProductionOrderItemParty.PARTY_NODEINST_SOLD_ORG)
	protected ProductionOrderItemPartyUIModel salesOrganizationParty;

	public ProductionOrderItemUIModel getProductionOrderItemUIModel() {
		return this.productionOrderItemUIModel;
	}

	public void setProductionOrderItemUIModel(
			ProductionOrderItemUIModel productionOrderItemUIModel) {
		this.productionOrderItemUIModel = productionOrderItemUIModel;
	}

	public List<ProdOrderItemReqProposalServiceUIModel> getProdOrderItemReqProposalUIModelList() {
		return prodOrderItemReqProposalUIModelList;
	}

	public void setProdOrderItemReqProposalUIModelList(
			List<ProdOrderItemReqProposalServiceUIModel> prodOrderItemReqProposalUIModelList) {
		this.prodOrderItemReqProposalUIModelList = prodOrderItemReqProposalUIModelList;
	}

}

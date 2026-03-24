package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.production.model.ProductionPlanItemParty;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProductionPlanItemServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME)
	protected ProductionPlanItemUIModel productionPlanItemUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId = ProductionPlanItemParty.PARTY_NODEINST_PUR_ORG)
	protected ProductionPlanItemPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId =
			ProductionPlanItemParty.PARTY_NODEINST_PROD_ORG)
	protected ProductionPlanItemPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId =
			ProductionPlanItemParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected ProductionPlanItemPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId =
			ProductionPlanItemParty.PARTY_NODEINST_SOLD_ORG)
	protected ProductionPlanItemPartyUIModel salesOrganizationParty;
	
	@IServiceUIModuleFieldConfig(nodeName = ProdPlanItemReqProposal.NODENAME, nodeInstId = ProdPlanItemReqProposal.NODENAME)
	protected List<ProdPlanItemReqProposalServiceUIModel> prodPlanItemReqProposalUIModelList = new ArrayList<>();

	public ProductionPlanItemUIModel getProductionPlanItemUIModel() {
		return this.productionPlanItemUIModel;
	}

	public void setProductionPlanItemUIModel(
			ProductionPlanItemUIModel productionPlanItemUIModel) {
		this.productionPlanItemUIModel = productionPlanItemUIModel;
	}

	public List<ProdPlanItemReqProposalServiceUIModel> getProdPlanItemReqProposalUIModelList() {
		return prodPlanItemReqProposalUIModelList;
	}

	public void setProdPlanItemReqProposalUIModelList(
			List<ProdPlanItemReqProposalServiceUIModel> prodPlanItemReqProposalUIModelList) {
		this.prodPlanItemReqProposalUIModelList = prodPlanItemReqProposalUIModelList;
	}

	public ProductionPlanItemPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProductionPlanItemPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProductionPlanItemPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProductionPlanItemPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProductionPlanItemPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProductionPlanItemPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProductionPlanItemPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProductionPlanItemPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

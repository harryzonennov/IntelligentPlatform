package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPlanItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.production.model.ProductionPlanItemParty;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductionPlanItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME)
	protected ProductionPlanItem productionPlanItem;
	
	@IServiceModuleFieldConfig(nodeName = ProdPlanItemReqProposal.NODENAME, nodeInstId = ProdPlanItemReqProposal.NODENAME)
	protected List<ProdPlanItemReqProposalServiceModel> prodPlanItemReqProposalList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId = ProductionPlanItemParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanItemParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId =
			ProductionPlanItemParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanItemParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId =
			ProductionPlanItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanItemParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItemParty.NODENAME, nodeInstId =
			ProductionPlanItemParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanItemParty salesOrganizationParty;

	public ProductionPlanItem getProductionPlanItem() {
		return this.productionPlanItem;
	}

	public void setProductionPlanItem(ProductionPlanItem productionPlanItem) {
		this.productionPlanItem = productionPlanItem;
	}

	public List<ProdPlanItemReqProposalServiceModel> getProdPlanItemReqProposalList() {
		return prodPlanItemReqProposalList;
	}

	public void setProdPlanItemReqProposalList(
			List<ProdPlanItemReqProposalServiceModel> prodPlanItemReqProposalList) {
		this.prodPlanItemReqProposalList = prodPlanItemReqProposalList;
	}

	public ProductionPlanItemParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProductionPlanItemParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProductionPlanItemParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProductionPlanItemParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProductionPlanItemParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProductionPlanItemParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProductionPlanItemParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProductionPlanItemParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrderItemParty;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductionOrderItemServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductionOrderItem.NODENAME, nodeInstId = ProductionOrderItem.NODENAME)
	protected ProductionOrderItem productionOrderItem;
	
	@IServiceModuleFieldConfig(nodeName = ProdOrderItemReqProposal.NODENAME, nodeInstId = ProdOrderItemReqProposal.NODENAME)
	protected List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId = ProductionOrderItemParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderItemParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId =
			ProductionOrderItemParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderItemParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId =
			ProductionOrderItemParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderItemParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderItemParty.NODENAME, nodeInstId =
			ProductionOrderItemParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderItemParty salesOrganizationParty;

	public ProductionOrderItem getProductionOrderItem() {
		return this.productionOrderItem;
	}

	public void setProductionOrderItem(ProductionOrderItem productionOrderItem) {
		this.productionOrderItem = productionOrderItem;
	}

	public List<ProdOrderItemReqProposalServiceModel> getProdOrderItemReqProposalList() {
		return prodOrderItemReqProposalList;
	}

	public void setProdOrderItemReqProposalList(
			List<ProdOrderItemReqProposalServiceModel> prodOrderItemReqProposalList) {
		this.prodOrderItemReqProposalList = prodOrderItemReqProposalList;
	}

	public ProductionOrderItemParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProductionOrderItemParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProductionOrderItemParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProductionOrderItemParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProductionOrderItemParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProductionOrderItemParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProductionOrderItemParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProductionOrderItemParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

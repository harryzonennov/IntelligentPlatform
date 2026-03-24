package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingOrderParty;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProdPickingOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProdPickingOrder.NODENAME, nodeInstId = ProdPickingOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected ProdPickingOrder prodPickingOrder;

	@IServiceModuleFieldConfig(nodeName = ProdPickingRefOrderItem.NODENAME, nodeInstId = ProdPickingRefOrderItem.NODENAME)
	protected List<ProdPickingRefOrderItemServiceModel> prodPickingRefOrderItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId = ProdPickingOrderParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProdPickingOrderParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId =
			ProdPickingOrderParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProdPickingOrderParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId =
			ProdPickingOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProdPickingOrderParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId =
			ProdPickingOrderParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProdPickingOrderParty salesOrganizationParty;

	public ProdPickingOrder getProdPickingOrder() {
		return this.prodPickingOrder;
	}

	public void setProdPickingOrder(ProdPickingOrder prodPickingOrder) {
		this.prodPickingOrder = prodPickingOrder;
	}

	public List<ProdPickingRefOrderItemServiceModel> getProdPickingRefOrderItemList() {
		return prodPickingRefOrderItemList;
	}

	public void setProdPickingRefOrderItemList(
			List<ProdPickingRefOrderItemServiceModel> prodPickingRefOrderItemList) {
		this.prodPickingRefOrderItemList = prodPickingRefOrderItemList;
	}

	public ProdPickingOrderParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProdPickingOrderParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProdPickingOrderParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProdPickingOrderParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProdPickingOrderParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProdPickingOrderParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProdPickingOrderParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProdPickingOrderParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

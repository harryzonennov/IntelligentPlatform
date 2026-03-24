package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.service.ProdPickingOrderManager;
import com.company.IntelligentPlatform.production.model.ProdPickingOrderActionNode;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingOrderParty;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class ProdPickingOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrder.NODENAME, nodeInstId = ProdPickingOrder.SENAME, convToUIMethod = ProdPickingOrderManager.METHOD_ConvProdPickingOrderToUI, convUIToMethod = ProdPickingOrderManager.METHOD_ConvUIToProdPickingOrder)
	protected ProdPickingOrderUIModel prodPickingOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderActionNode.NODENAME, nodeInstId =
			ProdPickingOrderActionNode.NODEINST_ACTION_APPROVE)
	protected ProdPickingOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderActionNode.NODENAME, nodeInstId =
			ProdPickingOrderActionNode.NODEINST_ACTION_INPROCESS)
	protected ProdPickingOrderActionNodeUIModel inProcessBy;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderActionNode.NODENAME, nodeInstId =
			ProdPickingOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected ProdPickingOrderActionNodeUIModel deliveryDoneBy;
	
	@IServiceUIModuleFieldConfig(nodeName = ProdPickingRefOrderItem.NODENAME, nodeInstId = ProdPickingRefOrderItem.NODENAME, convToUIMethod = ProdPickingOrderManager.METHOD_ConvProdPickingRefOrderItemToUI, convUIToMethod = ProdPickingOrderManager.METHOD_ConvUIToProdPickingRefOrderItem)
	protected List<ProdPickingRefOrderItemServiceUIModel> prodPickingRefOrderItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId = ProdPickingOrderParty.PARTY_NODEINST_PUR_ORG)
	protected ProdPickingOrderPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId =
			ProdPickingOrderParty.PARTY_NODEINST_PROD_ORG)
	protected ProdPickingOrderPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId =
			ProdPickingOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected ProdPickingOrderPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = ProdPickingOrderParty.NODENAME, nodeInstId =
			ProdPickingOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected ProdPickingOrderPartyUIModel salesOrganizationParty;

	public ProdPickingOrderUIModel getProdPickingOrderUIModel() {
		return this.prodPickingOrderUIModel;
	}

	public void setProdPickingOrderUIModel(
			ProdPickingOrderUIModel prodPickingOrderUIModel) {
		this.prodPickingOrderUIModel = prodPickingOrderUIModel;
	}

	public List<ProdPickingRefOrderItemServiceUIModel> getProdPickingRefOrderItemUIModelList() {
		return prodPickingRefOrderItemUIModelList;
	}

	public void setProdPickingRefOrderItemUIModelList(
			List<ProdPickingRefOrderItemServiceUIModel> prodPickingRefOrderItemUIModelList) {
		this.prodPickingRefOrderItemUIModelList = prodPickingRefOrderItemUIModelList;
	}

	public ProdPickingOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ProdPickingOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public ProdPickingOrderActionNodeUIModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(ProdPickingOrderActionNodeUIModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public ProdPickingOrderActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(ProdPickingOrderActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public ProdPickingOrderPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProdPickingOrderPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProdPickingOrderPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProdPickingOrderPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProdPickingOrderPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProdPickingOrderPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProdPickingOrderPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProdPickingOrderPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

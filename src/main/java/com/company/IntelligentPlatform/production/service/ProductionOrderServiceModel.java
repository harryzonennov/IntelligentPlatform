package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductionOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductionOrder.NODENAME, nodeInstId = ProductionOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected ProductionOrder productionOrder;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId = ProductionOrderActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId = ProductionOrderActionNode.NODEINST_ACTION_COUNTAPPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode countApprovedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode revokeSubmittedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_INPRODUCTION, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode inProductionBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderActionNode.NODENAME, nodeInstId =
			ProductionOrderActionNode.NODEINST_ACTION_FINISHED, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionOrderActionNode finishedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderParty.NODENAME, nodeInstId = ProductionOrderParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderParty.NODENAME, nodeInstId =
			ProductionOrderParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderParty.NODENAME, nodeInstId =
			ProductionOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderParty.NODENAME, nodeInstId =
			ProductionOrderParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionOrderParty salesOrganizationParty;

	@IServiceModuleFieldConfig(nodeName = ProductionOrderItem.NODENAME, nodeInstId = ProductionOrderItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<ProductionOrderItemServiceModel> productionOrderItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProdOrderSupplyWarehouse.NODENAME, nodeInstId = ProdOrderSupplyWarehouse.NODENAME)
	protected List<ServiceEntityNode> prodOrderSupplyWarehouseList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProductionOrderAttachment.NODENAME, nodeInstId = ProductionOrderAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> prodOrderAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProdOrderTargetMatItem.NODENAME, nodeInstId = ProdOrderTargetMatItem.NODENAME)
	protected List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList = new ArrayList<>();

	public List<ProductionOrderItemServiceModel> getProductionOrderItemList() {
		return this.productionOrderItemList;
	}

	public void setProductionOrderItemList(
			List<ProductionOrderItemServiceModel> productionOrderItemList) {
		this.productionOrderItemList = productionOrderItemList;
	}

	public List<ServiceEntityNode> getProdOrderSupplyWarehouseList() {
		return this.prodOrderSupplyWarehouseList;
	}

	public void setProdOrderSupplyWarehouseList(
			List<ServiceEntityNode> prodOrderSupplyWarehouseList) {
		this.prodOrderSupplyWarehouseList = prodOrderSupplyWarehouseList;
	}

	public ProductionOrder getProductionOrder() {
		return this.productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public List<ServiceEntityNode> getProdOrderAttachmentList() {
		return prodOrderAttachmentList;
	}

	public void setProdOrderAttachmentList(
			List<ServiceEntityNode> prodOrderAttachmentList) {
		this.prodOrderAttachmentList = prodOrderAttachmentList;
	}

	public List<ProdOrderTargetMatItemServiceModel> getProdOrderTargetMatItemList() {
		return prodOrderTargetMatItemList;
	}

	public void setProdOrderTargetMatItemList(
			List<ProdOrderTargetMatItemServiceModel> prodOrderTargetMatItemList) {
		this.prodOrderTargetMatItemList = prodOrderTargetMatItemList;
	}

	public ProductionOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ProductionOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public ProductionOrderActionNode getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(ProductionOrderActionNode countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public ProductionOrderActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(ProductionOrderActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public ProductionOrderActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(ProductionOrderActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public ProductionOrderActionNode getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(ProductionOrderActionNode revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public ProductionOrderActionNode getInProductionBy() {
		return inProductionBy;
	}

	public void setInProductionBy(ProductionOrderActionNode inProductionBy) {
		this.inProductionBy = inProductionBy;
	}

	public ProductionOrderActionNode getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(ProductionOrderActionNode finishedBy) {
		this.finishedBy = finishedBy;
	}

	public ProductionOrderParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProductionOrderParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProductionOrderParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProductionOrderParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProductionOrderParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProductionOrderParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProductionOrderParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProductionOrderParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ProductionPlanServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ProductionPlan.NODENAME, nodeInstId = ProductionPlan.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected ProductionPlan productionPlan;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId = ProductionPlanActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId = ProductionPlanActionNode.NODEINST_ACTION_COUNTAPPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode countApprovedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_REVOKE_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode revokeSubmittedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_INPRODUCTION, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode inProductionBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanActionNode.NODENAME, nodeInstId =
			ProductionPlanActionNode.NODEINST_ACTION_FINISHED, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected ProductionPlanActionNode finishedBy;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId = ProductionPlanParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId =
			ProductionPlanParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId =
			ProductionPlanParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanParty.NODENAME, nodeInstId =
			ProductionPlanParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected ProductionPlanParty salesOrganizationParty;

	@IServiceModuleFieldConfig(nodeName = ProductionPlanItem.NODENAME, nodeInstId = ProductionPlanItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<ProductionPlanItemServiceModel> productionPlanItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProdPlanTargetMatItem.NODENAME, nodeInstId = ProdPlanTargetMatItem.NODENAME)
	protected List<ProdPlanTargetMatItemServiceModel> prodPlanTargetMatItemList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = ProdPlanSupplyWarehouse.NODENAME, nodeInstId = ProdPlanSupplyWarehouse.NODENAME)
	protected List<ServiceEntityNode> prodPlanSupplyWarehouseList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ProductionPlanAttachment.NODENAME, nodeInstId = ProductionPlanAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> prodPlanAttachmentList = new ArrayList<>();

	public ProductionPlanActionNode getInProductionBy() {
		return inProductionBy;
	}

	public void setInProductionBy(ProductionPlanActionNode inProductionBy) {
		this.inProductionBy = inProductionBy;
	}

	public ProductionPlanActionNode getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(ProductionPlanActionNode finishedBy) {
		this.finishedBy = finishedBy;
	}

	public List<ProductionPlanItemServiceModel> getProductionPlanItemList() {
		return this.productionPlanItemList;
	}

	public void setProductionPlanItemList(
			List<ProductionPlanItemServiceModel> productionPlanItemList) {
		this.productionPlanItemList = productionPlanItemList;
	}

	public List<ProdPlanTargetMatItemServiceModel> getProdPlanTargetMatItemList() {
		return prodPlanTargetMatItemList;
	}

	public void setProdPlanTargetMatItemList(
			List<ProdPlanTargetMatItemServiceModel> prodPlanTargetMatItemList) {
		this.prodPlanTargetMatItemList = prodPlanTargetMatItemList;
	}

	public List<ServiceEntityNode> getProdPlanSupplyWarehouseList() {
		return prodPlanSupplyWarehouseList;
	}

	public void setProdPlanSupplyWarehouseList(
			List<ServiceEntityNode> prodPlanSupplyWarehouseList) {
		this.prodPlanSupplyWarehouseList = prodPlanSupplyWarehouseList;
	}

	public ProductionPlan getProductionPlan() {
		return this.productionPlan;
	}

	public void setProductionPlan(ProductionPlan productionPlan) {
		this.productionPlan = productionPlan;
	}

	public List<ServiceEntityNode> getProdPlanAttachmentList() {
		return prodPlanAttachmentList;
	}

	public void setProdPlanAttachmentList(
			List<ServiceEntityNode> prodPlanAttachmentList) {
		this.prodPlanAttachmentList = prodPlanAttachmentList;
	}

	public ProductionPlanActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(ProductionPlanActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public ProductionPlanActionNode getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(ProductionPlanActionNode countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public ProductionPlanActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(ProductionPlanActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public ProductionPlanActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(ProductionPlanActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public ProductionPlanActionNode getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(ProductionPlanActionNode revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public ProductionPlanParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(ProductionPlanParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public ProductionPlanParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(ProductionPlanParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public ProductionPlanParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(ProductionPlanParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public ProductionPlanParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(ProductionPlanParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

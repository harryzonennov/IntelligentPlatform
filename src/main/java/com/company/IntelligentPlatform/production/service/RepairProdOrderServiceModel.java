package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RepairProdOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RepairProdOrder.NODENAME, nodeInstId = RepairProdOrder.SENAME)
	protected RepairProdOrder repairProdOrder;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId = RepairProdOrderActionNode.NODEINST_ACTION_APPROVE)
	protected RepairProdOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId = RepairProdOrderActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected RepairProdOrderActionNode countApprovedBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected RepairProdOrderActionNode rejectApprovedBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected RepairProdOrderActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected RepairProdOrderActionNode revokeSubmittedBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_INPRODUCTION)
	protected RepairProdOrderActionNode inProductionBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_FINISHED)
	protected RepairProdOrderActionNode finishedBy;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId = RepairProdOrderParty.PARTY_NODEINST_PUR_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected RepairProdOrderParty purchaseOrgParty;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId =
			RepairProdOrderParty.PARTY_NODEINST_PROD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected RepairProdOrderParty productionOrgParty;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId =
			RepairProdOrderParty.PARTY_NODEINST_SOLD_CUSTOMER,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected RepairProdOrderParty corporateCustomerParty;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId =
			RepairProdOrderParty.PARTY_NODEINST_SOLD_ORG,
			docNodeCategory = IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected RepairProdOrderParty salesOrganizationParty;

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderItem.NODENAME, nodeInstId = RepairProdOrderItem.NODENAME)
	protected List<RepairProdOrderItemServiceModel> repairProdOrderItemList = new ArrayList<RepairProdOrderItemServiceModel>();

	@IServiceModuleFieldConfig(nodeName = RepairProdSupplyWarehouse.NODENAME, nodeInstId = RepairProdSupplyWarehouse.NODENAME)
	protected List<ServiceEntityNode> repairProdSupplyWarehouseList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = RepairProdOrderAttachment.NODENAME, nodeInstId = RepairProdOrderAttachment.NODENAME)
	protected List<ServiceEntityNode> prodOrderAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = RepairProdTargetMatItem.NODENAME, nodeInstId = RepairProdTargetMatItem.NODENAME)
	protected List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList = new ArrayList<>();

	public List<RepairProdOrderItemServiceModel> getRepairProdOrderItemList() {
		return this.repairProdOrderItemList;
	}

	public void setRepairProdOrderItemList(
			List<RepairProdOrderItemServiceModel> repairProdOrderItemList) {
		this.repairProdOrderItemList = repairProdOrderItemList;
	}

	public List<ServiceEntityNode> getRepairProdSupplyWarehouseList() {
		return this.repairProdSupplyWarehouseList;
	}

	public void setRepairProdSupplyWarehouseList(
			List<ServiceEntityNode> repairProdSupplyWarehouseList) {
		this.repairProdSupplyWarehouseList = repairProdSupplyWarehouseList;
	}

	public RepairProdOrder getRepairProdOrder() {
		return this.repairProdOrder;
	}

	public void setRepairProdOrder(RepairProdOrder repairProdOrder) {
		this.repairProdOrder = repairProdOrder;
	}

	public List<ServiceEntityNode> getProdOrderAttachmentList() {
		return prodOrderAttachmentList;
	}

	public void setProdOrderAttachmentList(
			List<ServiceEntityNode> prodOrderAttachmentList) {
		this.prodOrderAttachmentList = prodOrderAttachmentList;
	}

	public List<RepairProdTargetMatItemServiceModel> getRepairProdTargetMatItemList() {
		return repairProdTargetMatItemList;
	}

	public void setRepairProdTargetMatItemList(
			List<RepairProdTargetMatItemServiceModel> repairProdTargetMatItemList) {
		this.repairProdTargetMatItemList = repairProdTargetMatItemList;
	}

	public RepairProdOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(RepairProdOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public RepairProdOrderActionNode getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(RepairProdOrderActionNode countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public RepairProdOrderActionNode getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(RepairProdOrderActionNode rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public RepairProdOrderActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(RepairProdOrderActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public RepairProdOrderActionNode getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(RepairProdOrderActionNode revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public RepairProdOrderActionNode getInProductionBy() {
		return inProductionBy;
	}

	public void setInProductionBy(RepairProdOrderActionNode inProductionBy) {
		this.inProductionBy = inProductionBy;
	}

	public RepairProdOrderActionNode getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(RepairProdOrderActionNode finishedBy) {
		this.finishedBy = finishedBy;
	}

	public RepairProdOrderParty getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(RepairProdOrderParty purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public RepairProdOrderParty getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(RepairProdOrderParty productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public RepairProdOrderParty getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(RepairProdOrderParty corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public RepairProdOrderParty getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(RepairProdOrderParty salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

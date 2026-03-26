package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class RepairProdOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderItem.NODENAME, nodeInstId = RepairProdOrderItem.NODENAME)
	protected List<RepairProdOrderItemServiceUIModel> repairProdOrderItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = RepairProdSupplyWarehouse.NODENAME, nodeInstId = RepairProdSupplyWarehouse.NODENAME)
	protected List<RepairProdSupplyWarehouseUIModel> repairProdSupplyWarehouseUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderAttachment.NODENAME, nodeInstId = RepairProdOrderAttachment.NODENAME)
	protected List<RepairProdOrderAttachmentUIModel> repairProdOrderAttachmentList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = RepairProdTargetMatItem.NODENAME, nodeInstId = RepairProdTargetMatItem.NODENAME)
	protected List<RepairProdTargetMatItemServiceUIModel> repairProdTargetMatItemList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_APPROVE)
	protected RepairProdOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId = RepairProdOrderActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected RepairProdOrderActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected RepairProdOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected RepairProdOrderActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected RepairProdOrderActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_INPRODUCTION)
	protected RepairProdOrderActionNodeUIModel inProductionBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderActionNode.NODENAME, nodeInstId =
			RepairProdOrderActionNode.NODEINST_ACTION_FINISHED)
	protected RepairProdOrderActionNodeUIModel finishedBy;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrder.NODENAME, nodeInstId = RepairProdOrder.SENAME)
	protected RepairProdOrderUIModel repairProdOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId = RepairProdOrderParty.PARTY_NODEINST_PUR_ORG)
	protected RepairProdOrderPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId =
			RepairProdOrderParty.PARTY_NODEINST_PROD_ORG)
	protected RepairProdOrderPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId =
			RepairProdOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected RepairProdOrderPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = RepairProdOrderParty.NODENAME, nodeInstId =
			RepairProdOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected RepairProdOrderPartyUIModel salesOrganizationParty;

	public List<RepairProdOrderItemServiceUIModel> getRepairProdOrderItemUIModelList() {
		return repairProdOrderItemUIModelList;
	}

	public void setRepairProdOrderItemUIModelList(
			List<RepairProdOrderItemServiceUIModel> repairProdOrderItemUIModelList) {
		this.repairProdOrderItemUIModelList = repairProdOrderItemUIModelList;
	}

	public List<RepairProdSupplyWarehouseUIModel> getRepairProdSupplyWarehouseUIModelList() {
		return repairProdSupplyWarehouseUIModelList;
	}

	public void setRepairProdSupplyWarehouseUIModelList(
			List<RepairProdSupplyWarehouseUIModel> repairProdSupplyWarehouseUIModelList) {
		this.repairProdSupplyWarehouseUIModelList = repairProdSupplyWarehouseUIModelList;
	}

	public RepairProdOrderUIModel getRepairProdOrderUIModel() {
		return repairProdOrderUIModel;
	}

	public void setRepairProdOrderUIModel(
			RepairProdOrderUIModel repairProdOrderUIModel) {
		this.repairProdOrderUIModel = repairProdOrderUIModel;
	}

	public List<RepairProdOrderAttachmentUIModel> getRepairProdOrderAttachmentList() {
		return repairProdOrderAttachmentList;
	}

	public void setRepairProdOrderAttachmentList(
			List<RepairProdOrderAttachmentUIModel> repairProdOrderAttachmentList) {
		this.repairProdOrderAttachmentList = repairProdOrderAttachmentList;
	}

	public List<RepairProdTargetMatItemServiceUIModel> getRepairProdTargetMatItemList() {
		return repairProdTargetMatItemList;
	}

	public void setRepairProdTargetMatItemList(List<RepairProdTargetMatItemServiceUIModel> repairProdTargetMatItemList) {
		this.repairProdTargetMatItemList = repairProdTargetMatItemList;
	}

	public RepairProdOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(RepairProdOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public RepairProdOrderActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(RepairProdOrderActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public RepairProdOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(RepairProdOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public RepairProdOrderActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(RepairProdOrderActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public RepairProdOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(RepairProdOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public RepairProdOrderActionNodeUIModel getInProductionBy() {
		return inProductionBy;
	}

	public void setInProductionBy(RepairProdOrderActionNodeUIModel inProductionBy) {
		this.inProductionBy = inProductionBy;
	}

	public RepairProdOrderActionNodeUIModel getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(RepairProdOrderActionNodeUIModel finishedBy) {
		this.finishedBy = finishedBy;
	}

	public RepairProdOrderPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(RepairProdOrderPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public RepairProdOrderPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(RepairProdOrderPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public RepairProdOrderPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(RepairProdOrderPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public RepairProdOrderPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(RepairProdOrderPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

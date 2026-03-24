package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InventoryTransferOrderManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class InventoryTransferOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrder.NODENAME, nodeInstId = InventoryTransferOrder.SENAME, convToUIMethod = InventoryTransferOrderManager.METHOD_ConvInventoryTransferOrderToUI, convUIToMethod = InventoryTransferOrderManager.METHOD_ConvUIToInventoryTransferOrder)
	protected InventoryTransferOrderUIModel inventoryTransferOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected InventoryTransferOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected InventoryTransferOrderActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_APPROVE)
	protected InventoryTransferOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected InventoryTransferOrderActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId = InventoryTransferOrderActionNode.NODEINST_ACTION_COUNTAPPROVE)
	protected InventoryTransferOrderActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderActionNode.NODENAME, nodeInstId =
			InventoryTransferOrderActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected InventoryTransferOrderActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId = InventoryTransferOrderParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected InventoryTransferOrderPartyUIModel corporateSupplierParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId = InventoryTransferOrderParty.PARTY_NODEINST_PUR_ORG)
	protected InventoryTransferOrderPartyUIModel purchaseOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId =
			InventoryTransferOrderParty.PARTY_NODEINST_PROD_ORG)
	protected InventoryTransferOrderPartyUIModel productionOrgParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId =
			InventoryTransferOrderParty.PARTY_NODEINST_SOLD_CUSTOMER)
	protected InventoryTransferOrderPartyUIModel corporateCustomerParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderParty.NODENAME, nodeInstId =
			InventoryTransferOrderParty.PARTY_NODEINST_SOLD_ORG)
	protected InventoryTransferOrderPartyUIModel salesOrganizationParty;

	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferItem.NODENAME, nodeInstId = InventoryTransferItem.NODENAME)
	protected List<InventoryTransferItemServiceUIModel> inventoryTransferItemUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = InventoryTransferOrderAttachment.NODENAME, nodeInstId = InventoryTransferOrderAttachment.NODENAME)
	protected List<InventoryTransferOrderAttachmentUIModel> inventoryTransferOrderAttachmentUIModelList = new ArrayList<>();

	public InventoryTransferOrderUIModel getInventoryTransferOrderUIModel() {
		return this.inventoryTransferOrderUIModel;
	}

	public void setInventoryTransferOrderUIModel(
			InventoryTransferOrderUIModel inventoryTransferOrderUIModel) {
		this.inventoryTransferOrderUIModel = inventoryTransferOrderUIModel;
	}

	public List<InventoryTransferItemServiceUIModel> getInventoryTransferItemUIModelList() {
		return inventoryTransferItemUIModelList;
	}

	public void setInventoryTransferItemUIModelList(
			List<InventoryTransferItemServiceUIModel> inventoryTransferItemUIModelList) {
		this.inventoryTransferItemUIModelList = inventoryTransferItemUIModelList;
	}

	public List<InventoryTransferOrderAttachmentUIModel> getInventoryTransferOrderAttachmentUIModelList() {
		return inventoryTransferOrderAttachmentUIModelList;
	}

	public void setInventoryTransferOrderAttachmentUIModelList(
			List<InventoryTransferOrderAttachmentUIModel> inventoryTransferOrderAttachmentUIModelList) {
		this.inventoryTransferOrderAttachmentUIModelList = inventoryTransferOrderAttachmentUIModelList;
	}

	public InventoryTransferOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(InventoryTransferOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public InventoryTransferOrderActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(InventoryTransferOrderActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public InventoryTransferOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InventoryTransferOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InventoryTransferOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(InventoryTransferOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public InventoryTransferOrderActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(InventoryTransferOrderActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public InventoryTransferOrderActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(InventoryTransferOrderActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public InventoryTransferOrderPartyUIModel getCorporateSupplierParty() {
		return corporateSupplierParty;
	}

	public void setCorporateSupplierParty(InventoryTransferOrderPartyUIModel corporateSupplierParty) {
		this.corporateSupplierParty = corporateSupplierParty;
	}

	public InventoryTransferOrderPartyUIModel getPurchaseOrgParty() {
		return purchaseOrgParty;
	}

	public void setPurchaseOrgParty(InventoryTransferOrderPartyUIModel purchaseOrgParty) {
		this.purchaseOrgParty = purchaseOrgParty;
	}

	public InventoryTransferOrderPartyUIModel getProductionOrgParty() {
		return productionOrgParty;
	}

	public void setProductionOrgParty(InventoryTransferOrderPartyUIModel productionOrgParty) {
		this.productionOrgParty = productionOrgParty;
	}

	public InventoryTransferOrderPartyUIModel getCorporateCustomerParty() {
		return corporateCustomerParty;
	}

	public void setCorporateCustomerParty(InventoryTransferOrderPartyUIModel corporateCustomerParty) {
		this.corporateCustomerParty = corporateCustomerParty;
	}

	public InventoryTransferOrderPartyUIModel getSalesOrganizationParty() {
		return salesOrganizationParty;
	}

	public void setSalesOrganizationParty(InventoryTransferOrderPartyUIModel salesOrganizationParty) {
		this.salesOrganizationParty = salesOrganizationParty;
	}
}

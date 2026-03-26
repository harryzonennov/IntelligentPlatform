package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseReturnOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrder.NODENAME, nodeInstId = PurchaseReturnOrder.SENAME)
	protected PurchaseReturnOrderUIModel purchaseReturnOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrderParty.NODENAME, nodeInstId = PurchaseReturnOrderParty.PARTY_NODEINST_PUR_ORG)
	protected PurchaseReturnOrderPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrderParty.NODENAME, nodeInstId = PurchaseReturnOrderParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected PurchaseReturnOrderPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrderActionNode.NODENAME, nodeInstId =
			SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE)
	protected PurchaseReturnOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrderActionNode.NODENAME, nodeInstId =
			PurchaseReturnOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected PurchaseReturnOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrderActionNode.NODENAME, nodeInstId =
			PurchaseReturnOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected PurchaseReturnOrderActionNodeUIModel rejectApprovedBy;

	// update this node manually
	protected PurchaseContractUIModel purchaseContractUIModel;

	// update this node manually
	protected PurchaseContractActionNodeUIModel purchaseDeliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnMaterialItem.NODENAME, nodeInstId = PurchaseReturnMaterialItem.NODENAME)
	protected List<PurchaseReturnMaterialItemServiceUIModel> purchaseReturnMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = PurchaseReturnOrderAttachment.NODENAME, nodeInstId = PurchaseReturnOrderAttachment.NODENAME)
	protected List<PurchaseReturnOrderAttachmentUIModel> purchaseReturnOrderAttachmentUIModelList = new ArrayList<>();

	public List<PurchaseReturnMaterialItemServiceUIModel> getPurchaseReturnMaterialItemUIModelList() {
		return purchaseReturnMaterialItemUIModelList;
	}

	public void setPurchaseReturnMaterialItemUIModelList(
			List<PurchaseReturnMaterialItemServiceUIModel> purchaseReturnMaterialItemUIModelList) {
		this.purchaseReturnMaterialItemUIModelList = purchaseReturnMaterialItemUIModelList;
	}

	public List<PurchaseReturnOrderAttachmentUIModel> getPurchaseReturnOrderAttachmentUIModelList() {
		return purchaseReturnOrderAttachmentUIModelList;
	}

	public void setPurchaseReturnOrderAttachmentUIModelList(
			List<PurchaseReturnOrderAttachmentUIModel> purchaseReturnOrderAttachmentUIModelList) {
		this.purchaseReturnOrderAttachmentUIModelList = purchaseReturnOrderAttachmentUIModelList;
	}

	public PurchaseReturnOrderUIModel getPurchaseReturnOrderUIModel() {
		return purchaseReturnOrderUIModel;
	}

	public void setPurchaseReturnOrderUIModel(
			PurchaseReturnOrderUIModel purchaseReturnOrderUIModel) {
		this.purchaseReturnOrderUIModel = purchaseReturnOrderUIModel;
	}

	public PurchaseReturnOrderPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(PurchaseReturnOrderPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public PurchaseReturnOrderPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(PurchaseReturnOrderPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public PurchaseContractUIModel getPurchaseContractUIModel() {
		return purchaseContractUIModel;
	}

	public void setPurchaseContractUIModel(PurchaseContractUIModel purchaseContractUIModel) {
		this.purchaseContractUIModel = purchaseContractUIModel;
	}

	public PurchaseContractActionNodeUIModel getPurchaseDeliveryDoneBy() {
		return purchaseDeliveryDoneBy;
	}

	public void setPurchaseDeliveryDoneBy(PurchaseContractActionNodeUIModel purchaseDeliveryDoneBy) {
		this.purchaseDeliveryDoneBy = purchaseDeliveryDoneBy;
	}

	public PurchaseReturnOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(PurchaseReturnOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public PurchaseReturnOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(PurchaseReturnOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public PurchaseReturnOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(PurchaseReturnOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}
}

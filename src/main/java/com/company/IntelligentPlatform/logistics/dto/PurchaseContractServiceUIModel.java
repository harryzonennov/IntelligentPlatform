package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class PurchaseContractServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContract.NODENAME, nodeInstId = PurchaseContract.SENAME)
	protected PurchaseContractUIModel purchaseContractUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractParty.NODENAME, nodeInstId = PurchaseContractParty.PARTY_NODEINST_PUR_ORG)
	protected PurchaseContractPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractParty.NODENAME, nodeInstId = PurchaseContractParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected PurchaseContractPartyUIModel purchaseFromSupplierUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME, nodeInstId =
			PurchaseContractActionNode.NODEINST_ACTION_APPROVE)
	protected PurchaseContractActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME, nodeInstId = PurchaseContractActionNode.NODEINST_ACTION_DELIVERY_DONE)
	protected PurchaseContractActionNodeUIModel deliveryDoneBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractActionNode.NODENAME, nodeInstId =
			PurchaseContractActionNode.NODEINST_ACTION_SUBMIT)
	protected PurchaseContractActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractMaterialItem.NODENAME, nodeInstId = PurchaseContractMaterialItem.NODENAME)
	protected List<PurchaseContractMaterialItemServiceUIModel> purchaseContractMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = PurchaseContractAttachment.NODENAME, nodeInstId = PurchaseContractAttachment.NODENAME)
	protected List<PurchaseContractAttachmentUIModel> purchaseContractAttachmentUIModelList = new ArrayList<>();

	public PurchaseContractUIModel getPurchaseContractUIModel() {
		return this.purchaseContractUIModel;
	}

	public void setPurchaseContractUIModel(
			PurchaseContractUIModel purchaseContractUIModel) {
		this.purchaseContractUIModel = purchaseContractUIModel;
	}

	public List<PurchaseContractMaterialItemServiceUIModel> getPurchaseContractMaterialItemUIModelList() {
		return this.purchaseContractMaterialItemUIModelList;
	}

	public void setPurchaseContractMaterialItemUIModelList(
			List<PurchaseContractMaterialItemServiceUIModel> purchaseContractMaterialItemUIModelList) {
		this.purchaseContractMaterialItemUIModelList = purchaseContractMaterialItemUIModelList;
	}

	public List<PurchaseContractAttachmentUIModel> getPurchaseContractAttachmentUIModelList() {
		return this.purchaseContractAttachmentUIModelList;
	}


	public PurchaseContractActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(PurchaseContractActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public PurchaseContractActionNodeUIModel getDeliveryDoneBy() {
		return deliveryDoneBy;
	}

	public void setDeliveryDoneBy(PurchaseContractActionNodeUIModel deliveryDoneBy) {
		this.deliveryDoneBy = deliveryDoneBy;
	}

	public PurchaseContractActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(PurchaseContractActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public PurchaseContractPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(PurchaseContractPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public PurchaseContractPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(PurchaseContractPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public void setPurchaseContractAttachmentUIModelList(
			List<PurchaseContractAttachmentUIModel> purchaseContractAttachmentUIModelList) {
		this.purchaseContractAttachmentUIModelList = purchaseContractAttachmentUIModelList;
	}

}

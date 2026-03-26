package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseRequestServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequest.NODENAME, nodeInstId = PurchaseRequest.SENAME)
	protected PurchaseRequestUIModel purchaseRequestUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestParty.NODENAME, nodeInstId = PurchaseRequestParty.PARTY_NODEINST_PUR_ORG)
	protected PurchaseRequestPartyUIModel purchaseToOrgUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestParty.NODENAME, nodeInstId = PurchaseRequestParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected PurchaseRequestPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestActionNode.NODENAME, nodeInstId = PurchaseRequestActionNode.NODEINST_ACTION_APPROVE)
	protected PurchaseRequestActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestActionNode.NODENAME, nodeInstId = PurchaseRequestActionNode.NODEINST_ACTION_INPROCESS)
	protected PurchaseRequestActionNodeUIModel inProcessBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestActionNode.NODENAME, nodeInstId =
			PurchaseRequestActionNode.NODEINST_ACTION_SUBMIT)
	protected PurchaseRequestActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestMaterialItem.NODENAME, nodeInstId = PurchaseRequestMaterialItem.NODENAME)
	protected List<PurchaseRequestMaterialItemServiceUIModel> purchaseRequestMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = PurchaseRequestAttachment.NODENAME, nodeInstId = PurchaseRequestAttachment.NODENAME)
	protected List<PurchaseRequestAttachmentUIModel> purchaseRequestAttachmentUIModelList = new ArrayList<>();

	public List<PurchaseRequestMaterialItemServiceUIModel> getPurchaseRequestMaterialItemUIModelList() {
		return purchaseRequestMaterialItemUIModelList;
	}

	public void setPurchaseRequestMaterialItemUIModelList(
			List<PurchaseRequestMaterialItemServiceUIModel> purchaseRequestMaterialItemUIModelList) {
		this.purchaseRequestMaterialItemUIModelList = purchaseRequestMaterialItemUIModelList;
	}

	public List<PurchaseRequestAttachmentUIModel> getPurchaseRequestAttachmentUIModelList() {
		return purchaseRequestAttachmentUIModelList;
	}

	public void setPurchaseRequestAttachmentUIModelList(
			List<PurchaseRequestAttachmentUIModel> purchaseRequestAttachmentUIModelList) {
		this.purchaseRequestAttachmentUIModelList = purchaseRequestAttachmentUIModelList;
	}

	public PurchaseRequestUIModel getPurchaseRequestUIModel() {
		return purchaseRequestUIModel;
	}

	public void setPurchaseRequestUIModel(
			PurchaseRequestUIModel purchaseRequestUIModel) {
		this.purchaseRequestUIModel = purchaseRequestUIModel;
	}

	public PurchaseRequestPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(PurchaseRequestPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public PurchaseRequestPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(PurchaseRequestPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public PurchaseRequestActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(PurchaseRequestActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public PurchaseRequestActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(PurchaseRequestActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public PurchaseRequestActionNodeUIModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(PurchaseRequestActionNodeUIModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}
}

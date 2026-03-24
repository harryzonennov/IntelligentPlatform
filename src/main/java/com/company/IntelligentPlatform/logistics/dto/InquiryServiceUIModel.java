package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.ServiceExtendUIModel;

@Component
public class InquiryServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = Inquiry.NODENAME, nodeInstId = Inquiry.SENAME)
	protected InquiryUIModel inquiryUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InquiryParty.NODENAME, nodeInstId = InquiryParty.PARTY_NODEINST_PUR_ORG)
	protected InquiryPartyUIModel purchaseToOrgUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InquiryParty.NODENAME, nodeInstId = InquiryParty.PARTY_NODEINST_PUR_SUPPLIER)
	protected InquiryPartyUIModel purchaseFromSupplierUIModel;

	@IServiceUIModuleFieldConfig(nodeName = InquiryActionNode.NODENAME, nodeInstId =
			InquiryActionNode.NODEINST_ACTION_APPROVE)
	protected InquiryActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = InquiryActionNode.NODENAME, nodeInstId = InquiryActionNode.NODEINST_ACTION_INPROCESS)
	protected InquiryActionNodeUIModel inProcessBy;

	@IServiceUIModuleFieldConfig(nodeName = InquiryActionNode.NODENAME, nodeInstId =
			InquiryActionNode.NODEINST_ACTION_SUBMIT)
	protected InquiryActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = InquiryMaterialItem.NODENAME, nodeInstId = InquiryMaterialItem.NODENAME)
	protected List<InquiryMaterialItemServiceUIModel> inquiryMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = InquiryAttachment.NODENAME, nodeInstId = InquiryAttachment.NODENAME)
	protected List<InquiryAttachmentUIModel> inquiryAttachmentUIModelList = new ArrayList<>();
	
	protected List<ServiceExtendUIModel> serviceExtendUIModelList;

	public InquiryUIModel getInquiryUIModel() {
		return this.inquiryUIModel;
	}

	public void setInquiryUIModel(
			InquiryUIModel inquiryUIModel) {
		this.inquiryUIModel = inquiryUIModel;
	}

	public List<InquiryMaterialItemServiceUIModel> getInquiryMaterialItemUIModelList() {
		return this.inquiryMaterialItemUIModelList;
	}

	public void setInquiryMaterialItemUIModelList(
			List<InquiryMaterialItemServiceUIModel> inquiryMaterialItemUIModelList) {
		this.inquiryMaterialItemUIModelList = inquiryMaterialItemUIModelList;
	}

	public List<InquiryAttachmentUIModel> getInquiryAttachmentUIModelList() {
		return this.inquiryAttachmentUIModelList;
	}

	public void setInquiryAttachmentUIModelList(
			List<InquiryAttachmentUIModel> inquiryAttachmentUIModelList) {
		this.inquiryAttachmentUIModelList = inquiryAttachmentUIModelList;
	}

	public InquiryActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InquiryActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}
	public InquiryActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public InquiryPartyUIModel getPurchaseToOrgUIModel() {
		return purchaseToOrgUIModel;
	}

	public void setPurchaseToOrgUIModel(InquiryPartyUIModel purchaseToOrgUIModel) {
		this.purchaseToOrgUIModel = purchaseToOrgUIModel;
	}

	public InquiryPartyUIModel getPurchaseFromSupplierUIModel() {
		return purchaseFromSupplierUIModel;
	}

	public void setPurchaseFromSupplierUIModel(InquiryPartyUIModel purchaseFromSupplierUIModel) {
		this.purchaseFromSupplierUIModel = purchaseFromSupplierUIModel;
	}

	public InquiryActionNodeUIModel getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(InquiryActionNodeUIModel inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public void setSubmittedBy(InquiryActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public List<ServiceExtendUIModel> getServiceExtendUIModelList() {
		return serviceExtendUIModelList;
	}

	public void setServiceExtendUIModelList(
			List<ServiceExtendUIModel> serviceExtendUIModelList) {
		this.serviceExtendUIModelList = serviceExtendUIModelList;
	}

}

package com.company.IntelligentPlatform.logistics.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class InquiryServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = Inquiry.NODENAME, nodeInstId = Inquiry.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected Inquiry inquiry;

	@IServiceModuleFieldConfig(nodeName = InquiryParty.NODENAME, nodeInstId = InquiryParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InquiryParty purchaseToOrg;

	@IServiceModuleFieldConfig(nodeName = InquiryParty.NODENAME, nodeInstId = InquiryParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected InquiryParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = InquiryActionNode.NODENAME,
			nodeInstId = InquiryActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InquiryActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = InquiryActionNode.NODENAME,
			nodeInstId = InquiryActionNode.NODEINST_ACTION_INPROCESS, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InquiryActionNode inProcessBy;

	@IServiceModuleFieldConfig(nodeName = InquiryActionNode.NODENAME, nodeInstId =
			InquiryActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected InquiryActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = InquiryMaterialItem.NODENAME, nodeInstId = InquiryMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<InquiryMaterialItemServiceModel> inquiryMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = InquiryAttachment.NODENAME, nodeInstId = InquiryAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> inquiryAttachmentList = new ArrayList<>();

	public List<InquiryMaterialItemServiceModel> getInquiryMaterialItemList() {
		return this.inquiryMaterialItemList;
	}

	public void setInquiryMaterialItemList(
			List<InquiryMaterialItemServiceModel> inquiryMaterialItemList) {
		this.inquiryMaterialItemList = inquiryMaterialItemList;
	}

	public InquiryActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(InquiryActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public InquiryActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(InquiryActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Inquiry getInquiry() {
		return this.inquiry;
	}

	public void setInquiry(Inquiry inquiry) {
		this.inquiry = inquiry;
	}

	public List<ServiceEntityNode> getInquiryAttachmentList() {
		return this.inquiryAttachmentList;
	}

	public InquiryActionNode getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(InquiryActionNode inProcessBy) {
		this.inProcessBy = inProcessBy;
	}

	public void setInquiryAttachmentList(
			List<ServiceEntityNode> inquiryAttachmentList) {
		this.inquiryAttachmentList = inquiryAttachmentList;
	}

	public InquiryParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(InquiryParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public InquiryParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(InquiryParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}
}

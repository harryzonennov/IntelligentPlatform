package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.logistics.model.*;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRequestServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = PurchaseRequest.NODENAME, nodeInstId = PurchaseRequest.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected PurchaseRequest purchaseRequest;
	
	@IServiceModuleFieldConfig(nodeName = PurchaseRequestParty.NODENAME, nodeInstId = PurchaseRequestParty.PARTY_NODEINST_PUR_ORG, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected PurchaseRequestParty purchaseToOrg;
	
	@IServiceModuleFieldConfig(nodeName = PurchaseRequestParty.NODENAME, nodeInstId = PurchaseRequestParty.PARTY_NODEINST_PUR_SUPPLIER, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_PARTY)
	protected PurchaseRequestParty purchaseFromSupplier;

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestActionNode.NODENAME,
			nodeInstId = PurchaseRequestActionNode.NODEINST_ACTION_APPROVE, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseRequestActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestActionNode.NODENAME, nodeInstId =
			PurchaseRequestActionNode.NODEINST_ACTION_INPROCESS, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseRequestActionNode inProcessBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestActionNode.NODENAME, nodeInstId =
			PurchaseRequestActionNode.NODEINST_ACTION_SUBMIT, blockUpdate = true, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected PurchaseRequestActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestMaterialItem.NODENAME,
			nodeInstId = PurchaseRequestMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<PurchaseRequestMaterialItemServiceModel> purchaseRequestMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = PurchaseRequestAttachment.NODENAME, nodeInstId = PurchaseRequestAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> purchaseRequestAttachmentList = new ArrayList<>();

	public List<PurchaseRequestMaterialItemServiceModel> getPurchaseRequestMaterialItemList() {
		return purchaseRequestMaterialItemList;
	}

	public void setPurchaseRequestMaterialItemList(final List<PurchaseRequestMaterialItemServiceModel> purchaseRequestMaterialItemList) {
		this.purchaseRequestMaterialItemList = purchaseRequestMaterialItemList;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public PurchaseRequestParty getPurchaseToOrg() {
		return purchaseToOrg;
	}

	public void setPurchaseToOrg(PurchaseRequestParty purchaseToOrg) {
		this.purchaseToOrg = purchaseToOrg;
	}

	public PurchaseRequestParty getPurchaseFromSupplier() {
		return purchaseFromSupplier;
	}

	public void setPurchaseFromSupplier(PurchaseRequestParty purchaseFromSupplier) {
		this.purchaseFromSupplier = purchaseFromSupplier;
	}

	public List<ServiceEntityNode> getPurchaseRequestAttachmentList() {
		return purchaseRequestAttachmentList;
	}

	public void setPurchaseRequestAttachmentList(List<ServiceEntityNode> purchaseRequestAttachmentList) {
		this.purchaseRequestAttachmentList = purchaseRequestAttachmentList;
	}

	public PurchaseRequestActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(PurchaseRequestActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public PurchaseRequestActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(PurchaseRequestActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public PurchaseRequestActionNode getInProcessBy() {
		return inProcessBy;
	}

	public void setInProcessBy(PurchaseRequestActionNode inProcessBy) {
		this.inProcessBy = inProcessBy;
	}
}

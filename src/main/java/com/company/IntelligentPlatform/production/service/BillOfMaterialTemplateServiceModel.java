package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateAttachment;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateActionNode;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class BillOfMaterialTemplateServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplate.NODENAME, nodeInstId = BillOfMaterialTemplate.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected BillOfMaterialTemplate billOfMaterialTemplate;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId = BillOfMaterialTemplateActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialTemplateActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId = BillOfMaterialTemplateActionNode.NODEINST_ACTION_COUNTAPPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialTemplateActionNode countApprovedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialTemplateActionNode rejectApproveBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialTemplateActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_REVOKE_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialTemplateActionNode revokeSubmittedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_ACTIVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialTemplateActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateItem.NODENAME, nodeInstId = BillOfMaterialTemplateItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<BillOfMaterialTemplateItemServiceModel> billOfMaterialTemplateItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialTemplateAttachment.NODENAME, nodeInstId = BillOfMaterialTemplateAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> billOfMaterialTemplateAttachmentList = new ArrayList<>();

	public BillOfMaterialTemplateActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(BillOfMaterialTemplateActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public BillOfMaterialTemplateActionNode getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(BillOfMaterialTemplateActionNode countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public BillOfMaterialTemplateActionNode getRejectApproveBy() {
		return rejectApproveBy;
	}

	public void setRejectApproveBy(BillOfMaterialTemplateActionNode rejectApproveBy) {
		this.rejectApproveBy = rejectApproveBy;
	}

	public BillOfMaterialTemplateActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(BillOfMaterialTemplateActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public BillOfMaterialTemplateActionNode getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(BillOfMaterialTemplateActionNode revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public List<BillOfMaterialTemplateItemServiceModel> getBillOfMaterialTemplateItemList() {
		return billOfMaterialTemplateItemList;
	}

	public void setBillOfMaterialTemplateItemList(List<BillOfMaterialTemplateItemServiceModel> billOfMaterialTemplateItemList) {
		this.billOfMaterialTemplateItemList = billOfMaterialTemplateItemList;
	}

	public List<ServiceEntityNode> getBillOfMaterialTemplateAttachmentList() {
		return billOfMaterialTemplateAttachmentList;
	}

	public void setBillOfMaterialTemplateAttachmentList(List<ServiceEntityNode> billOfMaterialTemplateAttachmentList) {
		this.billOfMaterialTemplateAttachmentList = billOfMaterialTemplateAttachmentList;
	}

	public BillOfMaterialTemplate getBillOfMaterialTemplate() {
		return this.billOfMaterialTemplate;
	}

	public void setBillOfMaterialTemplate(BillOfMaterialTemplate billOfMaterialTemplate) {
		this.billOfMaterialTemplate = billOfMaterialTemplate;
	}

}

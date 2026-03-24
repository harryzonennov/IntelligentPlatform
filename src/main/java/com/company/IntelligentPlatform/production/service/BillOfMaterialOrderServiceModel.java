package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.BillOfMaterialAttachment;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;

import com.company.IntelligentPlatform.production.model.BillOfMaterialOrderActionNode;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class BillOfMaterialOrderServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrder.NODENAME, nodeInstId = BillOfMaterialOrder.SENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_DOC)
	protected BillOfMaterialOrder billOfMaterialOrder;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId = BillOfMaterialOrderActionNode.NODEINST_ACTION_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialOrderActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_ACTIVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialOrderActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_REINIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialOrderActionNode reInitBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_REJECT_APPROVE, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialOrderActionNode rejectApproveBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialOrderActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ACTNODE)
	protected BillOfMaterialOrderActionNode revokeSubmittedBy;

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialItem.NODENAME, nodeInstId = BillOfMaterialItem.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_MATITEM)
	protected List<BillOfMaterialItemServiceModel> billOfMaterialItemList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = BillOfMaterialAttachment.NODENAME, nodeInstId = BillOfMaterialAttachment.NODENAME, docNodeCategory =
			IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT)
	protected List<ServiceEntityNode> billOfMaterialAttachmentList = new ArrayList<>();

	public BillOfMaterialOrderActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(BillOfMaterialOrderActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public BillOfMaterialOrderActionNode getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(BillOfMaterialOrderActionNode activeBy) {
		this.activeBy = activeBy;
	}

	public BillOfMaterialOrderActionNode getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(BillOfMaterialOrderActionNode reInitBy) {
		this.reInitBy = reInitBy;
	}

	public BillOfMaterialOrderActionNode getRejectApproveBy() {
		return rejectApproveBy;
	}

	public void setRejectApproveBy(BillOfMaterialOrderActionNode rejectApproveBy) {
		this.rejectApproveBy = rejectApproveBy;
	}

	public BillOfMaterialOrderActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(BillOfMaterialOrderActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public BillOfMaterialOrderActionNode getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(BillOfMaterialOrderActionNode revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public List<BillOfMaterialItemServiceModel> getBillOfMaterialItemList() {
		return billOfMaterialItemList;
	}

	public void setBillOfMaterialItemList(List<BillOfMaterialItemServiceModel> billOfMaterialItemList) {
		this.billOfMaterialItemList = billOfMaterialItemList;
	}

	public List<ServiceEntityNode> getBillOfMaterialAttachmentList() {
		return billOfMaterialAttachmentList;
	}

	public void setBillOfMaterialAttachmentList(List<ServiceEntityNode> billOfMaterialAttachmentList) {
		this.billOfMaterialAttachmentList = billOfMaterialAttachmentList;
	}

	public BillOfMaterialOrder getBillOfMaterialOrder() {
		return this.billOfMaterialOrder;
	}

	public void setBillOfMaterialOrder(BillOfMaterialOrder billOfMaterialOrder) {
		this.billOfMaterialOrder = billOfMaterialOrder;
	}

}

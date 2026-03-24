package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.BillOfMaterialAttachment;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;

import com.company.IntelligentPlatform.production.model.BillOfMaterialOrderActionNode;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;

@Component
public class BillOfMaterialOrderServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialOrder.NODENAME, nodeInstId = BillOfMaterialOrder.SENAME)
	protected BillOfMaterialOrderUIModel billOfMaterialOrderUIModel;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId = SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE)
	protected BillOfMaterialOrderActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId = SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE)
	protected BillOfMaterialOrderActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_SUBMIT)
	protected BillOfMaterialOrderActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected BillOfMaterialOrderActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialOrderActionNode.NODENAME, nodeInstId =
			BillOfMaterialOrderActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected BillOfMaterialOrderActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialItem.NODENAME, nodeInstId = BillOfMaterialItem.NODENAME)
	protected List<BillOfMaterialItemServiceUIModel> billOfMaterialItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialAttachment.NODENAME, nodeInstId = BillOfMaterialAttachment.NODENAME)
	protected List<BillOfMaterialAttachmentUIModel> billOfMaterialAttachmentUIModelList = new ArrayList<>();

	public BillOfMaterialOrderUIModel getBillOfMaterialOrderUIModel() {
		return this.billOfMaterialOrderUIModel;
	}

	public void setBillOfMaterialOrderUIModel(
			BillOfMaterialOrderUIModel billOfMaterialOrderUIModel) {
		this.billOfMaterialOrderUIModel = billOfMaterialOrderUIModel;
	}

	public List<BillOfMaterialItemServiceUIModel> getBillOfMaterialItemUIModelList() {
		return this.billOfMaterialItemUIModelList;
	}

	public void setBillOfMaterialItemUIModelList(
			List<BillOfMaterialItemServiceUIModel> billOfMaterialItemUIModelList) {
		this.billOfMaterialItemUIModelList = billOfMaterialItemUIModelList;
	}

	public List<BillOfMaterialAttachmentUIModel> getBillOfMaterialAttachmentUIModelList() {
		return billOfMaterialAttachmentUIModelList;
	}

	public void setBillOfMaterialAttachmentUIModelList(List<BillOfMaterialAttachmentUIModel> billOfMaterialAttachmentUIModelList) {
		this.billOfMaterialAttachmentUIModelList = billOfMaterialAttachmentUIModelList;
	}

	public BillOfMaterialOrderActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(BillOfMaterialOrderActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public BillOfMaterialOrderActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(BillOfMaterialOrderActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public BillOfMaterialOrderActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(BillOfMaterialOrderActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public BillOfMaterialOrderActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(BillOfMaterialOrderActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public BillOfMaterialOrderActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(BillOfMaterialOrderActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}
}

package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateAttachment;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplate;
import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateActionNode;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;

import java.util.ArrayList;
import java.util.List;

@Component
public class BillOfMaterialTemplateServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplate.NODENAME, nodeInstId = BillOfMaterialTemplate.SENAME)
	protected BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId = SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE)
	protected BillOfMaterialTemplateActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId = SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE)
	protected BillOfMaterialTemplateActionNodeUIModel countApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_SUBMIT)
	protected BillOfMaterialTemplateActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_REVOKE_SUBMIT)
	protected BillOfMaterialTemplateActionNodeUIModel revokeSubmittedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_REJECT_APPROVE)
	protected BillOfMaterialTemplateActionNodeUIModel rejectApprovedBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateActionNode.NODENAME, nodeInstId =
			BillOfMaterialTemplateActionNode.NODEINST_ACTION_ACTIVE)
	protected BillOfMaterialTemplateActionNodeUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateItem.NODENAME, nodeInstId = BillOfMaterialTemplateItem.NODENAME)
	protected List<BillOfMaterialTemplateItemServiceUIModel> billOfMaterialTemplateItemUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = BillOfMaterialTemplateAttachment.NODENAME, nodeInstId = BillOfMaterialTemplateAttachment.NODENAME)
	protected List<BillOfMaterialTemplateAttachmentUIModel> billOfMaterialTemplateAttachmentUIModelList = new ArrayList<>();

	public BillOfMaterialTemplateUIModel getBillOfMaterialTemplateUIModel() {
		return this.billOfMaterialTemplateUIModel;
	}

	public void setBillOfMaterialTemplateUIModel(
			BillOfMaterialTemplateUIModel billOfMaterialTemplateUIModel) {
		this.billOfMaterialTemplateUIModel = billOfMaterialTemplateUIModel;
	}

	public List<BillOfMaterialTemplateItemServiceUIModel> getBillOfMaterialTemplateItemUIModelList() {
		return this.billOfMaterialTemplateItemUIModelList;
	}

	public void setBillOfMaterialTemplateItemUIModelList(
			List<BillOfMaterialTemplateItemServiceUIModel> billOfMaterialTemplateItemUIModelList) {
		this.billOfMaterialTemplateItemUIModelList = billOfMaterialTemplateItemUIModelList;
	}

	public List<BillOfMaterialTemplateAttachmentUIModel> getBillOfMaterialTemplateAttachmentUIModelList() {
		return billOfMaterialTemplateAttachmentUIModelList;
	}

	public void setBillOfMaterialTemplateAttachmentUIModelList(List<BillOfMaterialTemplateAttachmentUIModel> billOfMaterialTemplateAttachmentUIModelList) {
		this.billOfMaterialTemplateAttachmentUIModelList = billOfMaterialTemplateAttachmentUIModelList;
	}

	public BillOfMaterialTemplateActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(BillOfMaterialTemplateActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public BillOfMaterialTemplateActionNodeUIModel getCountApprovedBy() {
		return countApprovedBy;
	}

	public void setCountApprovedBy(BillOfMaterialTemplateActionNodeUIModel countApprovedBy) {
		this.countApprovedBy = countApprovedBy;
	}

	public BillOfMaterialTemplateActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(BillOfMaterialTemplateActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public BillOfMaterialTemplateActionNodeUIModel getRevokeSubmittedBy() {
		return revokeSubmittedBy;
	}

	public void setRevokeSubmittedBy(BillOfMaterialTemplateActionNodeUIModel revokeSubmittedBy) {
		this.revokeSubmittedBy = revokeSubmittedBy;
	}

	public BillOfMaterialTemplateActionNodeUIModel getRejectApprovedBy() {
		return rejectApprovedBy;
	}

	public void setRejectApprovedBy(BillOfMaterialTemplateActionNodeUIModel rejectApprovedBy) {
		this.rejectApprovedBy = rejectApprovedBy;
	}

	public BillOfMaterialTemplateActionNodeUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(BillOfMaterialTemplateActionNodeUIModel activeBy) {
		this.activeBy = activeBy;
	}
}

package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialTypeActionNode;
import com.company.IntelligentPlatform.common.model.MaterialTypeAttachment;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class MaterialTypeServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MaterialType.NODENAME, nodeInstId = MaterialType.SENAME)
	protected MaterialType materialType;

	@IServiceModuleFieldConfig(nodeName = MaterialTypeAttachment.NODENAME , nodeInstId = MaterialTypeAttachment.NODENAME)
	protected List<ServiceEntityNode> materialTypeAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId = MaterialTypeActionNode.NODEINST_ACTION_ACTIVE)
	protected MaterialTypeActionNode activeBy;

	@IServiceModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId = MaterialTypeActionNode.NODEINST_ACTION_APPROVE)
	protected MaterialTypeActionNode approvedBy;

	@IServiceModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId = MaterialTypeActionNode.NODEINST_ACTION_SUBMIT)
	protected MaterialTypeActionNode submittedBy;

	@IServiceModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId =
			MaterialTypeActionNode.NODEINST_ACTION_REINIT)
	protected MaterialTypeActionNode reInitBy;

	@IServiceModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId =
			MaterialTypeActionNode.NODEINST_ACTION_ARCHIVE)
	protected MaterialTypeActionNode archivedBy;

	public MaterialType getMaterialType() {
		return materialType;
	}

	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
	}

	public List<ServiceEntityNode> getMaterialTypeAttachmentList() {
		return materialTypeAttachmentList;
	}

	public void setMaterialTypeAttachmentList(List<ServiceEntityNode> materialTypeAttachmentList) {
		this.materialTypeAttachmentList = materialTypeAttachmentList;
	}

	public MaterialTypeActionNode getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(MaterialTypeActionNode activeBy) {
		this.activeBy = activeBy;
	}

	public MaterialTypeActionNode getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(MaterialTypeActionNode approvedBy) {
		this.approvedBy = approvedBy;
	}

	public MaterialTypeActionNode getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(MaterialTypeActionNode submittedBy) {
		this.submittedBy = submittedBy;
	}

	public MaterialTypeActionNode getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(MaterialTypeActionNode reInitBy) {
		this.reInitBy = reInitBy;
	}

	public MaterialTypeActionNode getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(MaterialTypeActionNode archivedBy) {
		this.archivedBy = archivedBy;
	}
}

package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.MaterialTypeActionNode;
import com.company.IntelligentPlatform.common.model.MaterialTypeAttachment;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;


@Component
public class MaterialTypeServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MaterialType.NODENAME, nodeInstId = MaterialType.SENAME)
	protected MaterialTypeUIModel materialTypeUIModel;

	@IServiceUIModuleFieldConfig(nodeName = MaterialTypeAttachment.NODENAME
			, nodeInstId = MaterialTypeAttachment.NODENAME)
	protected List<MaterialTypeAttachmentUIModel> materialTypeAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId = MaterialTypeActionNode.NODEINST_ACTION_ACTIVE)
	protected MaterialTypeActionNodeUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId =
			MaterialTypeActionNode.NODEINST_ACTION_APPROVE)
	protected MaterialTypeActionNodeUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId =
			MaterialTypeActionNode.NODEINST_ACTION_SUBMIT)
	protected MaterialTypeActionNodeUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId =
			MaterialTypeActionNode.NODEINST_ACTION_REINIT)
	protected MaterialTypeActionNodeUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialTypeActionNode.NODENAME, nodeInstId =
			MaterialTypeActionNode.NODEINST_ACTION_ARCHIVE)
	protected MaterialTypeActionNodeUIModel archivedBy;

	public MaterialTypeUIModel getMaterialTypeUIModel() {
		return materialTypeUIModel;
	}

	public void setMaterialTypeUIModel(MaterialTypeUIModel materialTypeUIModel) {
		this.materialTypeUIModel = materialTypeUIModel;
	}

	public List<MaterialTypeAttachmentUIModel> getMaterialTypeAttachmentUIModelList() {
		return materialTypeAttachmentUIModelList;
	}

	public void setMaterialTypeAttachmentUIModelList(List<MaterialTypeAttachmentUIModel> materialTypeAttachmentUIModelList) {
		this.materialTypeAttachmentUIModelList = materialTypeAttachmentUIModelList;
	}

	public MaterialTypeActionNodeUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(MaterialTypeActionNodeUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public MaterialTypeActionNodeUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(MaterialTypeActionNodeUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public MaterialTypeActionNodeUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(MaterialTypeActionNodeUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public MaterialTypeActionNodeUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(MaterialTypeActionNodeUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public MaterialTypeActionNodeUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(MaterialTypeActionNodeUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}
}

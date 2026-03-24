package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialActionLog;
import com.company.IntelligentPlatform.common.model.MaterialAttachment;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class MaterialServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = Material.NODENAME, nodeInstId = Material.SENAME)
	protected MaterialUIModel materialUIModel;

	@IServiceUIModuleFieldConfig(nodeName = MaterialUnitReference.NODENAME, nodeInstId = MaterialUnitReference.NODENAME)
	protected List<MaterialUnitServiceUIModel> materialUnitUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = MaterialAttachment.NODENAME, nodeInstId = MaterialAttachment.NODENAME)
	protected List<MaterialAttachmentUIModel> materialAttachmentUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId = MaterialActionLog.NODEINST_ACTION_ACTIVE)
	protected MaterialActionLogUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId =
			MaterialActionLog.NODEINST_ACTION_APPROVE)
	protected MaterialActionLogUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId =
			MaterialActionLog.NODEINST_ACTION_SUBMIT)
	protected MaterialActionLogUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId =
			MaterialActionLog.NODEINST_ACTION_REINIT)
	protected MaterialActionLogUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId =
			MaterialActionLog.NODEINST_ACTION_ARCHIVE)
	protected MaterialActionLogUIModel archivedBy;

	public MaterialUIModel getMaterialUIModel() {
		return this.materialUIModel;
	}

	public void setMaterialUIModel(MaterialUIModel materialUIModel) {
		this.materialUIModel = materialUIModel;
	}

	public List<MaterialUnitServiceUIModel> getMaterialUnitUIModelList() {
		return this.materialUnitUIModelList;
	}

	public void setMaterialUnitUIModelList(
			List<MaterialUnitServiceUIModel> materialUnitUIModelList) {
		this.materialUnitUIModelList = materialUnitUIModelList;
	}

	public List<MaterialAttachmentUIModel> getMaterialAttachmentUIModelList() {
		return materialAttachmentUIModelList;
	}

	public void setMaterialAttachmentUIModelList(
			List<MaterialAttachmentUIModel> MaterialAttachmentUIModelList) {
		this.materialAttachmentUIModelList = MaterialAttachmentUIModelList;
	}

	public MaterialActionLogUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(MaterialActionLogUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public MaterialActionLogUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(MaterialActionLogUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public MaterialActionLogUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(MaterialActionLogUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}

	public MaterialActionLogUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(MaterialActionLogUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public MaterialActionLogUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(MaterialActionLogUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}
}

package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialActionLog;
import com.company.IntelligentPlatform.common.model.MaterialAttachment;
import com.company.IntelligentPlatform.common.model.MaterialUnitReference;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class MaterialServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = Material.NODENAME, nodeInstId = Material.SENAME)
	protected Material material;

	@IServiceModuleFieldConfig(nodeName = MaterialUnitReference.NODENAME, nodeInstId = MaterialUnitReference.NODENAME)
	protected List<MaterialUnitServiceModel> materialUnitList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = MaterialAttachment.NODENAME, nodeInstId = MaterialAttachment.NODENAME,
			blockUpdate = true)
	protected List<ServiceEntityNode> MaterialAttachmentList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId = MaterialActionLog.NODEINST_ACTION_ACTIVE)
	protected MaterialActionLog activeBy;

	@IServiceModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId = MaterialActionLog.NODEINST_ACTION_APPROVE)
	protected MaterialActionLog approvedBy;

	@IServiceModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId = MaterialActionLog.NODEINST_ACTION_SUBMIT)
	protected MaterialActionLog submittedBy;

	@IServiceModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId =
			MaterialActionLog.NODEINST_ACTION_REINIT)
	protected MaterialActionLog reInitBy;

	@IServiceModuleFieldConfig(nodeName = MaterialActionLog.NODENAME, nodeInstId =
			MaterialActionLog.NODEINST_ACTION_ARCHIVE)
	protected MaterialActionLog archivedBy;


	public Material getMaterial() {
		return this.material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<MaterialUnitServiceModel> getMaterialUnitList() {
		return materialUnitList;
	}

	public void setMaterialUnitList(List<MaterialUnitServiceModel> materialUnitList) {
		this.materialUnitList = materialUnitList;
	}

	public List<ServiceEntityNode> getMaterialAttachmentList() {
		return MaterialAttachmentList;
	}

	public void setMaterialAttachmentList(
			List<ServiceEntityNode> MaterialAttachmentList) {
		this.MaterialAttachmentList = MaterialAttachmentList;
	}

	public MaterialActionLog getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(MaterialActionLog activeBy) {
		this.activeBy = activeBy;
	}

	public MaterialActionLog getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(MaterialActionLog reInitBy) {
		this.reInitBy = reInitBy;
	}

	public MaterialActionLog getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(MaterialActionLog archivedBy) {
		this.archivedBy = archivedBy;
	}

	public MaterialActionLog getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(MaterialActionLog approvedBy) {
		this.approvedBy = approvedBy;
	}
}

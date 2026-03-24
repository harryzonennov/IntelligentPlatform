package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.MaterialSKUActionLog;
import com.company.IntelligentPlatform.common.model.MaterialSKUAttachment;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class MaterialStockKeepUnitServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MaterialStockKeepUnit.NODENAME, nodeInstId = MaterialStockKeepUnit.SENAME)
	protected MaterialStockKeepUnit materialStockKeepUnit;

	@IServiceModuleFieldConfig(nodeName = MaterialSKUUnitReference.NODENAME, nodeInstId = MaterialSKUUnitReference.NODENAME)
	protected List<MaterialSKUUnitServiceModel> materialSKUUnitList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = MaterialSKUAttachment.NODENAME, nodeInstId = MaterialSKUAttachment.NODENAME,
			blockUpdate = true)
	protected List<ServiceEntityNode> materialSKUAttachment = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId = MaterialSKUActionLog.NODEINST_ACTION_ACTIVE)
	protected MaterialSKUActionLog activeBy;

	@IServiceModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId = MaterialSKUActionLog.NODEINST_ACTION_APPROVE)
	protected MaterialSKUActionLog approvedBy;

	@IServiceModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId = MaterialSKUActionLog.NODEINST_ACTION_SUBMIT)
	protected MaterialSKUActionLog submittedBy;

	@IServiceModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId =
			MaterialSKUActionLog.NODEINST_ACTION_REINIT)
	protected MaterialSKUActionLog reInitBy;

	@IServiceModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId =
			MaterialSKUActionLog.NODEINST_ACTION_ARCHIVE)
	protected MaterialSKUActionLog archivedBy;

	public List<MaterialSKUUnitServiceModel> getMaterialSKUUnitList() {
		return materialSKUUnitList;
	}

	public void setMaterialSKUUnitList(List<MaterialSKUUnitServiceModel> materialSKUUnitList) {
		this.materialSKUUnitList = materialSKUUnitList;
	}

	public MaterialStockKeepUnit getMaterialStockKeepUnit() {
		return this.materialStockKeepUnit;
	}

	public void setMaterialStockKeepUnit(
			MaterialStockKeepUnit materialStockKeepUnit) {
		this.materialStockKeepUnit = materialStockKeepUnit;
	}

	public List<ServiceEntityNode> getMaterialSKUAttachment() {
		return materialSKUAttachment;
	}

	public void setMaterialSKUAttachment(
			List<ServiceEntityNode> materialSKUAttachment) {
		this.materialSKUAttachment = materialSKUAttachment;
	}

	public MaterialSKUActionLog getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(MaterialSKUActionLog activeBy) {
		this.activeBy = activeBy;
	}

	public MaterialSKUActionLog getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(MaterialSKUActionLog approvedBy) {
		this.approvedBy = approvedBy;
	}

	public MaterialSKUActionLog getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(MaterialSKUActionLog submittedBy) {
		this.submittedBy = submittedBy;
	}

	public MaterialSKUActionLog getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(MaterialSKUActionLog reInitBy) {
		this.reInitBy = reInitBy;
	}

	public MaterialSKUActionLog getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(MaterialSKUActionLog archivedBy) {
		this.archivedBy = archivedBy;
	}
}

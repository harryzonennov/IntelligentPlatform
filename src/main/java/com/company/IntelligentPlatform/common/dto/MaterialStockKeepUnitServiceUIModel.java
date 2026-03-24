package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialSKUActionLog;
import com.company.IntelligentPlatform.common.model.MaterialSKUAttachment;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class MaterialStockKeepUnitServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MaterialStockKeepUnit.NODENAME, 
			nodeInstId = MaterialStockKeepUnit.SENAME)
	protected MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel;

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUUnitReference.NODENAME, 
			nodeInstId = MaterialSKUUnitReference.NODENAME)
	protected List<MaterialSKUUnitServiceUIModel > materialSKUUnitUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId = MaterialSKUActionLog.NODEINST_ACTION_ACTIVE)
	protected MaterialSKUActionLogUIModel activeBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId =
			MaterialSKUActionLog.NODEINST_ACTION_APPROVE)
	protected MaterialSKUActionLogUIModel approvedBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId =
			MaterialSKUActionLog.NODEINST_ACTION_SUBMIT)
	protected MaterialSKUActionLogUIModel submittedBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId =
			MaterialSKUActionLog.NODEINST_ACTION_REINIT)
	protected MaterialSKUActionLogUIModel reInitBy;

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUActionLog.NODENAME, nodeInstId =
			MaterialSKUActionLog.NODEINST_ACTION_ARCHIVE)
	protected MaterialSKUActionLogUIModel archivedBy;
	
	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUAttachment.NODENAME, nodeInstId = MaterialSKUAttachment.NODENAME)
	protected List<MaterialSKUAttachmentUIModel> materialSKUAttachmentUIModelList = new ArrayList<>();

	public MaterialStockKeepUnitUIModel getMaterialStockKeepUnitUIModel() {
		return this.materialStockKeepUnitUIModel;
	}

	public void setMaterialStockKeepUnitUIModel(
			MaterialStockKeepUnitUIModel materialStockKeepUnitUIModel) {
		this.materialStockKeepUnitUIModel = materialStockKeepUnitUIModel;
	}

	public List<MaterialSKUUnitServiceUIModel> getMaterialSKUUnitUIModelList() {
		return materialSKUUnitUIModelList;
	}

	public void setMaterialSKUUnitUIModelList(List<MaterialSKUUnitServiceUIModel> materialSKUUnitUIModelList) {
		this.materialSKUUnitUIModelList = materialSKUUnitUIModelList;
	}

	public List<MaterialSKUAttachmentUIModel> getMaterialSKUAttachmentUIModelList() {
		return materialSKUAttachmentUIModelList;
	}

	public void setMaterialSKUAttachmentUIModelList(
			List<MaterialSKUAttachmentUIModel> materialSKUAttachmentUIModelList) {
		this.materialSKUAttachmentUIModelList = materialSKUAttachmentUIModelList;
	}

	public MaterialSKUActionLogUIModel getActiveBy() {
		return activeBy;
	}

	public void setActiveBy(MaterialSKUActionLogUIModel activeBy) {
		this.activeBy = activeBy;
	}

	public MaterialSKUActionLogUIModel getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(MaterialSKUActionLogUIModel approvedBy) {
		this.approvedBy = approvedBy;
	}

	public MaterialSKUActionLogUIModel getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(MaterialSKUActionLogUIModel submittedBy) {
		this.submittedBy = submittedBy;
	}

	public MaterialSKUActionLogUIModel getReInitBy() {
		return reInitBy;
	}

	public void setReInitBy(MaterialSKUActionLogUIModel reInitBy) {
		this.reInitBy = reInitBy;
	}

	public MaterialSKUActionLogUIModel getArchivedBy() {
		return archivedBy;
	}

	public void setArchivedBy(MaterialSKUActionLogUIModel archivedBy) {
		this.archivedBy = archivedBy;
	}
}

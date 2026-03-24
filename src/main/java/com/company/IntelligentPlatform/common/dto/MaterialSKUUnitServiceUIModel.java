package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitAttachment;
import com.company.IntelligentPlatform.common.model.MaterialSKUUnitReference;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialSKUUnitServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUUnitReference.NODENAME, nodeInstId = MaterialSKUUnitReference.NODENAME)
	protected MaterialSKUUnitUIModel materialSKUUnitUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUUnitAttachment.NODENAME, nodeInstId = MaterialSKUUnitAttachment.NODENAME)
	protected List<MaterialSKUUnitAttachmentUIModel> materialSKUUnitAttachmentUIModelList = new ArrayList<>();

	public MaterialSKUUnitUIModel getMaterialSKUUnitUIModel() {
		return materialSKUUnitUIModel;
	}

	public void setMaterialSKUUnitUIModel(MaterialSKUUnitUIModel materialSKUUnitUIModel) {
		this.materialSKUUnitUIModel = materialSKUUnitUIModel;
	}

	public List<MaterialSKUUnitAttachmentUIModel> getMaterialSKUUnitAttachmentUIModelList() {
		return materialSKUUnitAttachmentUIModelList;
	}

	public void setMaterialSKUUnitAttachmentUIModelList(
			List<MaterialSKUUnitAttachmentUIModel> materialSKUUnitAttachmentUIModelList) {
		this.materialSKUUnitAttachmentUIModelList = materialSKUUnitAttachmentUIModelList;
	}

}

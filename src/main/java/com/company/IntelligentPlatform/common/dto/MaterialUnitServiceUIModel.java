package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialUnitServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MaterialUnitReference.NODENAME, nodeInstId = MaterialUnitReference.NODENAME)
	protected MaterialUnitUIModel materialUnitUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = MaterialUnitAttachment.NODENAME, nodeInstId = MaterialUnitAttachment.NODENAME)
	protected List<MaterialUnitAttachmentUIModel> materialUnitAttachmentUIModelList = new ArrayList<>();

	public MaterialUnitUIModel getMaterialUnitUIModel() {
		return materialUnitUIModel;
	}

	public void setMaterialUnitUIModel(MaterialUnitUIModel materialUnitUIModel) {
		this.materialUnitUIModel = materialUnitUIModel;
	}

	public List<MaterialUnitAttachmentUIModel> getMaterialUnitAttachmentUIModelList() {
		return materialUnitAttachmentUIModelList;
	}

	public void setMaterialUnitAttachmentUIModelList(
			List<MaterialUnitAttachmentUIModel> materialUnitAttachmentUIModelList) {
		this.materialUnitAttachmentUIModelList = materialUnitAttachmentUIModelList;
	}

}

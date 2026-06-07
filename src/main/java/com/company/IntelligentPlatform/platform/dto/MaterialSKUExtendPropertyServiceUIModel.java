package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.MaterialSKUExtendProperty;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

public class MaterialSKUExtendPropertyServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MaterialSKUExtendProperty.NODENAME, nodeInstId =
			MaterialSKUExtendProperty.NODENAME)
	protected MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel;

	public MaterialSKUExtendPropertyUIModel getMaterialSKUExtendPropertyUIModel() {
		return materialSKUExtendPropertyUIModel;
	}

	public void setMaterialSKUExtendPropertyUIModel(MaterialSKUExtendPropertyUIModel materialSKUExtendPropertyUIModel) {
		this.materialSKUExtendPropertyUIModel = materialSKUExtendPropertyUIModel;
	}
}

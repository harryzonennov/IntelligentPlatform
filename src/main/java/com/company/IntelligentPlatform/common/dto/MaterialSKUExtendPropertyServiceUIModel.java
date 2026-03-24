package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.MaterialSKUExtendProperty;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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

package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class StandardMaterialUnitServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = StandardMaterialUnit.NODENAME, nodeInstId = StandardMaterialUnit.SENAME)
	protected StandardMaterialUnitUIModel standardMaterialUnitUIModel;

	public StandardMaterialUnitUIModel getStandardMaterialUnitUIModel() {
		return standardMaterialUnitUIModel;
	}

	public void setStandardMaterialUnitUIModel(StandardMaterialUnitUIModel standardMaterialUnitUIModel) {
		this.standardMaterialUnitUIModel = standardMaterialUnitUIModel;
	}
}

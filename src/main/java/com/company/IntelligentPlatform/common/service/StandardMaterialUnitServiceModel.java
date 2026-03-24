package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class StandardMaterialUnitServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = StandardMaterialUnit.NODENAME, nodeInstId = StandardMaterialUnit.SENAME)
	protected StandardMaterialUnit standardMaterialUnit;

	public StandardMaterialUnit getStandardMaterialUnit() {
		return standardMaterialUnit;
	}

	public void setStandardMaterialUnit(StandardMaterialUnit standardMaterialUnit) {
		this.standardMaterialUnit = standardMaterialUnit;
	}
}

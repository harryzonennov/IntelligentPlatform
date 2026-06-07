package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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

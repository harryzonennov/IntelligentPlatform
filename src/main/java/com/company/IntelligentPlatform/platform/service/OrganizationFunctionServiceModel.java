package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.OrganizationFunction;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class OrganizationFunctionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = OrganizationFunction.NODENAME, nodeInstId = OrganizationFunction.NODENAME)
	protected OrganizationFunction organizationFunction;

	public OrganizationFunction getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(OrganizationFunction organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

}

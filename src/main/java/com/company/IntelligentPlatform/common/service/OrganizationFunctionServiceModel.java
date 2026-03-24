package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.OrganizationFunction;
import com.company.IntelligentPlatform.common.model.ServiceModule;


public class OrganizationFunctionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = OrganizationFunction.NODENAME, nodeInstId = OrganizationFunction.SENAME)
	protected OrganizationFunction organizationFunction;

	public OrganizationFunction getOrganizationFunction() {
		return organizationFunction;
	}

	public void setOrganizationFunction(OrganizationFunction organizationFunction) {
		this.organizationFunction = organizationFunction;
	}

}

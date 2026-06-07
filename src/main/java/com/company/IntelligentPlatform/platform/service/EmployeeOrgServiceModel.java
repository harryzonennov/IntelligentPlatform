package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.EmployeeOrgReference;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class EmployeeOrgServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = EmployeeOrgReference.NODENAME, nodeInstId = EmployeeOrgReference.NODENAME)
	protected EmployeeOrgReference employeeOrgReference;

	public EmployeeOrgReference getEmployeeOrgReference() {
		return employeeOrgReference;
	}

	public void setEmployeeOrgReference(EmployeeOrgReference employeeOrgReference) {
		this.employeeOrgReference = employeeOrgReference;
	}
}

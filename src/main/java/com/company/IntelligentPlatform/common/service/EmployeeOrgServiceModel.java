package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.EmployeeOrgReference;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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

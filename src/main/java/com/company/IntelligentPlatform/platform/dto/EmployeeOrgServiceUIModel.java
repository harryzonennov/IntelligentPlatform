package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.EmployeeOrgReference;

public class EmployeeOrgServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = EmployeeOrgReference.NODENAME, nodeInstId = EmployeeOrgReference.NODENAME)
	protected EmployeeOrgUIModel employeeOrgUIModel;

	public EmployeeOrgUIModel getEmployeeOrgUIModel() {
		return employeeOrgUIModel;
	}

	public void setEmployeeOrgUIModel(EmployeeOrgUIModel employeeOrgUIModel) {
		this.employeeOrgUIModel = employeeOrgUIModel;
	}
}

package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.EmployeeOrgReference;

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

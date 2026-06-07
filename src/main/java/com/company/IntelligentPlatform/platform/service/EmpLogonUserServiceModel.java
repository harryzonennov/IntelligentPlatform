package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.EmpLogonUserReference;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class EmpLogonUserServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = EmpLogonUserReference.NODENAME, nodeInstId = EmpLogonUserReference.NODENAME)
	protected EmpLogonUserReference employeeOrgReference;

	public EmpLogonUserReference getEmpLogonUserReference() {
		return employeeOrgReference;
	}

	public void setEmpLogonUserReference(EmpLogonUserReference employeeOrgReference) {
		this.employeeOrgReference = employeeOrgReference;
	}

}

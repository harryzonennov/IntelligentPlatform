package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.EmpLogonUserReference;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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

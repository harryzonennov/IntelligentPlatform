package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.EmpLogonUserReference;

public class EmpLogonUserServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = EmpLogonUserReference.NODENAME, nodeInstId = EmpLogonUserReference.NODENAME)
	protected EmpLogonUserUIModel empLogonUserUIModel;

	public EmpLogonUserUIModel getEmpLogonUserUIModel() {
		return empLogonUserUIModel;
	}

	public void setEmpLogonUserUIModel(EmpLogonUserUIModel empLogonUserUIModel) {
		this.empLogonUserUIModel = empLogonUserUIModel;
	}
}

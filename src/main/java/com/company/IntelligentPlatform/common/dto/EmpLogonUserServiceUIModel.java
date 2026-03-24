package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.EmpLogonUserReference;

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

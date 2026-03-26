package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.RoleSubAuthorization;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RoleSubAuthorizationServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RoleSubAuthorization.NODENAME, nodeInstId = RoleSubAuthorization.NODENAME)
	protected RoleSubAuthorization roleSubAuthorization;

	public RoleSubAuthorization getRoleSubAuthorization() {
		return roleSubAuthorization;
	}

	public void setRoleSubAuthorization(RoleSubAuthorization roleSubAuthorization) {
		this.roleSubAuthorization = roleSubAuthorization;
	}
}

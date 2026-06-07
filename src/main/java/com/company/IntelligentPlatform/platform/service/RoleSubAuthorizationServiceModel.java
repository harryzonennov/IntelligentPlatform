package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.RoleAuthorization;
import com.company.IntelligentPlatform.platform.model.RoleSubAuthorization;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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

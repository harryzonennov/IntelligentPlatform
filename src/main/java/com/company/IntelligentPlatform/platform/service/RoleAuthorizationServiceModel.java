package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.RoleAuthorization;
import com.company.IntelligentPlatform.platform.model.RoleSubAuthorization;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RoleAuthorizationServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RoleAuthorization.NODENAME, nodeInstId = RoleAuthorization.NODENAME)
	protected RoleAuthorization roleAuthorization;

	@IServiceModuleFieldConfig(nodeName = RoleSubAuthorization.NODENAME, nodeInstId = RoleSubAuthorization.NODENAME)
	protected List<RoleSubAuthorizationServiceModel> roleSubAuthorizationList = new ArrayList<>();

	public RoleAuthorization getRoleAuthorization() {
		return this.roleAuthorization;
	}

	public void setRoleAuthorization(RoleAuthorization roleAuthorization) {
		this.roleAuthorization = roleAuthorization;
	}

	public List<RoleSubAuthorizationServiceModel> getRoleSubAuthorizationList() {
		return roleSubAuthorizationList;
	}

	public void setRoleSubAuthorizationList(List<RoleSubAuthorizationServiceModel> roleSubAuthorizationList) {
		this.roleSubAuthorizationList = roleSubAuthorizationList;
	}
}

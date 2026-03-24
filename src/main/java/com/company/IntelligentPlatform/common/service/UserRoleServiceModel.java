package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.UserRole;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class UserRoleServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = UserRole.NODENAME, nodeInstId = UserRole.NODENAME)
	protected UserRole userRole;

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}

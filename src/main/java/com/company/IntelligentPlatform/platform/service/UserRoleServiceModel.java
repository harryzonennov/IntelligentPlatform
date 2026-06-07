package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.UserRole;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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

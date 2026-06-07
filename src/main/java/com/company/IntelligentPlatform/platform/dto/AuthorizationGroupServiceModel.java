package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.AuthorizationGroup;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class AuthorizationGroupServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = AuthorizationGroup.NODENAME, nodeInstId = AuthorizationGroup.SENAME)
	protected AuthorizationGroup authorizationGroup;

	public AuthorizationGroup getAuthorizationGroup() {
		return authorizationGroup;
	}

	public void setAuthorizationGroup(AuthorizationGroup authorizationGroup) {
		this.authorizationGroup = authorizationGroup;
	}

}

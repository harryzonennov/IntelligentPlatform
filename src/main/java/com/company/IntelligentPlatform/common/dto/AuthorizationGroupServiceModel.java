package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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

package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class AuthorizationObjectServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = AuthorizationObject.NODENAME, nodeInstId = AuthorizationObject.SENAME)
	protected AuthorizationObject authorizationObject;

	public AuthorizationObject getAuthorizationObject() {
		return authorizationObject;
	}

	public void setAuthorizationObject(AuthorizationObject authorizationObject) {
		this.authorizationObject = authorizationObject;
	}

}

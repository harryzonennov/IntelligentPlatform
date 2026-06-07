package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.AuthorizationObject;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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

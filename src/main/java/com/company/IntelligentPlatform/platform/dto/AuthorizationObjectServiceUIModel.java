package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.platform.model.AuthorizationObject;

public class AuthorizationObjectServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = AuthorizationObject.NODENAME, nodeInstId = AuthorizationObject.SENAME, 
			convToUIMethod = AuthorizationObjectManager.Method_ConvAuthorizationObjectToUI)
	protected AuthorizationObjectUIModel authorizationObjectUIModel;

	public AuthorizationObjectUIModel getAuthorizationObjectUIModel() {
		return authorizationObjectUIModel;
	}

	public void setAuthorizationObjectUIModel(
			AuthorizationObjectUIModel authorizationObjectUIModel) {
		this.authorizationObjectUIModel = authorizationObjectUIModel;
	}
	
}

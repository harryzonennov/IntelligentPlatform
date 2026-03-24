package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;

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

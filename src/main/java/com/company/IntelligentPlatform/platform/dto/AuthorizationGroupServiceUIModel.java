package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.platform.model.AuthorizationGroup;

public class AuthorizationGroupServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = AuthorizationGroup.NODENAME, nodeInstId = AuthorizationGroup.SENAME)
	protected AuthorizationGroupUIModel authorizationGroupUIModel;

	public AuthorizationGroupUIModel getAuthorizationGroupUIModel() {
		return authorizationGroupUIModel;
	}

	public void setAuthorizationGroupUIModel(
			AuthorizationGroupUIModel authorizationGroupUIModel) {
		this.authorizationGroupUIModel = authorizationGroupUIModel;
	}
	
}

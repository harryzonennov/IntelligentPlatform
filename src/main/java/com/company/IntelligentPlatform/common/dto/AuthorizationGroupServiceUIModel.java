package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;

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

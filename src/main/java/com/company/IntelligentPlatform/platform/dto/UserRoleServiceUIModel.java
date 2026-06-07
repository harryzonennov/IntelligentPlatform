package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.UserRole;

public class UserRoleServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = UserRole.NODENAME, nodeInstId =
			UserRole.NODENAME)
	protected UserRoleUIModel userRoleUIModel;

	public UserRoleUIModel getUserRoleUIModel() {
		return userRoleUIModel;
	}

	public void setUserRoleUIModel(UserRoleUIModel userRoleUIModel) {
		this.userRoleUIModel = userRoleUIModel;
	}
}

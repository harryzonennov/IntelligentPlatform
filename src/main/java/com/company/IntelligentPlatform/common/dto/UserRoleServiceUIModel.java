package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.UserRole;

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

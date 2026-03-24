package com.company.IntelligentPlatform.common.service;


import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.RoleMessageCategory;
import com.company.IntelligentPlatform.common.model.RoleSubAuthorization;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

public class RoleMessageCategoryServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = RoleMessageCategory.NODENAME, nodeInstId = RoleMessageCategory.NODENAME)
	protected RoleMessageCategory roleMessageCategory;

	public RoleMessageCategory getRoleMessageCategory() {
		return this.roleMessageCategory;
	}

	public void setRoleMessageCategory(RoleMessageCategory roleMessageCategory) {
		this.roleMessageCategory = roleMessageCategory;
	}
}

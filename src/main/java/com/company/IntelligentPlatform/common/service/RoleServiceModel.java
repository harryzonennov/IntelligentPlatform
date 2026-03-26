package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.RoleMessageCategory;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class RoleServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = Role.NODENAME, nodeInstId = Role.SENAME)
	protected Role role;

	@IServiceModuleFieldConfig(nodeName = RoleAuthorization.NODENAME, nodeInstId = RoleAuthorization.NODENAME)
	protected List<RoleAuthorizationServiceModel> roleAuthorizationList = new ArrayList<>();
	
	@IServiceModuleFieldConfig(nodeName = RoleMessageCategory.NODENAME, nodeInstId = RoleMessageCategory.NODENAME)
	protected List<RoleMessageCategoryServiceModel> roleMessageCategoryList = new ArrayList<>();

	public List<RoleAuthorizationServiceModel> getRoleAuthorizationList() {
		return this.roleAuthorizationList;
	}

	public void setRoleAuthorizationList(
			List<RoleAuthorizationServiceModel> roleAuthorizationList) {
		this.roleAuthorizationList = roleAuthorizationList;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<RoleMessageCategoryServiceModel> getRoleMessageCategoryList() {
		return roleMessageCategoryList;
	}

	public void setRoleMessageCategoryList(
			List<RoleMessageCategoryServiceModel> roleMessageCategoryList) {
		this.roleMessageCategoryList = roleMessageCategoryList;
	}

}

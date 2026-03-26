package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.RoleSubAuthorization;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleAuthorizationServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RoleAuthorization.NODENAME, nodeInstId = RoleAuthorization.NODENAME)
	protected RoleAuthorizationUIModel roleAuthorizationUIModel;

	@IServiceUIModuleFieldConfig(nodeName = RoleSubAuthorization.NODENAME, nodeInstId = RoleSubAuthorization.NODENAME)
	protected List<RoleSubAuthorizationServiceUIModel> roleSubAuthorizationUIModelList = new ArrayList<>();

	public RoleAuthorizationUIModel getRoleAuthorizationUIModel() {
		return roleAuthorizationUIModel;
	}

	public void setRoleAuthorizationUIModel(
			RoleAuthorizationUIModel roleAuthorizationUIModel) {
		this.roleAuthorizationUIModel = roleAuthorizationUIModel;
	}

	public List<RoleSubAuthorizationServiceUIModel> getRoleSubAuthorizationUIModelList() {
		return roleSubAuthorizationUIModelList;
	}

	public void setRoleSubAuthorizationUIModelList(List<RoleSubAuthorizationServiceUIModel> roleSubAuthorizationUIModelList) {
		this.roleSubAuthorizationUIModelList = roleSubAuthorizationUIModelList;
	}
}

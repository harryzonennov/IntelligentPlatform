package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.service.RoleManager;
import com.company.IntelligentPlatform.platform.model.Role;
import com.company.IntelligentPlatform.platform.model.RoleAuthorization;
import com.company.IntelligentPlatform.platform.model.RoleMessageCategory;

@Component
public class RoleServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = Role.NODENAME, nodeInstId = Role.SENAME, convToUIMethod = RoleManager.METHOD_ConvRoleToUI, convUIToMethod = RoleManager.METHOD_ConvUIToRole)
	protected RoleUIModel roleUIModel;

	@IServiceUIModuleFieldConfig(nodeName = RoleAuthorization.NODENAME, nodeInstId = RoleAuthorization.NODENAME, 
			convToUIMethod = RoleManager.METHOD_ConvRoleAuthorizationToComUI)
	protected List<RoleAuthorizationServiceUIModel> roleAuthorizationUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = RoleMessageCategory.NODENAME, nodeInstId = RoleMessageCategory.NODENAME)
	protected List<RoleMessageCategoryServiceUIModel> roleMessageCategoryUIModelList = new ArrayList<>();

	public RoleUIModel getRoleUIModel() {
		return this.roleUIModel;
	}

	public void setRoleUIModel(RoleUIModel roleUIModel) {
		this.roleUIModel = roleUIModel;
	}

	public List<RoleAuthorizationServiceUIModel> getRoleAuthorizationUIModelList() {
		return roleAuthorizationUIModelList;
	}

	public void setRoleAuthorizationUIModelList(
			List<RoleAuthorizationServiceUIModel> roleAuthorizationUIModelList) {
		this.roleAuthorizationUIModelList = roleAuthorizationUIModelList;
	}

	public List<RoleMessageCategoryServiceUIModel> getRoleMessageCategoryUIModelList() {
		return roleMessageCategoryUIModelList;
	}

	public void setRoleMessageCategoryUIModelList(List<RoleMessageCategoryServiceUIModel> roleMessageCategoryUIModelList) {
		this.roleMessageCategoryUIModelList = roleMessageCategoryUIModelList;
	}
}

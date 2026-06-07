package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.RoleMessageCategory;

@Component
public class RoleMessageCategoryServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = RoleMessageCategory.NODENAME, nodeInstId = RoleMessageCategory.NODENAME)
	protected RoleMessageCategoryUIModel roleMessageCategoryUIModel;

	public RoleMessageCategoryUIModel getRoleMessageCategoryUIModel() {
		return roleMessageCategoryUIModel;
	}

	public void setRoleMessageCategoryUIModel(RoleMessageCategoryUIModel roleMessageCategoryUIModel) {
		this.roleMessageCategoryUIModel = roleMessageCategoryUIModel;
	}
}

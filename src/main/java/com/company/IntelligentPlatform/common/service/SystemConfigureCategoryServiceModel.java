package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceServiceModel;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class SystemConfigureCategoryServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemConfigureCategory.NODENAME, nodeInstId = SystemConfigureCategory.SENAME)
	protected SystemConfigureCategory systemConfigureCategory;

	@IServiceModuleFieldConfig(nodeName = SystemConfigureResource.NODENAME, nodeInstId = SystemConfigureResource.NODENAME)
	protected List<SystemConfigureResourceServiceModel> systemConfigureResourceList = new ArrayList<SystemConfigureResourceServiceModel>();

	public SystemConfigureCategory getSystemConfigureCategory() {
		return this.systemConfigureCategory;
	}

	public void setSystemConfigureCategory(
			SystemConfigureCategory systemConfigureCategory) {
		this.systemConfigureCategory = systemConfigureCategory;
	}

	public List<SystemConfigureResourceServiceModel> getSystemConfigureResourceList() {
		return this.systemConfigureResourceList;
	}

	public void setSystemConfigureResourceList(
			List<SystemConfigureResourceServiceModel> systemConfigureResourceList) {
		this.systemConfigureResourceList = systemConfigureResourceList;
	}

}

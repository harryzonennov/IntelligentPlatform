package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.service.SystemConfigureResourceServiceModel;
import com.company.IntelligentPlatform.platform.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.platform.model.SystemConfigureResource;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

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

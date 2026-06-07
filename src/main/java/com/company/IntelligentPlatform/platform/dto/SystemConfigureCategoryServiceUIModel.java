package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.platform.model.SystemConfigureResource;

@Component
public class SystemConfigureCategoryServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureCategory.NODENAME, nodeInstId = SystemConfigureCategory.SENAME)
	protected SystemConfigureCategoryUIModel systemConfigureCategoryUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureResource.NODENAME, nodeInstId = SystemConfigureResource.NODENAME)
	protected List<SystemConfigureResourceServiceUIModel> systemConfigureResourceUIModelList = new ArrayList<SystemConfigureResourceServiceUIModel>();

	public SystemConfigureCategoryUIModel getSystemConfigureCategoryUIModel() {
		return this.systemConfigureCategoryUIModel;
	}

	public void setSystemConfigureCategoryUIModel(
			SystemConfigureCategoryUIModel systemConfigureCategoryUIModel) {
		this.systemConfigureCategoryUIModel = systemConfigureCategoryUIModel;
	}

	public List<SystemConfigureResourceServiceUIModel> getSystemConfigureResourceUIModelList() {
		return this.systemConfigureResourceUIModelList;
	}

	public void setSystemConfigureResourceUIModelList(
			List<SystemConfigureResourceServiceUIModel> systemConfigureResourceUIModelList) {
		this.systemConfigureResourceUIModelList = systemConfigureResourceUIModelList;
	}

}

package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.SystemConfigureResourceUIModel;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;
import com.company.IntelligentPlatform.common.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

@Component
public class SystemConfigureResourceServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureResource.NODENAME, nodeInstId = SystemConfigureResource.NODENAME)
	protected SystemConfigureResourceUIModel systemConfigureResourceUIModel;

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureElement.NODENAME, nodeInstId = SystemConfigureElement.NODENAME)
	protected List<SystemConfigureElementServiceUIModel> systemConfigureElementUIModelList = new ArrayList<SystemConfigureElementServiceUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = SystemConfigureExtensionUnion.NODENAME, nodeInstId = SystemConfigureExtensionUnion.NODENAME)
	protected List<SystemConfigureExtensionUnionUIModel> systemConfigureExtensionUnionUIModelList = new ArrayList<SystemConfigureExtensionUnionUIModel>();
	
	public SystemConfigureResourceUIModel getSystemConfigureResourceUIModel() {
		return this.systemConfigureResourceUIModel;
	}

	public void setSystemConfigureResourceUIModel(
			SystemConfigureResourceUIModel systemConfigureResourceUIModel) {
		this.systemConfigureResourceUIModel = systemConfigureResourceUIModel;
	}

	public List<SystemConfigureElementServiceUIModel> getSystemConfigureElementUIModelList() {
		return this.systemConfigureElementUIModelList;
	}

	public void setSystemConfigureElementUIModelList(
			List<SystemConfigureElementServiceUIModel> systemConfigureElementUIModelList) {
		this.systemConfigureElementUIModelList = systemConfigureElementUIModelList;
	}

	public List<SystemConfigureExtensionUnionUIModel> getSystemConfigureExtensionUnionUIModelList() {
		return systemConfigureExtensionUnionUIModelList;
	}

	public void setSystemConfigureExtensionUnionUIModelList(
			List<SystemConfigureExtensionUnionUIModel> systemConfigureExtensionUnionUIModelList) {
		this.systemConfigureExtensionUnionUIModelList = systemConfigureExtensionUnionUIModelList;
	}

}

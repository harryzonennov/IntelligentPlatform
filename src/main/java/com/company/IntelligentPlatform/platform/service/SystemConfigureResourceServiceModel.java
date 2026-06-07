package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.SystemConfigureElement;
import com.company.IntelligentPlatform.platform.model.SystemConfigureExtensionUnion;
import com.company.IntelligentPlatform.platform.model.SystemConfigureResource;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class SystemConfigureResourceServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemConfigureResource.NODENAME, nodeInstId = SystemConfigureResource.NODENAME)
	protected SystemConfigureResource systemConfigureResource;

	@IServiceModuleFieldConfig(nodeName = SystemConfigureElement.NODENAME, nodeInstId = SystemConfigureElement.NODENAME)
	protected List<SystemConfigureElementServiceModel> systemConfigureElementList = new ArrayList<SystemConfigureElementServiceModel>();
	
	@IServiceModuleFieldConfig(nodeName = SystemConfigureExtensionUnion.NODENAME, nodeInstId = SystemConfigureExtensionUnion.NODENAME)
	protected List<ServiceEntityNode> systemConfigureExtensionUnionList = new ArrayList<>();

	public List<SystemConfigureElementServiceModel> getSystemConfigureElementList() {
		return systemConfigureElementList;
	}

	public void setSystemConfigureElementList(
			List<SystemConfigureElementServiceModel> systemConfigureElementList) {
		this.systemConfigureElementList = systemConfigureElementList;
	}

	public SystemConfigureResource getSystemConfigureResource() {
		return this.systemConfigureResource;
	}

	public void setSystemConfigureResource(
			SystemConfigureResource systemConfigureResource) {
		this.systemConfigureResource = systemConfigureResource;
	}

	public List<ServiceEntityNode> getSystemConfigureExtensionUnionList() {
		return systemConfigureExtensionUnionList;
	}

	public void setSystemConfigureExtensionUnionList(
			List<ServiceEntityNode> systemConfigureExtensionUnionList) {
		this.systemConfigureExtensionUnionList = systemConfigureExtensionUnionList;
	}

}

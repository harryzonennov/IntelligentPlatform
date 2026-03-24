package com.company.IntelligentPlatform.finance.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.ResourceAuthorization;
import com.company.IntelligentPlatform.common.model.SystemResource;

public class ResFinSystemResourceServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SystemResource.NODENAME, nodeInstId = SystemResource.SENAME)
	protected SystemResource systemResource;

	@IServiceModuleFieldConfig(nodeName = ResFinAccountSetting.NODENAME, nodeInstId = ResFinAccountSetting.NODENAME)
	protected List<ResFinAccountSettingServiceModel> resFinAccountSettingList = new ArrayList<ResFinAccountSettingServiceModel>();
	
	@IServiceModuleFieldConfig(nodeName = ResourceAuthorization.NODENAME, nodeInstId = ResourceAuthorization.NODENAME)
	protected List<ServiceEntityNode> resourceAuthorizationList = new ArrayList<ServiceEntityNode>();

	public SystemResource getSystemResource() {
		return this.systemResource;
	}

	public void setSystemResource(SystemResource systemResource) {
		this.systemResource = systemResource;
	}

	public List<ResFinAccountSettingServiceModel> getResFinAccountSettingList() {
		return resFinAccountSettingList;
	}

	public void setResFinAccountSettingList(
			List<ResFinAccountSettingServiceModel> resFinAccountSettingList) {
		this.resFinAccountSettingList = resFinAccountSettingList;
	}

	public List<ServiceEntityNode> getResourceAuthorizationList() {
		return resourceAuthorizationList;
	}

	public void setResourceAuthorizationList(
			List<ServiceEntityNode> resourceAuthorizationList) {
		this.resourceAuthorizationList = resourceAuthorizationList;
	}

}

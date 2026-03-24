package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.dto.ResourceAuthorizationUIModel;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;
import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.ResourceAuthorization;
import com.company.IntelligentPlatform.common.model.SystemResource;

@Component
public class ResFinSystemResourceServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SystemResource.NODENAME, nodeInstId = SystemResource.SENAME, convToUIMethod = SystemResourceManager.METHOD_ConvResFinSystemResourceToUI, convUIToMethod = SystemResourceManager.METHOD_ConvUIToResFinSystemResource)
	protected ResFinSystemResourceUIModel resFinSystemResourceUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ResFinAccountSetting.NODENAME, nodeInstId = ResFinAccountSetting.NODENAME, convToUIMethod = SystemResourceManager.METHOD_ConvResFinAccountFieldSettingToUI, convUIToMethod = SystemResourceManager.METHOD_ConvUIToResFinAccountSetting)
	protected List<ResFinAccountSettingServiceUIModel> resFinAccountSettingUIModelList = new ArrayList<ResFinAccountSettingServiceUIModel>();

	@IServiceUIModuleFieldConfig(nodeName = ResourceAuthorization.NODENAME, nodeInstId = ResourceAuthorization.NODENAME, convToUIMethod = SystemResourceManager.METHOD_ConvResourceAuthorizationToUI, convUIToMethod = SystemResourceManager.METHOD_ConvUIToResourceAuthorization)
	protected List<ResourceAuthorizationUIModel> resourceAuthorizationUIModelList = new ArrayList<ResourceAuthorizationUIModel>();

	public ResFinSystemResourceUIModel getResFinSystemResourceUIModel() {
		return resFinSystemResourceUIModel;
	}

	public void setResFinSystemResourceUIModel(
			ResFinSystemResourceUIModel resFinSystemResourceUIModel) {
		this.resFinSystemResourceUIModel = resFinSystemResourceUIModel;
	}


	public List<ResFinAccountSettingServiceUIModel> getResFinAccountSettingUIModelList() {
		return resFinAccountSettingUIModelList;
	}

	public void setResFinAccountSettingUIModelList(
			List<ResFinAccountSettingServiceUIModel> resFinAccountSettingUIModelList) {
		this.resFinAccountSettingUIModelList = resFinAccountSettingUIModelList;
	}

	public List<ResourceAuthorizationUIModel> getResourceAuthorizationUIModelList() {
		return resourceAuthorizationUIModelList;
	}

	public void setResourceAuthorizationUIModelList(
			List<ResourceAuthorizationUIModel> resourceAuthorizationUIModelList) {
		this.resourceAuthorizationUIModelList = resourceAuthorizationUIModelList;
	}

}

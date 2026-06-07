package com.company.IntelligentPlatform.platform.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.ServiceExtendFieldI18nSetting;
import com.company.IntelligentPlatform.platform.model.ServiceExtendFieldSetting;

public class ServiceExtendFieldSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceExtendFieldSetting.NODENAME, nodeInstId = ServiceExtendFieldSetting.NODENAME)
	protected ServiceExtendFieldSetting serviceExtendFieldSetting;
	
	@IServiceModuleFieldConfig(nodeName = ServiceExtendFieldI18nSetting.NODENAME, nodeInstId = ServiceExtendFieldI18nSetting.NODENAME)
	protected List<ServiceEntityNode> serviceExtendFieldI18nSettingList = new ArrayList<>();
	
	public ServiceExtendFieldSetting getServiceExtendFieldSetting() {
		return this.serviceExtendFieldSetting;
	}

	public List<ServiceEntityNode> getServiceExtendFieldI18nSettingList() {
		return serviceExtendFieldI18nSettingList;
	}

	public void setServiceExtendFieldI18nSettingList(
			List<ServiceEntityNode> serviceExtendFieldI18nSettingList) {
		this.serviceExtendFieldI18nSettingList = serviceExtendFieldI18nSettingList;
	}

	public void setServiceExtendFieldSetting(
			ServiceExtendFieldSetting serviceExtendFieldSetting) {
		this.serviceExtendFieldSetting = serviceExtendFieldSetting;
	}

}

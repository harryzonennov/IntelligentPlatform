package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.service.ServiceExtendFieldSettingServiceModel;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageI18n;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

public class ServiceExtensionSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceExtensionSetting.NODENAME, nodeInstId = ServiceExtensionSetting.SENAME)
	protected ServiceExtensionSetting serviceExtensionSetting;

	@IServiceModuleFieldConfig(nodeName = SerExtendPageI18n.NODENAME, nodeInstId = SerExtendPageI18n.NODENAME)
	protected List<SerExtendPageI18nServiceModel> serExtendPageI18nList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceExtendFieldSetting.NODENAME, nodeInstId = ServiceExtendFieldSetting.NODENAME)
	protected List<ServiceExtendFieldSettingServiceModel> serviceExtendFieldSettingList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = SerExtendPageSetting.NODENAME, nodeInstId = SerExtendPageSetting.NODENAME)
	protected List<SerExtendPageSettingServiceModel> serExtendPageSettingList = new ArrayList<>();

	public ServiceExtensionSetting getServiceExtensionSetting() {
		return this.serviceExtensionSetting;
	}

	public void setServiceExtensionSetting(
			ServiceExtensionSetting serviceExtensionSetting) {
		this.serviceExtensionSetting = serviceExtensionSetting;
	}

	public List<ServiceExtendFieldSettingServiceModel> getServiceExtendFieldSettingList() {
		return this.serviceExtendFieldSettingList;
	}

	public void setServiceExtendFieldSettingList(
			List<ServiceExtendFieldSettingServiceModel> serviceExtendFieldSettingList) {
		this.serviceExtendFieldSettingList = serviceExtendFieldSettingList;
	}

	public List<SerExtendPageSettingServiceModel> getSerExtendPageSettingList() {
		return serExtendPageSettingList;
	}

	public void setSerExtendPageSettingList(
			List<SerExtendPageSettingServiceModel> serExtendPageSettingList) {
		this.serExtendPageSettingList = serExtendPageSettingList;
	}

	public List<SerExtendPageI18nServiceModel> getSerExtendPageI18nList() {
		return serExtendPageI18nList;
	}

	public void setSerExtendPageI18nList(List<SerExtendPageI18nServiceModel> serExtendPageI18nList) {
		this.serExtendPageI18nList = serExtendPageI18nList;
	}
}

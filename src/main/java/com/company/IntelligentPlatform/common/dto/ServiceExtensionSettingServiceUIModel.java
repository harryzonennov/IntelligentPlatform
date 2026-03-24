package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.SerExtendPageI18n;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Component
public class ServiceExtensionSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceExtendFieldSetting.NODENAME, nodeInstId = ServiceExtendFieldSetting.NODENAME)
	protected List<ServiceExtendFieldSettingServiceUIModel> serviceExtendFieldSettingUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageI18n.NODENAME, nodeInstId = SerExtendPageI18n.NODENAME)
	protected List<SerExtendPageI18nServiceUIModel> serExtendPageI18nUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ServiceExtensionSetting.NODENAME, nodeInstId = ServiceExtensionSetting.SENAME)
	protected ServiceExtensionSettingUIModel serviceExtensionSettingUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = SerExtendPageSetting.NODENAME, nodeInstId = SerExtendPageSetting.NODENAME)
	protected List<SerExtendPageSettingServiceUIModel> serExtendPageSettingUIModelList = new ArrayList<>();

	public List<ServiceExtendFieldSettingServiceUIModel> getServiceExtendFieldSettingUIModelList() {
		return serviceExtendFieldSettingUIModelList;
	}

	public void setServiceExtendFieldSettingUIModelList(
			List<ServiceExtendFieldSettingServiceUIModel> serviceExtendFieldSettingUIModelList) {
		this.serviceExtendFieldSettingUIModelList = serviceExtendFieldSettingUIModelList;
	}

	public ServiceExtensionSettingUIModel getServiceExtensionSettingUIModel() {
		return serviceExtensionSettingUIModel;
	}

	public void setServiceExtensionSettingUIModel(
			ServiceExtensionSettingUIModel serviceExtensionSettingUIModel) {
		this.serviceExtensionSettingUIModel = serviceExtensionSettingUIModel;
	}

	public List<SerExtendPageSettingServiceUIModel> getSerExtendPageSettingUIModelList() {
		return serExtendPageSettingUIModelList;
	}

	public void setSerExtendPageSettingUIModelList(
			List<SerExtendPageSettingServiceUIModel> serExtendPageSettingUIModelList) {
		this.serExtendPageSettingUIModelList = serExtendPageSettingUIModelList;
	}

	public List<SerExtendPageI18nServiceUIModel> getSerExtendPageI18nUIModelList() {
		return serExtendPageI18nUIModelList;
	}

	public void setSerExtendPageI18nUIModelList(List<SerExtendPageI18nServiceUIModel> serExtendPageI18nUIModelList) {
		this.serExtendPageI18nUIModelList = serExtendPageI18nUIModelList;
	}
}

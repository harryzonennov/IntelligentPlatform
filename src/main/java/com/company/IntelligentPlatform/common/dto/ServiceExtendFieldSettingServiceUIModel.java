package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldI18nSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;

@Component
public class ServiceExtendFieldSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceExtendFieldSetting.NODENAME, nodeInstId = ServiceExtendFieldSetting.NODENAME)
	protected ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel;
	
	@IServiceUIModuleFieldConfig(nodeName = ServiceExtendFieldI18nSetting.NODENAME, nodeInstId = ServiceExtendFieldI18nSetting.NODENAME)
	protected List<ServiceExtendFieldI18nSettingUIModel> serviceExtendFieldI18nSettingUIModelList = new ArrayList<ServiceExtendFieldI18nSettingUIModel>();

	public ServiceExtendFieldSettingUIModel getServiceExtendFieldSettingUIModel() {
		return this.serviceExtendFieldSettingUIModel;
	}

	public void setServiceExtendFieldSettingUIModel(
			ServiceExtendFieldSettingUIModel serviceExtendFieldSettingUIModel) {
		this.serviceExtendFieldSettingUIModel = serviceExtendFieldSettingUIModel;
	}

	public List<ServiceExtendFieldI18nSettingUIModel> getServiceExtendFieldI18nSettingUIModelList() {
		return serviceExtendFieldI18nSettingUIModelList;
	}

	public void setServiceExtendFieldI18nSettingUIModelList(
			List<ServiceExtendFieldI18nSettingUIModel> serviceExtendFieldI18nSettingUIModelList) {
		this.serviceExtendFieldI18nSettingUIModelList = serviceExtendFieldI18nSettingUIModelList;
	}

}

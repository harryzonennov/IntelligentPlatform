package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.ServiceDocDeletionSetting;
import com.company.IntelligentPlatform.platform.model.ServiceDocInitInvolveParty;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceDocDeletionSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocDeletionSetting.NODENAME, nodeInstId = ServiceDocDeletionSetting.NODENAME)
	protected ServiceDocDeletionSettingUIModel serviceDocDeletionSettingUIModel;

	public ServiceDocDeletionSettingUIModel getServiceDocDeletionSettingUIModel() {
		return serviceDocDeletionSettingUIModel;
	}

	public void setServiceDocDeletionSettingUIModel(ServiceDocDeletionSettingUIModel serviceDocDeletionSettingUIModel) {
		this.serviceDocDeletionSettingUIModel = serviceDocDeletionSettingUIModel;
	}

}

package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.ServiceDocDeletionSetting;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

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

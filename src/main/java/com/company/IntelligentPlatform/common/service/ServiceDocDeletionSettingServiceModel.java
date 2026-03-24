package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceDocDeletionSetting;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocInitInvolveParty;

import java.util.ArrayList;
import java.util.List;

public class ServiceDocDeletionSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocDeletionSetting.NODENAME, nodeInstId = ServiceDocDeletionSetting.NODENAME)
	protected ServiceDocDeletionSetting serviceDocDeletionSetting;

	public ServiceDocDeletionSetting getServiceDocDeletionSetting() {
		return serviceDocDeletionSetting;
	}

	public void setServiceDocDeletionSetting(ServiceDocDeletionSetting serviceDocDeletionSetting) {
		this.serviceDocDeletionSetting = serviceDocDeletionSetting;
	}
}

package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.SerExtendUIControlSet;

public class SerExtendUIControlSetServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SerExtendUIControlSet.NODENAME, nodeInstId = SerExtendUIControlSet.NODENAME)
	protected SerExtendUIControlSet serExtendPageSetting;

	public SerExtendUIControlSet getSerExtendPageSetting() {
		return serExtendPageSetting;
	}

	public void setSerExtendPageSetting(SerExtendUIControlSet serExtendPageSetting) {
		this.serExtendPageSetting = serExtendPageSetting;
	}
}

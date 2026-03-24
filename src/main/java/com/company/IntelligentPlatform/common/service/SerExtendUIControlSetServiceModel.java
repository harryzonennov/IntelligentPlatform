package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;

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

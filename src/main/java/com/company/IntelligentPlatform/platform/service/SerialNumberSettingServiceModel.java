package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.SerialNumberSetting;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class SerialNumberSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = SerialNumberSetting.NODENAME, nodeInstId = SerialNumberSetting.SENAME)
	protected SerialNumberSetting serialNumberSetting;

	public SerialNumberSetting getSerialNumberSetting() {
		return serialNumberSetting;
	}

	public void setSerialNumberSetting(SerialNumberSetting serialNumberSetting) {
		this.serialNumberSetting = serialNumberSetting;
	}
}

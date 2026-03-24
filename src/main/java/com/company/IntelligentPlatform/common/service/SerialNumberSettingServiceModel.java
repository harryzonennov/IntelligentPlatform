package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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

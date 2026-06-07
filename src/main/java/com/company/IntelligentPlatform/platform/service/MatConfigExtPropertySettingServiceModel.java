package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class MatConfigExtPropertySettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MatConfigExtPropertySetting.NODENAME, nodeInstId = MatConfigExtPropertySetting.NODENAME)
	protected MatConfigExtPropertySetting matConfigExtPropertySetting;

	public MatConfigExtPropertySetting getMatConfigExtPropertySetting() {
		return matConfigExtPropertySetting;
	}

	public void setMatConfigExtPropertySetting(MatConfigExtPropertySetting matConfigExtPropertySetting) {
		this.matConfigExtPropertySetting = matConfigExtPropertySetting;
	}
}

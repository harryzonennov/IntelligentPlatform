package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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

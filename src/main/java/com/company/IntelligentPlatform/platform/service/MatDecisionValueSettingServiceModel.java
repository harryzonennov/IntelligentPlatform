package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;

public class MatDecisionValueSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MatDecisionValueSetting.NODENAME, nodeInstId = MatDecisionValueSetting.NODENAME)
	protected MatDecisionValueSetting matDecisionValueSetting;

	public MatDecisionValueSetting getMatDecisionValueSetting() {
		return matDecisionValueSetting;
	}

	public void setMatDecisionValueSetting(MatDecisionValueSetting matDecisionValueSetting) {
		this.matDecisionValueSetting = matDecisionValueSetting;
	}

}

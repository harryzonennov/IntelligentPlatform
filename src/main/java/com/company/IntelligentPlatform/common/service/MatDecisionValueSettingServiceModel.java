package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

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

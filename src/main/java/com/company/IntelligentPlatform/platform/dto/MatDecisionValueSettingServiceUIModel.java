package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

public class MatDecisionValueSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MatDecisionValueSetting.NODENAME, nodeInstId =
			MatDecisionValueSetting.NODENAME)
	protected MatDecisionValueSettingUIModel matDecisionValueSettingUIModel;

	public MatDecisionValueSettingUIModel getMatDecisionValueSettingUIModel() {
		return matDecisionValueSettingUIModel;
	}

	public void setMatDecisionValueSettingUIModel(MatDecisionValueSettingUIModel matDecisionValueSettingUIModel) {
		this.matDecisionValueSettingUIModel = matDecisionValueSettingUIModel;
	}
}

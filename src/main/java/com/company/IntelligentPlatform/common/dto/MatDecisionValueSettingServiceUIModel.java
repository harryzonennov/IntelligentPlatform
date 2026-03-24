package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

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

package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

public class MatConfigExtPropertySettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MatConfigExtPropertySetting.NODENAME, nodeInstId =
			MatConfigExtPropertySetting.NODENAME)
	protected MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel;

	public MatConfigExtPropertySettingUIModel getMatConfigExtPropertySettingUIModel() {
		return matConfigExtPropertySettingUIModel;
	}

	public void setMatConfigExtPropertySettingUIModel(MatConfigExtPropertySettingUIModel matConfigExtPropertySettingUIModel) {
		this.matConfigExtPropertySettingUIModel = matConfigExtPropertySettingUIModel;
	}
}

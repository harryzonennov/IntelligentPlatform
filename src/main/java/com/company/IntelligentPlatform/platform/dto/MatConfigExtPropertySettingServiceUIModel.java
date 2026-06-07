package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;

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

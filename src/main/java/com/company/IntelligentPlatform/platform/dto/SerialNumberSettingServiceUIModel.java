package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.SerialNumberSetting;

@Component
public class SerialNumberSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = SerialNumberSetting.NODENAME, nodeInstId = SerialNumberSetting.SENAME)
	protected SerialNumberSettingUIModel serialNumberSettingUIModel;

	public SerialNumberSettingUIModel getSerialNumberSettingUIModel() {
		return serialNumberSettingUIModel;
	}

	public void setSerialNumberSettingUIModel(SerialNumberSettingUIModel serialNumberSettingUIModel) {
		this.serialNumberSettingUIModel = serialNumberSettingUIModel;
	}
}

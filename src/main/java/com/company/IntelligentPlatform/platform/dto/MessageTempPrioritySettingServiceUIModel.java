package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.MessageTempPrioritySetting;

@Component
public class MessageTempPrioritySettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MessageTempPrioritySetting.NODENAME, nodeInstId = MessageTempPrioritySetting.NODENAME)
	protected MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel;

	public MessageTempPrioritySettingUIModel getMessageTempPrioritySettingUIModel() {
		return messageTempPrioritySettingUIModel;
	}

	public void setMessageTempPrioritySettingUIModel(MessageTempPrioritySettingUIModel messageTempPrioritySettingUIModel) {
		this.messageTempPrioritySettingUIModel = messageTempPrioritySettingUIModel;
	}

}

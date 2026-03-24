package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;

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

package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;

public class MessageTempPrioritySettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MessageTempPrioritySetting.NODENAME, nodeInstId = MessageTempPrioritySetting.NODENAME)
	protected MessageTempPrioritySetting messageTempPrioritySetting;

	public MessageTempPrioritySetting getMessageTempPrioritySetting() {
		return messageTempPrioritySetting;
	}

	public void setMessageTempPrioritySetting(MessageTempPrioritySetting messageTempPrioritySetting) {
		this.messageTempPrioritySetting = messageTempPrioritySetting;
	}
}

package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.MessageTempPrioritySetting;

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

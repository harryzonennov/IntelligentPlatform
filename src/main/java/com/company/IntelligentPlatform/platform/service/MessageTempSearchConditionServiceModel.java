package com.company.IntelligentPlatform.platform.service;

import com.company.IntelligentPlatform.platform.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;
import com.company.IntelligentPlatform.platform.model.ServiceModule;
import com.company.IntelligentPlatform.platform.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.platform.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.platform.model.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class MessageTempSearchConditionServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MessageTempSearchCondition.NODENAME, nodeInstId = MessageTempSearchCondition.NODENAME)
	protected MessageTempSearchCondition messageTempSearchCondition;

	public MessageTempSearchCondition getMessageTempSearchCondition() {
		return messageTempSearchCondition;
	}

	public void setMessageTempSearchCondition(MessageTempSearchCondition messageTempSearchCondition) {
		this.messageTempSearchCondition = messageTempSearchCondition;
	}
}

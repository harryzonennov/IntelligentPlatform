package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

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

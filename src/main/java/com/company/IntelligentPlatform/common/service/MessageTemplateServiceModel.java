package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

public class MessageTemplateServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MessageTemplate.NODENAME, nodeInstId = MessageTemplate.SENAME)
	protected MessageTemplate messageTemplate;

	@IServiceModuleFieldConfig(nodeName = MessageTempSearchCondition.NODENAME, nodeInstId = MessageTempSearchCondition.NODENAME)
	protected List<MessageTempSearchConditionServiceModel> messageTempSearchConditionList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = MessageTempPrioritySetting.NODENAME, nodeInstId = MessageTempPrioritySetting.NODENAME)
	protected List<MessageTempPrioritySettingServiceModel> messageTempPrioritySettingList = new ArrayList<>();

	public MessageTemplate getMessageTemplate() {
		return this.messageTemplate;
	}

	public void setMessageTemplate(MessageTemplate messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public List<MessageTempSearchConditionServiceModel> getMessageTempSearchConditionList() {
		return messageTempSearchConditionList;
	}

	public void setMessageTempSearchConditionList(List<MessageTempSearchConditionServiceModel> messageTempSearchConditionList) {
		this.messageTempSearchConditionList = messageTempSearchConditionList;
	}

	public List<MessageTempPrioritySettingServiceModel> getMessageTempPrioritySettingList() {
		return messageTempPrioritySettingList;
	}

	public void setMessageTempPrioritySettingList(List<MessageTempPrioritySettingServiceModel> messageTempPrioritySettingList) {
		this.messageTempPrioritySettingList = messageTempPrioritySettingList;
	}
}

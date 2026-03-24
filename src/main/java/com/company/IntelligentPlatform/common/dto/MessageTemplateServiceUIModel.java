package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Component
public class MessageTemplateServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MessageTemplate.NODENAME, nodeInstId = MessageTemplate.SENAME)
	protected MessageTemplateUIModel messageTemplateUIModel;

	@IServiceUIModuleFieldConfig(nodeName = MessageTempSearchCondition.NODENAME, nodeInstId = MessageTempSearchCondition.NODENAME)
	protected List<MessageTempSearchConditionServiceUIModel> messageTempSearchConditionUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = MessageTempPrioritySetting.NODENAME, nodeInstId = MessageTempPrioritySetting.NODENAME)
	protected List<MessageTempPrioritySettingServiceUIModel> messageTempPrioritySettingUIModelList = new ArrayList<>();

	public MessageTemplateUIModel getMessageTemplateUIModel() {
		return this.messageTemplateUIModel;
	}

	public void setMessageTemplateUIModel(
			MessageTemplateUIModel messageTemplateUIModel) {
		this.messageTemplateUIModel = messageTemplateUIModel;
	}

	public List<MessageTempSearchConditionServiceUIModel> getMessageTempSearchConditionUIModelList() {
		return messageTempSearchConditionUIModelList;
	}

	public void setMessageTempSearchConditionUIModelList(List<MessageTempSearchConditionServiceUIModel> messageTempSearchConditionUIModelList) {
		this.messageTempSearchConditionUIModelList = messageTempSearchConditionUIModelList;
	}

	public List<MessageTempPrioritySettingServiceUIModel> getMessageTempPrioritySettingUIModelList() {
		return messageTempPrioritySettingUIModelList;
	}

	public void setMessageTempPrioritySettingUIModelList(List<MessageTempPrioritySettingServiceUIModel> messageTempPrioritySettingUIModelList) {
		this.messageTempPrioritySettingUIModelList = messageTempPrioritySettingUIModelList;
	}
}

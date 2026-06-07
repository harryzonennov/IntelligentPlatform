package com.company.IntelligentPlatform.platform.dto;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.platform.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.platform.dto.ServiceUIModule;
import com.company.IntelligentPlatform.platform.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.platform.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.platform.model.MessageTempSearchCondition;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageTempSearchConditionServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MessageTempSearchCondition.NODENAME, nodeInstId = MessageTempSearchCondition.NODENAME)
	protected MessageTempSearchConditionUIModel messageTempSearchConditionUIModel;

	public MessageTempSearchConditionUIModel getMessageTempSearchConditionUIModel() {
		return messageTempSearchConditionUIModel;
	}

	public void setMessageTempSearchConditionUIModel(MessageTempSearchConditionUIModel messageTempSearchConditionUIModel) {
		this.messageTempSearchConditionUIModel = messageTempSearchConditionUIModel;
	}
}

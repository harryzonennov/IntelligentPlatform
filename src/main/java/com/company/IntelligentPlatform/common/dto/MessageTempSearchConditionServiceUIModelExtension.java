package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.MessageTempSearchConditionUIModel;
import com.company.IntelligentPlatform.common.service.MessageTempSearchConditionManager;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.model.MessageTempSearchCondition;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Service
public class MessageTempSearchConditionServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MessageTempSearchConditionManager messageTempSearchConditionManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion messageTempSearchConditionExtensionUnion = new ServiceUIModelExtensionUnion();
		messageTempSearchConditionExtensionUnion
				.setNodeInstId(MessageTempSearchCondition.NODENAME);
		messageTempSearchConditionExtensionUnion
				.setNodeName(MessageTempSearchCondition.NODENAME);

		// UI Model Configure of node:[MessageTempSearchCondition]
		UIModelNodeMapConfigure messageTempSearchConditionMap = new UIModelNodeMapConfigure();
		messageTempSearchConditionMap
				.setSeName(MessageTempSearchCondition.SENAME);
		messageTempSearchConditionMap
				.setNodeName(MessageTempSearchCondition.NODENAME);
		messageTempSearchConditionMap
				.setNodeInstID(MessageTempSearchCondition.NODENAME);
		messageTempSearchConditionMap.setHostNodeFlag(true);
		Class<?>[] messageTempSearchConditionConvToUIParas = {
				MessageTempSearchCondition.class,
				MessageTempSearchConditionUIModel.class };
		messageTempSearchConditionMap.setLogicManager(messageTempSearchConditionManager);
		messageTempSearchConditionMap
				.setConvToUIMethodParas(messageTempSearchConditionConvToUIParas);
		messageTempSearchConditionMap
				.setConvToUIMethod(MessageTempSearchConditionManager.METHOD_ConvMessageTempSearchConditionToUI);
		Class<?>[] MessageTempSearchConditionConvUIToParas = {
				MessageTempSearchConditionUIModel.class,
				MessageTempSearchCondition.class };
		messageTempSearchConditionMap
				.setConvUIToMethodParas(MessageTempSearchConditionConvUIToParas);
		messageTempSearchConditionMap
				.setConvUIToMethod(MessageTempSearchConditionManager.METHOD_ConvUIToMessageTempSearchCondition);
		uiModelNodeMapList.add(messageTempSearchConditionMap);

		// UI Model Configure of node:[MessageTempSearchCondition]
		UIModelNodeMapConfigure messageTemplateMap = new UIModelNodeMapConfigure();
		messageTemplateMap.setSeName(MessageTemplate.SENAME);
		messageTemplateMap.setNodeName(MessageTemplate.NODENAME);
		messageTemplateMap.setNodeInstID(MessageTemplate.SENAME);
		messageTemplateMap.setHostNodeFlag(false);
		messageTemplateMap.setBaseNodeInstID(MessageTempSearchCondition.NODENAME);
		messageTemplateMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] messageTemplateConvToUIParas = { MessageTemplate.class,
				MessageTempSearchConditionUIModel.class };
		messageTemplateMap.setLogicManager(messageTempSearchConditionManager);
		messageTemplateMap.setConvToUIMethodParas(messageTemplateConvToUIParas);
		messageTemplateMap
				.setConvToUIMethod(MessageTempSearchConditionManager.METHOD_ConvMessageTemplateToSearchUI);

		uiModelNodeMapList.add(messageTemplateMap);

		messageTempSearchConditionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(messageTempSearchConditionExtensionUnion);
		return resultList;
	}

}

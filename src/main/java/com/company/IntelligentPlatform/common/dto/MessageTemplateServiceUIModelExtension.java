package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Service
public class MessageTemplateServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MessageTempSearchConditionServiceUIModelExtension messageTempSearchConditionServiceUIModelExtension;

	@Autowired
	protected MessageTemplateManager messageTemplateManager;

	@Autowired
	protected MessageTempPrioritySettingServiceUIModelExtension messageTempPrioritySettingServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(messageTempSearchConditionServiceUIModelExtension);
		resultList.add(messageTempPrioritySettingServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion messageTemplateExtensionUnion = new ServiceUIModelExtensionUnion();
		messageTemplateExtensionUnion.setNodeInstId(MessageTemplate.SENAME);
		messageTemplateExtensionUnion.setNodeName(MessageTemplate.NODENAME);

		// UI Model Configure of node:[MessageTemplate]
		UIModelNodeMapConfigure messageTemplateMap = new UIModelNodeMapConfigure();
		messageTemplateMap.setSeName(MessageTemplate.SENAME);
		messageTemplateMap.setNodeName(MessageTemplate.NODENAME);
		messageTemplateMap.setNodeInstID(MessageTemplate.SENAME);
		messageTemplateMap.setHostNodeFlag(true);
		Class<?>[] messageTemplateConvToUIParas = { MessageTemplate.class,
				MessageTemplateUIModel.class };
		messageTemplateMap.setConvToUIMethodParas(messageTemplateConvToUIParas);
		messageTemplateMap
				.setConvToUIMethod(MessageTemplateManager.METHOD_ConvMessageTemplateToUI);
		Class<?>[] MessageTemplateConvUIToParas = {
				MessageTemplateUIModel.class, MessageTemplate.class };
		messageTemplateMap.setConvUIToMethodParas(MessageTemplateConvUIToParas);
		messageTemplateMap
				.setConvUIToMethod(MessageTemplateManager.METHOD_ConvUIToMessageTemplate);
		uiModelNodeMapList.add(messageTemplateMap);
		messageTemplateExtensionUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(messageTemplateExtensionUnion);
		return resultList;
	}

}

package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.RoleMessageCategoryManager;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.RoleMessageCategory;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

@Service
public class RoleMessageCategoryServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected RoleManager roleManager;

	@Autowired
	protected MessageTemplateManager messageTemplateManager;

	@Autowired
	protected RoleMessageCategoryManager roleMessageCategoryManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		return null;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion roleMessageCategoryExtensionUnion = new ServiceUIModelExtensionUnion();
		roleMessageCategoryExtensionUnion
				.setNodeInstId(IServiceModelConstants.RoleMessageCategory);
		roleMessageCategoryExtensionUnion
				.setNodeName(IServiceModelConstants.RoleMessageCategory);

		// UI Model Configure of node:[RoleMessageCategory]
		UIModelNodeMapConfigure roleMessageCategoryMap = new UIModelNodeMapConfigure();
		roleMessageCategoryMap.setSeName(IServiceModelConstants.Role);
		roleMessageCategoryMap
				.setNodeName(IServiceModelConstants.RoleMessageCategory);
		roleMessageCategoryMap
				.setNodeInstID(IServiceModelConstants.RoleMessageCategory);
		roleMessageCategoryMap.setHostNodeFlag(true);
		Class<?>[] roleMessageCategoryConvToUIParas = {
				RoleMessageCategory.class, RoleMessageCategoryUIModel.class };
		roleMessageCategoryMap
				.setConvToUIMethodParas(roleMessageCategoryConvToUIParas);
		roleMessageCategoryMap.setLogicManager(roleMessageCategoryManager);
		roleMessageCategoryMap
				.setConvToUIMethod(RoleMessageCategoryManager.METHOD_ConvRoleMessageCategoryToUI);

		Class<?>[] roleMessageCategoryConvUIToParas = {
				RoleMessageCategoryUIModel.class, RoleMessageCategory.class};
		roleMessageCategoryMap
				.setConvUIToMethodParas(roleMessageCategoryConvUIToParas);
		roleMessageCategoryMap.setLogicManager(roleMessageCategoryManager);
		roleMessageCategoryMap
				.setConvUIToMethod(RoleMessageCategoryManager.METHOD_ConvUIToRoleMessageCategory);
		uiModelNodeMapList.add(roleMessageCategoryMap);

		// UI Model Configure of node:[MessageTemplate]
		UIModelNodeMapConfigure messageTemplateMap = new UIModelNodeMapConfigure();
		messageTemplateMap.setSeName(MessageTemplate.SENAME);
		messageTemplateMap.setNodeName(MessageTemplate.NODENAME);
		messageTemplateMap
				.setServiceEntityManager(messageTemplateManager);
		messageTemplateMap.setNodeInstID(MessageTemplate.SENAME);
		messageTemplateMap.setBaseNodeInstID(IServiceModelConstants.RoleMessageCategory);
		messageTemplateMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] messageTemplateConvToUIParas = {
				MessageTemplate.class, RoleMessageCategoryUIModel.class };
		messageTemplateMap
				.setConvToUIMethodParas(messageTemplateConvToUIParas);
		messageTemplateMap.setLogicManager(roleMessageCategoryManager);
		messageTemplateMap
				.setConvToUIMethod(RoleMessageCategoryManager.METHOD_ConvMessageTemplateToUI);
		messageTemplateMap
				.setServiceEntityManager(messageTemplateManager);
		uiModelNodeMapList.add(messageTemplateMap);

		roleMessageCategoryExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(roleMessageCategoryExtensionUnion);
		return resultList;
	}

}

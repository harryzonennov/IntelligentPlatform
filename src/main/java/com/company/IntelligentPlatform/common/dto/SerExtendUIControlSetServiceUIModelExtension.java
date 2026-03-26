package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.SerExtendUIControlSetManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.SerExtendPageSection;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.SerExtendUIControlSet;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Service
public class SerExtendUIControlSetServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendUIControlSetManager serExtendUIControlSetManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serExtendUIControlSetExtensionUnion = new ServiceUIModelExtensionUnion();
		serExtendUIControlSetExtensionUnion
				.setNodeInstId(SerExtendUIControlSet.NODENAME);
		serExtendUIControlSetExtensionUnion
				.setNodeName(SerExtendUIControlSet.NODENAME);

		// UI Model Configure of node:[SerExtendUIControlSet]
		UIModelNodeMapConfigure serExtendUIControlSetMap = new UIModelNodeMapConfigure();
		serExtendUIControlSetMap.setSeName(SerExtendUIControlSet.SENAME);
		serExtendUIControlSetMap.setNodeName(SerExtendUIControlSet.NODENAME);
		serExtendUIControlSetMap.setNodeInstID(SerExtendUIControlSet.NODENAME);
		serExtendUIControlSetMap.setHostNodeFlag(true);
		Class<?>[] serExtendUIControlSetConvToUIParas = {
				SerExtendUIControlSet.class, SerExtendUIControlSetUIModel.class };
		serExtendUIControlSetMap
				.setConvToUIMethodParas(serExtendUIControlSetConvToUIParas);
		serExtendUIControlSetMap.setLogicManager(serExtendUIControlSetManager);
		serExtendUIControlSetMap
				.setConvToUIMethod(SerExtendUIControlSetManager.METHOD_ConvSerExtendUIControlSetToUI);
		Class<?>[] SerExtendUIControlSetConvUIToParas = {
				SerExtendUIControlSetUIModel.class, SerExtendUIControlSet.class };
		serExtendUIControlSetMap
				.setConvUIToMethodParas(SerExtendUIControlSetConvUIToParas);
		serExtendUIControlSetMap
				.setConvUIToMethod(SerExtendUIControlSetManager.METHOD_ConvUIToSerExtendUIControl);
		uiModelNodeMapList.add(serExtendUIControlSetMap);

		// UI Model Configure of node:[serviceExtendFieldSettingMap]
		UIModelNodeMapConfigure serviceExtendFieldSettingMap = new UIModelNodeMapConfigure();
		serviceExtendFieldSettingMap
				.setSeName(ServiceExtendFieldSetting.SENAME);
		serviceExtendFieldSettingMap
				.setNodeName(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingMap
				.setNodeInstID(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingMap.setHostNodeFlag(false);
		serviceExtendFieldSettingMap
				.setBaseNodeInstID(SerExtendUIControlSet.NODENAME);
		List<SearchConfigConnectCondition> fieldConditionList = new ArrayList<>();
		SearchConfigConnectCondition fieldCondition0 = new SearchConfigConnectCondition();
		fieldCondition0.setSourceFieldName("refFieldUUID");
		fieldCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		fieldConditionList.add(fieldCondition0);
		serviceExtendFieldSettingMap.setLogicManager(serExtendUIControlSetManager);
		serviceExtendFieldSettingMap.setConnectionConditions(fieldConditionList);
		Class<?>[] serviceExtendFieldSettingConvToUIParas = {
				ServiceExtendFieldSetting.class,
				SerExtendUIControlSetUIModel.class };
		serviceExtendFieldSettingMap
				.setConvToUIMethodParas(serviceExtendFieldSettingConvToUIParas);
		

		serviceExtendFieldSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		serviceExtendFieldSettingMap
				.setConvToUIMethod(SerExtendUIControlSetManager.METHOD_ConvServiceFieldToUIControlSetUI);
		uiModelNodeMapList.add(serviceExtendFieldSettingMap);

		// UI Model Configure of node:[serExtendPageSectionMap]
		UIModelNodeMapConfigure serExtendPageSectionMap = new UIModelNodeMapConfigure();
		serExtendPageSectionMap.setSeName(SerExtendPageSection.SENAME);
		serExtendPageSectionMap.setNodeName(SerExtendPageSection.NODENAME);
		serExtendPageSectionMap.setNodeInstID(SerExtendPageSection.NODENAME);
		serExtendPageSectionMap.setHostNodeFlag(false);
		serExtendPageSectionMap.setLogicManager(serExtendUIControlSetManager);
		serExtendPageSectionMap
				.setBaseNodeInstID(SerExtendUIControlSet.NODENAME);
		Class<?>[] serExtendPageSectionConvToUIParas = {
				SerExtendPageSection.class, SerExtendUIControlSetUIModel.class };
		serExtendPageSectionMap
				.setConvToUIMethodParas(serExtendPageSectionConvToUIParas);
		serExtendPageSectionMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serExtendPageSectionMap
				.setConvToUIMethod(SerExtendUIControlSetManager.METHOD_ConvPageSectionToUIControlSetUI);
		uiModelNodeMapList.add(serExtendPageSectionMap);

		// UI Model Configure of node:[serExtendPageSettingMap]
		UIModelNodeMapConfigure serExtendPageSettingMap = new UIModelNodeMapConfigure();
		serExtendPageSettingMap.setSeName(SerExtendPageSetting.SENAME);
		serExtendPageSettingMap.setNodeName(SerExtendPageSetting.NODENAME);
		serExtendPageSettingMap.setNodeInstID(SerExtendPageSetting.NODENAME);
		serExtendPageSettingMap.setHostNodeFlag(false);
		serExtendPageSettingMap
				.setBaseNodeInstID(SerExtendPageSection.NODENAME);
		Class<?>[] serExtendPageSettingConvToUIParas = {
				SerExtendPageSetting.class, SerExtendUIControlSetUIModel.class };
		serExtendPageSettingMap
				.setConvToUIMethodParas(serExtendPageSettingConvToUIParas);
		serExtendPageSettingMap.setLogicManager(serExtendUIControlSetManager);

		serExtendPageSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serExtendPageSettingMap
				.setConvToUIMethod(SerExtendUIControlSetManager.METHOD_ConvPageSettingToUIControlSetUI);
		uiModelNodeMapList.add(serExtendPageSettingMap);

		// UI Model Configure of node:[serviceExtensionSettingMap]
		UIModelNodeMapConfigure serviceExtensionSettingMap = new UIModelNodeMapConfigure();
		serviceExtensionSettingMap.setSeName(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingMap
				.setNodeName(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingMap
				.setNodeInstID(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingMap.setHostNodeFlag(false);
		serviceExtensionSettingMap
				.setBaseNodeInstID(ServiceExtendFieldSetting.NODENAME);
		Class<?>[] serviceExtensionSettingConvToUIParas = {
				ServiceExtensionSetting.class,
				SerExtendUIControlSetUIModel.class };
		serviceExtensionSettingMap
				.setConvToUIMethodParas(serviceExtensionSettingConvToUIParas);
		serviceExtensionSettingMap.setLogicManager(serExtendUIControlSetManager);
		serviceExtensionSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceExtensionSettingMap
				.setConvToUIMethod(SerExtendUIControlSetManager.METHOD_ConvExtensionSettingToUIControlSetUI);
		uiModelNodeMapList.add(serviceExtensionSettingMap);
		
		

		serExtendUIControlSetExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serExtendUIControlSetExtensionUnion);
		return resultList;
	}

}

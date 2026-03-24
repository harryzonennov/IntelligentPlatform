package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.ServiceExtendFieldI18nSettingUIModel;
import com.company.IntelligentPlatform.common.service.ServiceExtendFieldSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldI18nSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;

@Service
public class ServiceExtendFieldI18nSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceExtendFieldI18nSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceExtendFieldI18nSettingExtensionUnion
				.setNodeInstId(ServiceExtendFieldI18nSetting.NODENAME);
		serviceExtendFieldI18nSettingExtensionUnion
				.setNodeName(ServiceExtendFieldI18nSetting.NODENAME);

		// UI Model Configure of node:[ServiceExtendFieldI18nSetting]
		UIModelNodeMapConfigure serviceExtendFieldI18nSettingMap = new UIModelNodeMapConfigure();
		serviceExtendFieldI18nSettingMap
				.setSeName(ServiceExtendFieldI18nSetting.SENAME);
		serviceExtendFieldI18nSettingMap
				.setNodeName(ServiceExtendFieldI18nSetting.NODENAME);
		serviceExtendFieldI18nSettingMap
				.setNodeInstID(ServiceExtendFieldI18nSetting.NODENAME);
		serviceExtendFieldI18nSettingMap.setHostNodeFlag(true);
		Class<?>[] serviceExtendFieldI18nSettingConvToUIParas = {
				ServiceExtendFieldI18nSetting.class,
				ServiceExtendFieldI18nSettingUIModel.class };
		serviceExtendFieldI18nSettingMap
				.setConvToUIMethodParas(serviceExtendFieldI18nSettingConvToUIParas);
		serviceExtendFieldI18nSettingMap.setLogicManager(serviceExtendFieldSettingManager);
		serviceExtendFieldI18nSettingMap
				.setConvToUIMethod(ServiceExtendFieldSettingManager.METHOD_ConvServiceExtendFieldI18nSettingToUI);
		Class<?>[] ServiceExtendFieldI18nSettingConvUIToParas = {
				ServiceExtendFieldI18nSettingUIModel.class,
				ServiceExtendFieldI18nSetting.class };
		serviceExtendFieldI18nSettingMap
				.setConvUIToMethodParas(ServiceExtendFieldI18nSettingConvUIToParas);
		serviceExtendFieldI18nSettingMap
				.setConvUIToMethod(ServiceExtendFieldSettingManager.METHOD_ConvUIToServiceExtendFieldI18nSetting);
		uiModelNodeMapList.add(serviceExtendFieldI18nSettingMap);

		// UI Model Configure of node:[serviceExtendFieldSettingMap]
		UIModelNodeMapConfigure serviceExtendFieldSettingMap = new UIModelNodeMapConfigure();
		serviceExtendFieldSettingMap
				.setSeName(ServiceExtendFieldSetting.SENAME);
		serviceExtendFieldSettingMap
				.setNodeName(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingMap
				.setNodeInstID(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingMap.setHostNodeFlag(false);
		serviceExtendFieldSettingMap.setBaseNodeInstID(ServiceExtendFieldI18nSetting.NODENAME);
		Class<?>[] serviceExtendFieldSettingConvToUIParas = {
				ServiceExtendFieldSetting.class,
				ServiceExtendFieldI18nSettingUIModel.class };
		serviceExtendFieldSettingMap
				.setConvToUIMethodParas(serviceExtendFieldSettingConvToUIParas);
		serviceExtendFieldSettingMap.setLogicManager(serviceExtendFieldSettingManager);

		serviceExtendFieldSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceExtendFieldSettingMap
				.setConvToUIMethod(ServiceExtendFieldSettingManager.METHOD_ConvParentToFieldI18nSettingUI);
		uiModelNodeMapList.add(serviceExtendFieldSettingMap);

		serviceExtendFieldI18nSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceExtendFieldI18nSettingExtensionUnion);
		return resultList;
	}

}

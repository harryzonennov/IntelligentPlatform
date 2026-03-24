package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.ServiceExtendFieldSettingUIModel;
import com.company.IntelligentPlatform.common.service.ServiceExtendFieldSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Service
public class ServiceExtendFieldSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected ServiceExtendFieldSettingManager serviceExtendFieldSettingManager;
	
	@Autowired
	protected ServiceExtendFieldI18nSettingServiceUIModelExtension serviceExtendFieldI18nSettingServiceUIModelExtension;
	
	@Autowired
	protected SerExtendUIControlSetServiceUIModelExtension serExtendUIControlSetServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceExtendFieldI18nSettingServiceUIModelExtension);
		resultList.add(serExtendUIControlSetServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceExtendFieldSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceExtendFieldSettingExtensionUnion
				.setNodeInstId(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingExtensionUnion
				.setNodeName(ServiceExtendFieldSetting.NODENAME);

		// UI Model Configure of node:[ServiceExtendFieldSetting]
		UIModelNodeMapConfigure serviceExtendFieldSettingMap = new UIModelNodeMapConfigure();
		serviceExtendFieldSettingMap
				.setSeName(ServiceExtendFieldSetting.SENAME);
		serviceExtendFieldSettingMap
				.setNodeName(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingMap
				.setNodeInstID(ServiceExtendFieldSetting.NODENAME);
		serviceExtendFieldSettingMap.setHostNodeFlag(true);
		Class<?>[] serviceExtendFieldSettingConvToUIParas = {
				ServiceExtendFieldSetting.class,
				ServiceExtendFieldSettingUIModel.class };
		serviceExtendFieldSettingMap
				.setConvToUIMethodParas(serviceExtendFieldSettingConvToUIParas);
		serviceExtendFieldSettingMap.setLogicManager(serviceExtendFieldSettingManager);
		serviceExtendFieldSettingMap
				.setConvToUIMethod(ServiceExtendFieldSettingManager.METHOD_ConvServiceExtendFieldSettingToUI);
		Class<?>[] ServiceExtendFieldSettingConvUIToParas = {
				ServiceExtendFieldSettingUIModel.class,
				ServiceExtendFieldSetting.class };
		serviceExtendFieldSettingMap
				.setConvUIToMethodParas(ServiceExtendFieldSettingConvUIToParas);
		serviceExtendFieldSettingMap
				.setConvUIToMethod(ServiceExtendFieldSettingManager.METHOD_ConvUIToServiceExtendFieldSetting);
		uiModelNodeMapList.add(serviceExtendFieldSettingMap);

		// UI Model Configure of node:[ServiceExtendFieldI18nSetting]
		UIModelNodeMapConfigure serviceExtensionSettingMap = new UIModelNodeMapConfigure();
		serviceExtensionSettingMap.setSeName(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingMap
				.setNodeName(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingMap
				.setNodeInstID(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingMap.setHostNodeFlag(false);
		serviceExtensionSettingMap
				.setBaseNodeInstID(ServiceExtendFieldSetting.NODENAME);
		serviceExtensionSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] serviceExtensionSettingConvToUIParas = {
				ServiceExtensionSetting.class,
				ServiceExtendFieldSettingUIModel.class };
		serviceExtensionSettingMap
				.setConvToUIMethodParas(serviceExtensionSettingConvToUIParas);
		serviceExtensionSettingMap.setLogicManager(serviceExtendFieldSettingManager);
		serviceExtensionSettingMap
				.setConvToUIMethod(ServiceExtendFieldSettingManager.METHOD_ConvParentToFieldSettingUI);
		uiModelNodeMapList.add(serviceExtensionSettingMap);

		serviceExtendFieldSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceExtendFieldSettingExtensionUnion);
		return resultList;
	}

}

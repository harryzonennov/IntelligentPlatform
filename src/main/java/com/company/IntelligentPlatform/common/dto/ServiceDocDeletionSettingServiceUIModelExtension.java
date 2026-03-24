package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocDeletionSettingManager;
import com.company.IntelligentPlatform.common.model.ServiceDocDeletionSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDocDeletionSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceDocDeletionSettingManager serviceDocDeletionSettingManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocDeletionSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocDeletionSettingExtensionUnion
				.setNodeInstId(ServiceDocDeletionSetting.NODENAME);
		serviceDocDeletionSettingExtensionUnion
				.setNodeName(ServiceDocDeletionSetting.NODENAME);

		// UI Model Configure of node:[ServiceDocDeletionSetting]
		UIModelNodeMapConfigure serviceDocDeletionSettingMap = new UIModelNodeMapConfigure();
		serviceDocDeletionSettingMap
				.setSeName(ServiceDocDeletionSetting.SENAME);
		serviceDocDeletionSettingMap
				.setNodeName(ServiceDocDeletionSetting.NODENAME);
		serviceDocDeletionSettingMap
				.setNodeInstID(ServiceDocDeletionSetting.NODENAME);
		serviceDocDeletionSettingMap.setHostNodeFlag(true);
		Class<?>[] serviceDocDeletionSettingConvToUIParas = {
				ServiceDocDeletionSetting.class,
				ServiceDocDeletionSettingUIModel.class };
		serviceDocDeletionSettingMap
				.setConvToUIMethodParas(serviceDocDeletionSettingConvToUIParas);
		serviceDocDeletionSettingMap.setLogicManager(serviceDocDeletionSettingManager);
		serviceDocDeletionSettingMap
				.setConvToUIMethod(ServiceDocDeletionSettingManager.METHOD_ConvServiceDocDeletionSettingToUI);
		Class<?>[] ServiceDocDeletionSettingConvUIToParas = {
				ServiceDocDeletionSettingUIModel.class,
				ServiceDocDeletionSetting.class };
		serviceDocDeletionSettingMap
				.setConvUIToMethodParas(ServiceDocDeletionSettingConvUIToParas);
		serviceDocDeletionSettingMap
				.setConvUIToMethod(ServiceDocDeletionSettingManager.METHOD_ConvUIToServiceDocDeletionSetting);
		uiModelNodeMapList.add(serviceDocDeletionSettingMap);
		serviceDocDeletionSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocDeletionSettingExtensionUnion);


		serviceDocDeletionSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocDeletionSettingExtensionUnion);
		return resultList;
	}

}

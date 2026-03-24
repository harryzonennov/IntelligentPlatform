package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.ServiceExtensionSettingUIModel;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Service
public class ServiceExtensionSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceExtendFieldSettingServiceUIModelExtension serviceExtendFieldSettingServiceUIModelExtension;
	
	@Autowired
	protected ServiceExtendFieldI18nSettingServiceUIModelExtension serviceExtendFieldI18nSettingServiceUIModelExtension;
	
	@Autowired
	protected SerExtendPageSettingServiceUIModelExtension serExtendPageSettingServiceUIModelExtension;

	@Autowired
	protected SerExtendPageI18nServiceUIModelExtension serExtendPageI18nServiceUIModelExtension;

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceExtendFieldSettingServiceUIModelExtension);
		resultList.add(serExtendPageI18nServiceUIModelExtension);
		resultList.add(serExtendPageSettingServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<String> getBlockSaveSubNodeNameList() {
		return ServiceCollectionsHelper.asList("serExtendPageMetadataUIModelList");
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceExtensionSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceExtensionSettingExtensionUnion
				.setNodeInstId(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingExtensionUnion
				.setNodeName(ServiceExtensionSetting.NODENAME);

		// UI Model Configure of node:[ServiceExtensionSetting]
		UIModelNodeMapConfigure serviceExtensionSettingMap = new UIModelNodeMapConfigure();
		serviceExtensionSettingMap.setSeName(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingMap
				.setNodeName(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingMap
				.setNodeInstID(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingMap.setHostNodeFlag(true);
		Class<?>[] serviceExtensionSettingConvToUIParas = {
				ServiceExtensionSetting.class,
				ServiceExtensionSettingUIModel.class };
		serviceExtensionSettingMap
				.setConvToUIMethodParas(serviceExtensionSettingConvToUIParas);
		serviceExtensionSettingMap
				.setConvToUIMethod(ServiceExtensionSettingManager.METHOD_ConvServiceExtensionSettingToUI);
		Class<?>[] ServiceExtensionSettingConvUIToParas = {
				ServiceExtensionSettingUIModel.class,
				ServiceExtensionSetting.class };
		serviceExtensionSettingMap
				.setConvUIToMethodParas(ServiceExtensionSettingConvUIToParas);
		serviceExtensionSettingMap
				.setConvUIToMethod(ServiceExtensionSettingManager.METHOD_ConvUIToServiceExtensionSetting);
		uiModelNodeMapList.add(serviceExtensionSettingMap);
		serviceExtensionSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceExtensionSettingExtensionUnion);
		return resultList;
	}

}

package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocActConfigureItemManager;
import com.company.IntelligentPlatform.common.service.ServiceDocActionConfigureManager;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDocActionConfigureServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected ServiceDocActionConfigureManager serviceDocActionConfigureManager;

	@Autowired
	protected ServiceDocActConfigureItemServiceUIModelExtension serviceDocActConfigureItemServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceDocActConfigureItemServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocActionConfigureExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocActionConfigureExtensionUnion
				.setNodeInstId(ServiceDocActionConfigure.NODENAME);
		serviceDocActionConfigureExtensionUnion
				.setNodeName(ServiceDocActionConfigure.NODENAME);

		// UI Model Configure of node:[ServiceDocActionConfigure]
		UIModelNodeMapConfigure serviceDocActionConfigureMap = new UIModelNodeMapConfigure();
		serviceDocActionConfigureMap
				.setSeName(ServiceDocActionConfigure.SENAME);
		serviceDocActionConfigureMap
				.setNodeName(ServiceDocActionConfigure.NODENAME);
		serviceDocActionConfigureMap
				.setNodeInstID(ServiceDocActionConfigure.NODENAME);
		serviceDocActionConfigureMap.setHostNodeFlag(true);
		Class<?>[] serviceDocActionConfigureConvToUIParas = {
				ServiceDocActionConfigure.class,
				ServiceDocActionConfigureUIModel.class };
		serviceDocActionConfigureMap
				.setConvToUIMethodParas(serviceDocActionConfigureConvToUIParas);
		serviceDocActionConfigureMap.setLogicManager(serviceDocActionConfigureManager);
		serviceDocActionConfigureMap
				.setConvToUIMethod(ServiceDocActionConfigureManager.METHOD_ConvServiceDocActionConfigureToUI);
		Class<?>[] ServiceDocActionConfigureConvUIToParas = {
				ServiceDocActionConfigureUIModel.class,
				ServiceDocActionConfigure.class };
		serviceDocActionConfigureMap
				.setConvUIToMethodParas(ServiceDocActionConfigureConvUIToParas);
		serviceDocActionConfigureMap
				.setConvUIToMethod(ServiceDocActionConfigureManager.METHOD_ConvUIToServiceDocActionConfigure);
		uiModelNodeMapList.add(serviceDocActionConfigureMap);
		serviceDocActionConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocActionConfigureExtensionUnion);

		UIModelNodeMapConfigure serviceDocumentSettingMap = new UIModelNodeMapConfigure();
		serviceDocumentSettingMap
				.setSeName(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap
				.setNodeName(ServiceDocumentSetting.NODENAME);
		serviceDocumentSettingMap
				.setNodeInstID(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap.setBaseNodeInstID(ServiceDocActionConfigure.NODENAME);
		serviceDocumentSettingMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceDocumentSettingMap.setHostNodeFlag(false);
		Class<?>[] serviceDocumentSettingConvToUIParas = {
				ServiceDocumentSetting.class,
				ServiceDocActionConfigureUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(serviceDocActionConfigureManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(ServiceDocActionConfigureManager.METHOD_ConvDocumentToActionConfigureUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);
		return resultList;
	}

}

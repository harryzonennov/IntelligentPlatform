package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDocInitConfigureServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceDocInitConfigureManager serviceDocInitConfigureManager;

	@Autowired
	protected ServiceDocInitInvolvePartyServiceUIModelExtension serviceDocServiceDocInitInvolvePartyServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceDocServiceDocInitInvolvePartyServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocInitConfigureExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocInitConfigureExtensionUnion
				.setNodeInstId(ServiceDocInitConfigure.NODENAME);
		serviceDocInitConfigureExtensionUnion
				.setNodeName(ServiceDocInitConfigure.NODENAME);

		// UI Model Configure of node:[ServiceDocInitConfigure]
		UIModelNodeMapConfigure serviceDocInitConfigureMap = new UIModelNodeMapConfigure();
		serviceDocInitConfigureMap
				.setSeName(ServiceDocInitConfigure.SENAME);
		serviceDocInitConfigureMap
				.setNodeName(ServiceDocInitConfigure.NODENAME);
		serviceDocInitConfigureMap
				.setNodeInstID(ServiceDocInitConfigure.NODENAME);
		serviceDocInitConfigureMap.setHostNodeFlag(true);
		Class<?>[] serviceDocInitConfigureConvToUIParas = {
				ServiceDocInitConfigure.class,
				ServiceDocInitConfigureUIModel.class };
		serviceDocInitConfigureMap
				.setConvToUIMethodParas(serviceDocInitConfigureConvToUIParas);
		serviceDocInitConfigureMap.setLogicManager(serviceDocInitConfigureManager);
		serviceDocInitConfigureMap
				.setConvToUIMethod(ServiceDocInitConfigureManager.METHOD_ConvServiceDocInitConfigureToUI);
		Class<?>[] ServiceDocInitConfigureConvUIToParas = {
				ServiceDocInitConfigureUIModel.class,
				ServiceDocInitConfigure.class };
		serviceDocInitConfigureMap
				.setConvUIToMethodParas(ServiceDocInitConfigureConvUIToParas);
		serviceDocInitConfigureMap
				.setConvUIToMethod(ServiceDocInitConfigureManager.METHOD_ConvUIToServiceDocInitConfigure);
		uiModelNodeMapList.add(serviceDocInitConfigureMap);
		serviceDocInitConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocInitConfigureExtensionUnion);


		UIModelNodeMapConfigure serviceDocumentSettingMap = new UIModelNodeMapConfigure();
		serviceDocumentSettingMap
				.setSeName(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap
				.setNodeName(ServiceDocumentSetting.NODENAME);
		serviceDocumentSettingMap
				.setNodeInstID(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap.setBaseNodeInstID(ServiceDocInitConfigure.NODENAME);
		serviceDocumentSettingMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceDocumentSettingMap.setHostNodeFlag(false);
		Class<?>[] serviceDocumentSettingConvToUIParas = {
				ServiceDocumentSetting.class,
				ServiceDocInitConfigureUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(serviceDocInitConfigureManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(ServiceDocInitConfigureManager.METHOD_ConvHomeDocumentToConfigureUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);

		serviceDocInitConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocInitConfigureExtensionUnion);
		return resultList;
	}

}

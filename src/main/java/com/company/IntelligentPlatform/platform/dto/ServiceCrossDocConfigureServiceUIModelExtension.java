package com.company.IntelligentPlatform.platform.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.platform.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.platform.service.ServiceCrossDocConfigureManager;
import com.company.IntelligentPlatform.platform.model.ServiceCrossDocConfigure;
import com.company.IntelligentPlatform.platform.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCrossDocConfigureServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceCrossDocConfigureManager serviceCrossDocConfigureManager;

	@Autowired
	protected ServiceCrossDocEventMonitorServiceUIModelExtension serviceCrossDocEventMonitorServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceCrossDocEventMonitorServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceCrossDocConfigureExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceCrossDocConfigureExtensionUnion
				.setNodeInstId(ServiceCrossDocConfigure.NODENAME);
		serviceCrossDocConfigureExtensionUnion
				.setNodeName(ServiceCrossDocConfigure.NODENAME);

		// UI Model Configure of node:[ServiceCrossDocConfigure]
		UIModelNodeMapConfigure serviceCrossDocConfigureMap = new UIModelNodeMapConfigure();
		serviceCrossDocConfigureMap
				.setSeName(ServiceCrossDocConfigure.SENAME);
		serviceCrossDocConfigureMap
				.setNodeName(ServiceCrossDocConfigure.NODENAME);
		serviceCrossDocConfigureMap
				.setNodeInstID(ServiceCrossDocConfigure.NODENAME);
		serviceCrossDocConfigureMap.setHostNodeFlag(true);
		Class<?>[] serviceCrossDocConfigureConvToUIParas = {
				ServiceCrossDocConfigure.class,
				ServiceCrossDocConfigureUIModel.class };
		serviceCrossDocConfigureMap
				.setConvToUIMethodParas(serviceCrossDocConfigureConvToUIParas);
		serviceCrossDocConfigureMap.setLogicManager(serviceCrossDocConfigureManager);
		serviceCrossDocConfigureMap
				.setConvToUIMethod(ServiceCrossDocConfigureManager.METHOD_ConvServiceCrossDocConfigureToUI);
		Class<?>[] ServiceCrossDocConfigureConvUIToParas = {
				ServiceCrossDocConfigureUIModel.class,
				ServiceCrossDocConfigure.class };
		serviceCrossDocConfigureMap
				.setConvUIToMethodParas(ServiceCrossDocConfigureConvUIToParas);
		serviceCrossDocConfigureMap
				.setConvUIToMethod(ServiceCrossDocConfigureManager.METHOD_ConvUIToServiceCrossDocConfigure);
		uiModelNodeMapList.add(serviceCrossDocConfigureMap);
		serviceCrossDocConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceCrossDocConfigureExtensionUnion);

		UIModelNodeMapConfigure serviceDocumentSettingMap = new UIModelNodeMapConfigure();
		serviceDocumentSettingMap
				.setSeName(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap
				.setNodeName(ServiceDocumentSetting.NODENAME);
		serviceDocumentSettingMap
				.setNodeInstID(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap.setBaseNodeInstID(ServiceCrossDocConfigure.NODENAME);
		serviceDocumentSettingMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceDocumentSettingMap.setHostNodeFlag(false);
		Class<?>[] serviceDocumentSettingConvToUIParas = {
				ServiceDocumentSetting.class,
				ServiceCrossDocConfigureUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(serviceCrossDocConfigureManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(ServiceCrossDocConfigureManager.METHOD_ConvHomeDocumentToConfigureUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);

		serviceCrossDocConfigureExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceCrossDocConfigureExtensionUnion);
		return resultList;
	}

}

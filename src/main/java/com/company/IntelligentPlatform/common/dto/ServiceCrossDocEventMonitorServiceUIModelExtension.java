package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceCrossDocEventMonitorManager;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCrossDocEventMonitorServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected ServiceCrossDocEventMonitorManager serviceCrossDocEventMonitorManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceCrossDocEventMonitorExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceCrossDocEventMonitorExtensionUnion
				.setNodeInstId(ServiceCrossDocEventMonitor.NODENAME);
		serviceCrossDocEventMonitorExtensionUnion
				.setNodeName(ServiceCrossDocEventMonitor.NODENAME);

		// UI Model Configure of node:[ServiceCrossDocEventMonitor]
		UIModelNodeMapConfigure serviceCrossDocEventMonitorMap = new UIModelNodeMapConfigure();
		serviceCrossDocEventMonitorMap
				.setSeName(ServiceCrossDocEventMonitor.SENAME);
		serviceCrossDocEventMonitorMap
				.setNodeName(ServiceCrossDocEventMonitor.NODENAME);
		serviceCrossDocEventMonitorMap
				.setNodeInstID(ServiceCrossDocEventMonitor.NODENAME);
		serviceCrossDocEventMonitorMap.setHostNodeFlag(true);
		Class<?>[] serviceCrossDocEventMonitorConvToUIParas = {
				ServiceCrossDocEventMonitor.class,
				ServiceCrossDocEventMonitorUIModel.class };
		serviceCrossDocEventMonitorMap
				.setConvToUIMethodParas(serviceCrossDocEventMonitorConvToUIParas);
		serviceCrossDocEventMonitorMap.setLogicManager(serviceCrossDocEventMonitorManager);
		serviceCrossDocEventMonitorMap
				.setConvToUIMethod(ServiceCrossDocEventMonitorManager.METHOD_ConvServiceCrossDocEventMonitorToUI);
		Class<?>[] ServiceCrossDocEventMonitorConvUIToParas = {
				ServiceCrossDocEventMonitorUIModel.class,
				ServiceCrossDocEventMonitor.class };
		serviceCrossDocEventMonitorMap
				.setConvUIToMethodParas(ServiceCrossDocEventMonitorConvUIToParas);
		serviceCrossDocEventMonitorMap
				.setConvUIToMethod(ServiceCrossDocEventMonitorManager.METHOD_ConvUIToServiceCrossDocEventMonitor);
		uiModelNodeMapList.add(serviceCrossDocEventMonitorMap);

		UIModelNodeMapConfigure serviceCrossDocConfigureMap = new UIModelNodeMapConfigure();
		serviceCrossDocConfigureMap
				.setSeName(ServiceCrossDocConfigure.SENAME);
		serviceCrossDocConfigureMap
				.setNodeName(ServiceCrossDocConfigure.NODENAME);
		serviceCrossDocConfigureMap
				.setNodeInstID(ServiceCrossDocConfigure.NODENAME);
		serviceCrossDocConfigureMap.setBaseNodeInstID(ServiceCrossDocEventMonitor.NODENAME);
		serviceCrossDocConfigureMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceCrossDocConfigureMap.setHostNodeFlag(false);
		Class<?>[] serviceCrossDocConfigureConvToUIParas = {
				ServiceCrossDocConfigure.class,
				ServiceCrossDocEventMonitorUIModel.class };
		serviceCrossDocConfigureMap
				.setConvToUIMethodParas(serviceCrossDocConfigureConvToUIParas);
		serviceCrossDocConfigureMap.setLogicManager(serviceCrossDocEventMonitorManager);
		serviceCrossDocConfigureMap
				.setConvToUIMethod(ServiceCrossDocEventMonitorManager.METHOD_ConvServiceCrossDocConfigureToEventUI);
		uiModelNodeMapList.add(serviceCrossDocConfigureMap);

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
				ServiceCrossDocEventMonitorUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(serviceCrossDocEventMonitorManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(ServiceCrossDocEventMonitorManager.METHOD_ConvHomeDocumentToEventUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);

		serviceCrossDocEventMonitorExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceCrossDocEventMonitorExtensionUnion);
		return resultList;
	}

}

package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceDocActConfigureItemManager;
import com.company.IntelligentPlatform.common.model.ServiceDocActionConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocActConfigureItem;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDocActConfigureItemServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected ServiceDocActConfigureItemManager serviceDocActConfigureItemManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocActConfigureItemExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocActConfigureItemExtensionUnion
				.setNodeInstId(ServiceDocActConfigureItem.NODENAME);
		serviceDocActConfigureItemExtensionUnion
				.setNodeName(ServiceDocActConfigureItem.NODENAME);

		// UI Model Configure of node:[ServiceDocActConfigureItem]
		UIModelNodeMapConfigure serviceDocActConfigureItemMap = new UIModelNodeMapConfigure();
		serviceDocActConfigureItemMap
				.setSeName(ServiceDocActConfigureItem.SENAME);
		serviceDocActConfigureItemMap
				.setNodeName(ServiceDocActConfigureItem.NODENAME);
		serviceDocActConfigureItemMap
				.setNodeInstID(ServiceDocActConfigureItem.NODENAME);
		serviceDocActConfigureItemMap.setHostNodeFlag(true);
		Class<?>[] serviceDocActConfigureItemConvToUIParas = {
				ServiceDocActConfigureItem.class,
				ServiceDocActConfigureItemUIModel.class };
		serviceDocActConfigureItemMap
				.setConvToUIMethodParas(serviceDocActConfigureItemConvToUIParas);
		serviceDocActConfigureItemMap.setLogicManager(serviceDocActConfigureItemManager);
		serviceDocActConfigureItemMap
				.setConvToUIMethod(ServiceDocActConfigureItemManager.METHOD_ConvServiceDocActConfigureItemToUI);
		Class<?>[] ServiceDocActConfigureItemConvUIToParas = {
				ServiceDocActConfigureItemUIModel.class,
				ServiceDocActConfigureItem.class };
		serviceDocActConfigureItemMap
				.setConvUIToMethodParas(ServiceDocActConfigureItemConvUIToParas);
		serviceDocActConfigureItemMap
				.setConvUIToMethod(ServiceDocActConfigureItemManager.METHOD_ConvUIToServiceDocActConfigureItem);
		uiModelNodeMapList.add(serviceDocActConfigureItemMap);

		UIModelNodeMapConfigure serviceDocActionConfigureMap = new UIModelNodeMapConfigure();
		serviceDocActionConfigureMap
				.setSeName(ServiceDocActionConfigure.SENAME);
		serviceDocActionConfigureMap
				.setNodeName(ServiceDocActionConfigure.NODENAME);
		serviceDocActionConfigureMap
				.setNodeInstID(ServiceDocActionConfigure.NODENAME);
		serviceDocActionConfigureMap.setBaseNodeInstID(ServiceDocActConfigureItem.NODENAME);
		serviceDocActionConfigureMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceDocActionConfigureMap.setHostNodeFlag(false);
		Class<?>[] serviceDocActionConfigureConvToUIParas = {
				ServiceDocActionConfigure.class,
				ServiceDocActConfigureItemUIModel.class };
		serviceDocActionConfigureMap
				.setConvToUIMethodParas(serviceDocActionConfigureConvToUIParas);
		serviceDocActionConfigureMap.setLogicManager(serviceDocActConfigureItemManager);
		serviceDocActionConfigureMap
				.setConvToUIMethod(ServiceDocActConfigureItemManager.METHOD_ConvServiceDocActionConfigureToItemUI);
		uiModelNodeMapList.add(serviceDocActionConfigureMap);

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
				ServiceDocActConfigureItemUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap.setLogicManager(serviceDocActConfigureItemManager);
		serviceDocumentSettingMap
				.setConvToUIMethod(ServiceDocActConfigureItemManager.METHOD_ConvDocumentToItemUI);
		uiModelNodeMapList.add(serviceDocumentSettingMap);

		serviceDocActConfigureItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocActConfigureItemExtensionUnion);
		return resultList;
	}

}

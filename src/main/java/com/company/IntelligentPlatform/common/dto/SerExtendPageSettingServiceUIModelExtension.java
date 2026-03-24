package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocActionNodeProxy;
import com.company.IntelligentPlatform.common.service.SerExtendPageSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceExtensionSettingManager;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SerExtendPageMetadata;
import com.company.IntelligentPlatform.common.model.SerExtendPageSetting;
import com.company.IntelligentPlatform.common.model.SerExtendPageSettingActionNode;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

@Service
public class SerExtendPageSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceExtensionSettingManager serviceExtensionSettingManager;

	@Autowired
	protected SerExtendPageSettingManager serExtendPageSettingManager;
	
	@Autowired
	protected SerExtendPageSectionServiceUIModelExtension serExtendPageSectionServiceUIModelExtension;

	@Autowired
	protected SerExtendPageMetadataServiceUIModelExtension serExtendPageMetadataServiceUIModelExtension;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() throws ServiceEntityConfigureException {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serExtendPageSectionServiceUIModelExtension);
		resultList.add(serExtendPageMetadataServiceUIModelExtension);
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
		ServiceUIModelExtensionUnion serExtendPageSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serExtendPageSettingExtensionUnion
				.setNodeInstId(SerExtendPageSetting.NODENAME);
		serExtendPageSettingExtensionUnion
				.setNodeName(SerExtendPageSetting.NODENAME);

		// UI Model Configure of node:[SerExtendPageSetting]
		UIModelNodeMapConfigure serExtendPageSettingMap = new UIModelNodeMapConfigure();
		serExtendPageSettingMap.setSeName(SerExtendPageSetting.SENAME);
		serExtendPageSettingMap.setNodeName(SerExtendPageSetting.NODENAME);
		serExtendPageSettingMap.setNodeInstID(SerExtendPageSetting.NODENAME);
		serExtendPageSettingMap.setHostNodeFlag(true);
		Class<?>[] serExtendPageSettingConvToUIParas = {
				SerExtendPageSetting.class, SerExtendPageSettingUIModel.class };
		serExtendPageSettingMap
				.setConvToUIMethodParas(serExtendPageSettingConvToUIParas);
		serExtendPageSettingMap.setLogicManager(serExtendPageSettingManager);
		serExtendPageSettingMap
				.setConvToUIMethod(SerExtendPageSettingManager.METHOD_ConvSerExtendPageSettingToUI);
		Class<?>[] SerExtendPageSettingConvUIToParas = {
				SerExtendPageSettingUIModel.class, SerExtendPageSetting.class };
		serExtendPageSettingMap
				.setConvUIToMethodParas(SerExtendPageSettingConvUIToParas);
		serExtendPageSettingMap
				.setConvUIToMethod(SerExtendPageSettingManager.METHOD_ConvUIToSerExtendPageSetting);
		uiModelNodeMapList.add(serExtendPageSettingMap);

		// UI Model Configure of node:[serviceExtensionSettingMap]
		UIModelNodeMapConfigure serviceExtensionSettingMap = new UIModelNodeMapConfigure();
		serviceExtensionSettingMap
				.setSeName(ServiceExtensionSetting.SENAME);
		serviceExtensionSettingMap
				.setNodeName(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingMap
				.setNodeInstID(ServiceExtensionSetting.NODENAME);
		serviceExtensionSettingMap.setHostNodeFlag(false);
		serviceExtensionSettingMap
				.setBaseNodeInstID(SerExtendPageSetting.NODENAME);
		Class<?>[] serviceExtensionSettingConvToUIParas = {
				ServiceExtensionSetting.class,
				SerExtendPageSettingUIModel.class };
		serviceExtensionSettingMap
				.setConvToUIMethodParas(serviceExtensionSettingConvToUIParas);
		serviceExtensionSettingMap.setLogicManager(serExtendPageSettingManager);
		serviceExtensionSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		serviceExtensionSettingMap
				.setConvToUIMethod(SerExtendPageSettingManager.METHOD_ConvExtensionSettingToPageSettingUI);
		uiModelNodeMapList.add(serviceExtensionSettingMap);

		

		serExtendPageSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serExtendPageSettingExtensionUnion);
		return resultList;
	}

}

package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemConfigureResourceUIModel;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.service.SystemConfigureResourceManager;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureResource;

@Service
public class SystemConfigureResourceServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemConfigureElementServiceUIModelExtension systemConfigureElementServiceUIModelExtension;

	@Autowired
	protected SystemConfigureExtensionUnionServiceUIModelExtension systemConfigureExtensionUnionServiceUIModelExtension;

	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	@Autowired
	protected SystemConfigureResourceManager systemConfigureResourceManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(systemConfigureElementServiceUIModelExtension);
		resultList.add(systemConfigureExtensionUnionServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemConfigureResourceExtensionUnion = new ServiceUIModelExtensionUnion();
		systemConfigureResourceExtensionUnion
				.setNodeInstId(SystemConfigureResource.NODENAME);
		systemConfigureResourceExtensionUnion
				.setNodeName(SystemConfigureResource.NODENAME);

		// UI Model Configure of node:[SystemConfigureResource]
		UIModelNodeMapConfigure systemConfigureResourceMap = new UIModelNodeMapConfigure();
		systemConfigureResourceMap.setSeName(SystemConfigureResource.SENAME);
		systemConfigureResourceMap
				.setNodeName(SystemConfigureResource.NODENAME);
		systemConfigureResourceMap
				.setNodeInstID(SystemConfigureResource.NODENAME);
		systemConfigureResourceMap.setHostNodeFlag(true);
		Class<?>[] systemConfigureResourceConvToUIParas = {
				SystemConfigureResource.class,
				SystemConfigureResourceUIModel.class };
		systemConfigureResourceMap
				.setConvToUIMethodParas(systemConfigureResourceConvToUIParas);
		systemConfigureResourceMap.setLogicManager(systemConfigureResourceManager);
		systemConfigureResourceMap
				.setConvToUIMethod(SystemConfigureResourceManager.METHOD_ConvSystemConfigureResourceToUI);
		Class<?>[] SystemConfigureResourceConvUIToParas = {
				SystemConfigureResourceUIModel.class,
				SystemConfigureResource.class };
		systemConfigureResourceMap
				.setConvUIToMethodParas(SystemConfigureResourceConvUIToParas);
		systemConfigureResourceMap
				.setConvUIToMethod(SystemConfigureResourceManager.METHOD_ConvUIToSystemConfigureResource);
		uiModelNodeMapList.add(systemConfigureResourceMap);

		// UI Model Configure of node:[SystemConfigureResource]
		UIModelNodeMapConfigure systemConfigureCategoryMap = new UIModelNodeMapConfigure();
		systemConfigureCategoryMap.setSeName(SystemConfigureCategory.SENAME);
		systemConfigureCategoryMap
				.setNodeName(SystemConfigureCategory.NODENAME);
		systemConfigureCategoryMap
				.setNodeInstID(SystemConfigureCategory.SENAME);
		systemConfigureCategoryMap.setHostNodeFlag(false);
		systemConfigureCategoryMap
				.setBaseNodeInstID(SystemConfigureResource.NODENAME);
		systemConfigureCategoryMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] systemConfigureCategoryConvToUIParas = {
				SystemConfigureCategory.class,
				SystemConfigureResourceUIModel.class };
		systemConfigureCategoryMap
				.setConvToUIMethodParas(systemConfigureCategoryConvToUIParas);
		systemConfigureCategoryMap.setLogicManager(systemConfigureResourceManager);
		systemConfigureCategoryMap
				.setConvToUIMethod(SystemConfigureResourceManager.METHOD_ConvCategoryToResourceUI);
		uiModelNodeMapList.add(systemConfigureCategoryMap);

		systemConfigureResourceExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemConfigureResourceExtensionUnion);

		return resultList;
	}

}

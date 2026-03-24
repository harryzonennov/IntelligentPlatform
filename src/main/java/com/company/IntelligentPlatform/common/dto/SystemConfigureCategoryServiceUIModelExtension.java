package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemConfigureCategoryUIModel;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;

@Service
public class SystemConfigureCategoryServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemConfigureResourceServiceUIModelExtension systemConfigureResourceServiceUIModelExtension;

	@Autowired
	protected SystemConfigureCategoryManager systemConfigureCategoryManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(systemConfigureResourceServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemConfigureCategoryExtensionUnion = new ServiceUIModelExtensionUnion();
		systemConfigureCategoryExtensionUnion
				.setNodeInstId(SystemConfigureCategory.SENAME);
		systemConfigureCategoryExtensionUnion
				.setNodeName(SystemConfigureCategory.NODENAME);

		// UI Model Configure of node:[SystemConfigureCategory]
		UIModelNodeMapConfigure systemConfigureCategoryMap = new UIModelNodeMapConfigure();
		systemConfigureCategoryMap.setSeName(SystemConfigureCategory.SENAME);
		systemConfigureCategoryMap
				.setNodeName(SystemConfigureCategory.NODENAME);
		systemConfigureCategoryMap
				.setNodeInstID(SystemConfigureCategory.SENAME);
		systemConfigureCategoryMap.setHostNodeFlag(true);
		Class<?>[] systemConfigureCategoryConvToUIParas = {
				SystemConfigureCategory.class,
				SystemConfigureCategoryUIModel.class };
		systemConfigureCategoryMap
				.setConvToUIMethodParas(systemConfigureCategoryConvToUIParas);
		systemConfigureCategoryMap
				.setConvToUIMethod(SystemConfigureCategoryManager.METHOD_ConvSystemConfigureCategoryToUI);
		Class<?>[] SystemConfigureCategoryConvUIToParas = {
				SystemConfigureCategoryUIModel.class,
				SystemConfigureCategory.class };
		systemConfigureCategoryMap
				.setConvUIToMethodParas(SystemConfigureCategoryConvUIToParas);
		systemConfigureCategoryMap
				.setConvUIToMethod(SystemConfigureCategoryManager.METHOD_ConvUIToSystemConfigureCategory);
		uiModelNodeMapList.add(systemConfigureCategoryMap);
		systemConfigureCategoryExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemConfigureCategoryExtensionUnion);
		return resultList;
	}

}

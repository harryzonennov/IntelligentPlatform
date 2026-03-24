package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemConfigureUIFieldUIModel;
import com.company.IntelligentPlatform.common.service.SystemConfigureCategoryManager;
import com.company.IntelligentPlatform.common.model.SystemConfigureUIField;

@Service
public class SystemConfigureUIFieldServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemConfigureUIFieldExtensionUnion = new ServiceUIModelExtensionUnion();
		systemConfigureUIFieldExtensionUnion
				.setNodeInstId(SystemConfigureUIField.NODENAME);
		systemConfigureUIFieldExtensionUnion
				.setNodeName(SystemConfigureUIField.NODENAME);

		// UI Model Configure of node:[SystemConfigureUIField]
		UIModelNodeMapConfigure systemConfigureUIFieldMap = new UIModelNodeMapConfigure();
		systemConfigureUIFieldMap.setSeName(SystemConfigureUIField.SENAME);
		systemConfigureUIFieldMap.setNodeName(SystemConfigureUIField.NODENAME);
		systemConfigureUIFieldMap
				.setNodeInstID(SystemConfigureUIField.NODENAME);
		systemConfigureUIFieldMap.setHostNodeFlag(true);
		Class<?>[] systemConfigureUIFieldConvToUIParas = {
				SystemConfigureUIField.class,
				SystemConfigureUIFieldUIModel.class };
		systemConfigureUIFieldMap
				.setConvToUIMethodParas(systemConfigureUIFieldConvToUIParas);
		systemConfigureUIFieldMap
				.setConvToUIMethod(SystemConfigureCategoryManager.METHOD_ConvSystemConfigureUIFieldToUI);
		Class<?>[] SystemConfigureUIFieldConvUIToParas = {
				SystemConfigureUIFieldUIModel.class,
				SystemConfigureUIField.class };
		systemConfigureUIFieldMap
				.setConvUIToMethodParas(SystemConfigureUIFieldConvUIToParas);
		systemConfigureUIFieldMap
				.setConvUIToMethod(SystemConfigureCategoryManager.METHOD_ConvUIToSystemConfigureUIField);
		uiModelNodeMapList.add(systemConfigureUIFieldMap);
		systemConfigureUIFieldExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemConfigureUIFieldExtensionUnion);
		return resultList;
	}

}

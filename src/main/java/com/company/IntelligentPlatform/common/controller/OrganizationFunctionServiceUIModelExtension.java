package com.company.IntelligentPlatform.common.controller;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.OrganizationFunctionManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.OrganizationFunctionUIModel;
import com.company.IntelligentPlatform.common.model.OrganizationFunction;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationFunctionServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion organizationFunctionExtensionUnion = new ServiceUIModelExtensionUnion();
		organizationFunctionExtensionUnion
				.setNodeInstId(OrganizationFunction.SENAME);
		organizationFunctionExtensionUnion.setNodeName(OrganizationFunction.NODENAME);

		// UI Model Configure of node:[OrganizationFunction]
		UIModelNodeMapConfigure organizationFunctionMap = new UIModelNodeMapConfigure();
		organizationFunctionMap.setSeName(OrganizationFunction.SENAME);
		organizationFunctionMap.setNodeName(OrganizationFunction.NODENAME);
		organizationFunctionMap.setNodeInstID(OrganizationFunction.SENAME);
		organizationFunctionMap.setHostNodeFlag(true);
		Class<?>[] organizationFunctionConvToUIParas = {
				OrganizationFunction.class, OrganizationFunctionUIModel.class };
		organizationFunctionMap
				.setConvToUIMethodParas(organizationFunctionConvToUIParas);
		organizationFunctionMap
				.setConvToUIMethod(OrganizationFunctionManager.METHOD_ConvOrganizationFunctionToUI);
		Class<?>[] OrganizationFunctionConvUIToParas = {
				OrganizationFunctionUIModel.class, OrganizationFunction.class };
		organizationFunctionMap
				.setConvUIToMethodParas(OrganizationFunctionConvUIToParas);
		organizationFunctionMap
				.setConvUIToMethod(OrganizationFunctionManager.METHOD_ConvUIToOrganizationFunction);
		uiModelNodeMapList.add(organizationFunctionMap);
		organizationFunctionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(organizationFunctionExtensionUnion);
		return resultList;
	}

}

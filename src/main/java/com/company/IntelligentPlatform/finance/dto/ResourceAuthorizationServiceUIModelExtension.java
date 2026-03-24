package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.dto.ResourceAuthorizationUIModel;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.ResourceAuthorization;

@Service
public class ResourceAuthorizationServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion resourceAuthorizationExtensionUnion = new ServiceUIModelExtensionUnion();
		resourceAuthorizationExtensionUnion
				.setNodeInstId(ResourceAuthorization.NODENAME);
		resourceAuthorizationExtensionUnion
				.setNodeName(ResourceAuthorization.NODENAME);

		// UI Model Configure of node:[ResourceAuthorization]
		UIModelNodeMapConfigure resourceAuthorizationMap = new UIModelNodeMapConfigure();
		resourceAuthorizationMap.setSeName(ResourceAuthorization.SENAME);
		resourceAuthorizationMap.setNodeName(ResourceAuthorization.NODENAME);
		resourceAuthorizationMap.setNodeInstID(ResourceAuthorization.NODENAME);
		resourceAuthorizationMap.setHostNodeFlag(true);
		Class<?>[] resourceAuthorizationConvToUIParas = {
				ResourceAuthorization.class, ResourceAuthorizationUIModel.class };
		resourceAuthorizationMap
				.setConvToUIMethodParas(resourceAuthorizationConvToUIParas);
		resourceAuthorizationMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResourceAuthorizationToUI);
		Class<?>[] ResourceAuthorizationConvUIToParas = {
				ResourceAuthorizationUIModel.class, ResourceAuthorization.class };
		resourceAuthorizationMap
				.setConvUIToMethodParas(ResourceAuthorizationConvUIToParas);
		resourceAuthorizationMap
				.setConvUIToMethod(SystemResourceManager.METHOD_ConvUIToResourceAuthorization);
		uiModelNodeMapList.add(resourceAuthorizationMap);
		resourceAuthorizationExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(resourceAuthorizationExtensionUnion);
		return resultList;
	}

}

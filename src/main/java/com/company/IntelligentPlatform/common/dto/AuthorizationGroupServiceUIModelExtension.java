package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;

@Service
public class AuthorizationGroupServiceUIModelExtension extends
		ServiceUIModelExtension {

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion authorizationGroupExtensionUnion = new ServiceUIModelExtensionUnion();
		authorizationGroupExtensionUnion
				.setNodeInstId(AuthorizationGroup.SENAME);
		authorizationGroupExtensionUnion.setNodeName(AuthorizationGroup.NODENAME);

		// UI Model Configure of node:[AuthorizationGroup]
		UIModelNodeMapConfigure authorizationGroupMap = new UIModelNodeMapConfigure();
		authorizationGroupMap.setSeName(AuthorizationGroup.SENAME);
		authorizationGroupMap.setNodeName(AuthorizationGroup.NODENAME);
		authorizationGroupMap.setNodeInstID(AuthorizationGroup.SENAME);
		authorizationGroupMap.setHostNodeFlag(true);
		Class<?>[] authorizationGroupConvToUIParas = {
				AuthorizationGroup.class, AuthorizationGroupUIModel.class };
		authorizationGroupMap
				.setConvToUIMethodParas(authorizationGroupConvToUIParas);
		authorizationGroupMap
				.setConvToUIMethod(AuthorizationGroupManager.METHOD_ConvAuthorizationGroupToUI);
		Class<?>[] AuthorizationGroupConvUIToParas = {
				AuthorizationGroupUIModel.class, AuthorizationGroup.class };
		authorizationGroupMap
				.setConvUIToMethodParas(AuthorizationGroupConvUIToParas);
		authorizationGroupMap
				.setConvUIToMethod(AuthorizationGroupManager.METHOD_ConvUIToAuthorizationGroup);
		uiModelNodeMapList.add(authorizationGroupMap);
		authorizationGroupExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(authorizationGroupExtensionUnion);
		return resultList;
	}

}

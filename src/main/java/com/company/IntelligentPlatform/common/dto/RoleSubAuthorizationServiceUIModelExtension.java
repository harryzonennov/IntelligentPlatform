package com.company.IntelligentPlatform.common.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.RoleSubAuthorizationManager;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.RoleSubAuthorization;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleSubAuthorizationServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected AuthorizationGroupManager authorizationGroupManager;

	@Autowired
	protected RoleSubAuthorizationManager roleSubAuthorizationManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		return null;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion roleSubAuthorizationExtensionUnion = new ServiceUIModelExtensionUnion();
		roleSubAuthorizationExtensionUnion
				.setNodeInstId(RoleSubAuthorization.NODENAME);
		roleSubAuthorizationExtensionUnion.setNodeName(RoleSubAuthorization.NODENAME);

		// UI Model Configure of node:[RoleSubAuthorization]
		UIModelNodeMapConfigure roleSubAuthorizationMap = new UIModelNodeMapConfigure();
		roleSubAuthorizationMap.setSeName(RoleSubAuthorization.SENAME);
		roleSubAuthorizationMap.setNodeName(RoleSubAuthorization.NODENAME);
		roleSubAuthorizationMap.setNodeInstID(RoleSubAuthorization.NODENAME);
		roleSubAuthorizationMap.setHostNodeFlag(true);
		Class<?>[] roleSubAuthorizationConvToUIParas = { RoleSubAuthorization.class,
				RoleSubAuthorizationUIModel.class, List.class };
		roleSubAuthorizationMap
				.setConvToUIMethodParas(roleSubAuthorizationConvToUIParas);
		roleSubAuthorizationMap.setLogicManager(roleSubAuthorizationManager);
		roleSubAuthorizationMap
				.setConvToUIMethod(RoleSubAuthorizationManager.METHOD_ConvRoleSubAuthorizationToUI);
		Class<?>[] roleSubAuthorizationConvUIToParas = {
				RoleSubAuthorizationUIModel.class, RoleSubAuthorization.class };
		roleSubAuthorizationMap
				.setConvUIToMethodParas(roleSubAuthorizationConvUIToParas);
		roleSubAuthorizationMap
				.setConvUIToMethod(RoleSubAuthorizationManager.METHOD_ConvUIToRoleSubAuthorization);
		uiModelNodeMapList.add(roleSubAuthorizationMap);

		// UI Model Configure of node:[AuthorizationObject]
		UIModelNodeMapConfigure authorizationObjectMap = new UIModelNodeMapConfigure();
		authorizationObjectMap.setSeName(AuthorizationObject.SENAME);
		authorizationObjectMap.setNodeName(AuthorizationObject.NODENAME);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		authorizationObjectMap.setNodeInstID(AuthorizationObject.SENAME);
		authorizationObjectMap.setBaseNodeInstID(RoleSubAuthorization.NODENAME);
		authorizationObjectMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] authorizationObjectConvToUIParas = {
				AuthorizationObject.class, RoleSubAuthorizationUIModel.class };
		authorizationObjectMap
				.setConvToUIMethodParas(authorizationObjectConvToUIParas);
		authorizationObjectMap.setLogicManager(roleSubAuthorizationManager);
		authorizationObjectMap
				.setConvToUIMethod(RoleSubAuthorizationManager.METHOD_ConvAuthorizationObjectToSubUI);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		uiModelNodeMapList.add(authorizationObjectMap);
		roleSubAuthorizationExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(roleSubAuthorizationExtensionUnion);

		roleSubAuthorizationExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		return resultList;
	}

}

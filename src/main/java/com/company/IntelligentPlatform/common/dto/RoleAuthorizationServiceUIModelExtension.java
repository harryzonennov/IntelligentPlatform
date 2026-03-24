package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.AuthorizationGroupManager;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.RoleAuthorizationManager;
import com.company.IntelligentPlatform.common.service.RoleManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.RoleAuthorization;

@Service
public class RoleAuthorizationServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected RoleAuthorizationManager roleAuthorizationManager;

	@Autowired
	protected RoleSubAuthorizationServiceUIModelExtension roleSubAuthorizationServiceUIModelExtension;

	@Autowired
	protected AuthorizationGroupManager authorizationGroupManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(roleSubAuthorizationServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion roleAuthorizationExtensionUnion = new ServiceUIModelExtensionUnion();
		roleAuthorizationExtensionUnion
				.setNodeInstId(RoleAuthorization.NODENAME);
		roleAuthorizationExtensionUnion.setNodeName(RoleAuthorization.NODENAME);

		// UI Model Configure of node:[RoleAuthorization]
		UIModelNodeMapConfigure roleAuthorizationMap = new UIModelNodeMapConfigure();
		roleAuthorizationMap.setSeName(RoleAuthorization.SENAME);
		roleAuthorizationMap.setNodeName(RoleAuthorization.NODENAME);
		roleAuthorizationMap.setNodeInstID(RoleAuthorization.NODENAME);
		roleAuthorizationMap.setHostNodeFlag(true);
		Class<?>[] roleAuthorizationConvToUIParas = { RoleAuthorization.class,
				RoleAuthorizationUIModel.class, List.class };
		roleAuthorizationMap.setLogicManager(roleAuthorizationManager);
		roleAuthorizationMap
				.setConvToUIMethodParas(roleAuthorizationConvToUIParas);
		roleAuthorizationMap
				.setConvToUIMethod(RoleAuthorizationManager.METHOD_ConvRoleAuthorizationToUI);
		Class<?>[] roleAuthorizationConvUIToParas = {
				RoleAuthorizationUIModel.class, RoleAuthorization.class };
		roleAuthorizationMap
				.setConvUIToMethodParas(roleAuthorizationConvUIToParas);
		roleAuthorizationMap
				.setConvUIToMethod(RoleAuthorizationManager.METHOD_ConvUIToRoleAuthorization);
		uiModelNodeMapList.add(roleAuthorizationMap);

		// UI Model Configure of node:[AuthorizationObject]
		UIModelNodeMapConfigure authorizationObjectMap = new UIModelNodeMapConfigure();
		authorizationObjectMap.setSeName(AuthorizationObject.SENAME);
		authorizationObjectMap.setNodeName(AuthorizationObject.NODENAME);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		authorizationObjectMap.setNodeInstID(AuthorizationObject.SENAME);
		authorizationObjectMap.setBaseNodeInstID(RoleAuthorization.NODENAME);
		authorizationObjectMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		Class<?>[] authorizationObjectConvToUIParas = {
				AuthorizationObject.class, RoleAuthorizationUIModel.class };
		authorizationObjectMap
				.setConvToUIMethodParas(authorizationObjectConvToUIParas);
		authorizationObjectMap
				.setConvToUIMethod(RoleAuthorizationManager.METHOD_ConvAuthorizationObjectToUI);
		authorizationObjectMap.setLogicManager(roleAuthorizationManager);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		uiModelNodeMapList.add(authorizationObjectMap);
		roleAuthorizationExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(roleAuthorizationExtensionUnion);

		// UI Model Configure of node:[AuthorizationGroup]
		UIModelNodeMapConfigure authorizationgGroupMap = new UIModelNodeMapConfigure();
		authorizationgGroupMap.setSeName(AuthorizationGroup.SENAME);
		authorizationgGroupMap.setNodeName(AuthorizationGroup.NODENAME);
		authorizationgGroupMap
				.setServiceEntityManager(authorizationGroupManager);
		authorizationgGroupMap.setNodeInstID(AuthorizationGroup.SENAME);
		authorizationgGroupMap.setBaseNodeInstID(AuthorizationObject.SENAME);
		authorizationgGroupMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);

		List<SearchConfigConnectCondition> aoGroupConditionList = new ArrayList<>();
		SearchConfigConnectCondition aoGroupCondition0 = new SearchConfigConnectCondition();
		aoGroupCondition0.setSourceFieldName("refAGUUID");
		aoGroupCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		aoGroupConditionList.add(aoGroupCondition0);
		authorizationgGroupMap.setConnectionConditions(aoGroupConditionList);

		Class<?>[] authorizationGroupConvToUIParas = {
				AuthorizationGroup.class, RoleAuthorizationUIModel.class };
		authorizationgGroupMap
				.setConvToUIMethodParas(authorizationGroupConvToUIParas);
		authorizationgGroupMap
				.setConvToUIMethod(RoleAuthorizationManager.METHOD_ConvAuthorizationGroupToUI);
		authorizationgGroupMap.setLogicManager(roleAuthorizationManager);
		uiModelNodeMapList.add(authorizationgGroupMap);

		roleAuthorizationExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		return resultList;
	}

}

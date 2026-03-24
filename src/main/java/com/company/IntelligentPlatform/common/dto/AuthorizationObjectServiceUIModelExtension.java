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
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;

@Service
public class AuthorizationObjectServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected AuthorizationGroupManager authorizationGroupManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		return null;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion authorizationObjectExtensionUnion = new ServiceUIModelExtensionUnion();
		authorizationObjectExtensionUnion
				.setNodeInstId(AuthorizationObject.SENAME);
		authorizationObjectExtensionUnion
				.setNodeName(AuthorizationObject.NODENAME);

		// UI Model Configure of node:[AuthorizationObject]
		UIModelNodeMapConfigure authorizationObjectMap = new UIModelNodeMapConfigure();
		authorizationObjectMap.setSeName(AuthorizationObject.SENAME);
		authorizationObjectMap.setNodeName(AuthorizationObject.NODENAME);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		authorizationObjectMap.setNodeInstID(AuthorizationObject.SENAME);
		authorizationObjectMap.setHostNodeFlag(true);
		Class<?>[] authorizationObjectConvToUIParas = {
				AuthorizationObject.class, AuthorizationObjectUIModel.class };
		authorizationObjectMap
				.setConvToUIMethodParas(authorizationObjectConvToUIParas);
		authorizationObjectMap
				.setConvToUIMethod(AuthorizationObjectManager.Method_ConvAuthorizationObjectToUI);

		Class<?>[] authorizationObjectConvUIToParas = {
				AuthorizationObjectUIModel.class, AuthorizationObject.class };
		authorizationObjectMap
				.setConvUIToMethodParas(authorizationObjectConvUIToParas);
		authorizationObjectMap
				.setConvUIToMethod(AuthorizationObjectManager.Method_ConvUIToAuthorizationObject);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		uiModelNodeMapList.add(authorizationObjectMap);
		authorizationObjectExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(authorizationObjectExtensionUnion);

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
				AuthorizationGroup.class, AuthorizationObjectUIModel.class };
		authorizationgGroupMap
				.setConvToUIMethodParas(authorizationGroupConvToUIParas);
		authorizationgGroupMap
				.setConvToUIMethod(AuthorizationObjectManager.Method_ConvAuthorizationGroupToUI);
		uiModelNodeMapList.add(authorizationgGroupMap);

		authorizationObjectExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		return resultList;
	}

}

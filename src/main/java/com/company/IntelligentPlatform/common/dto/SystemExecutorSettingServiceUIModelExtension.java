package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.SystemExecutorSettingUIModel;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.SystemExecutorSettingManager;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;

@Service
public class SystemExecutorSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemExecutorLogServiceUIModelExtension systemExecutorLogServiceUIModelExtension;

	@Autowired
	protected SystemExecutorSettingManager systemExecutorSettingManager;

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(systemExecutorLogServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion systemExecutorSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		systemExecutorSettingExtensionUnion
				.setNodeInstId(SystemExecutorSetting.SENAME);
		systemExecutorSettingExtensionUnion
				.setNodeName(SystemExecutorSetting.NODENAME);

		// UI Model Configure of node:[SystemExecutorSetting]
		UIModelNodeMapConfigure systemExecutorSettingMap = new UIModelNodeMapConfigure();
		systemExecutorSettingMap.setSeName(SystemExecutorSetting.SENAME);
		systemExecutorSettingMap.setNodeName(SystemExecutorSetting.NODENAME);
		systemExecutorSettingMap.setNodeInstID(SystemExecutorSetting.SENAME);
		systemExecutorSettingMap.setHostNodeFlag(true);
		Class<?>[] systemExecutorSettingConvToUIParas = {
				SystemExecutorSetting.class, SystemExecutorSettingUIModel.class };
		systemExecutorSettingMap
				.setConvToUIMethodParas(systemExecutorSettingConvToUIParas);
		systemExecutorSettingMap
				.setConvToUIMethod(SystemExecutorSettingManager.METHOD_ConvSystemExecutorSettingToUI);
		Class<?>[] SystemExecutorSettingConvUIToParas = {
				SystemExecutorSettingUIModel.class, SystemExecutorSetting.class };
		systemExecutorSettingMap
				.setConvUIToMethodParas(SystemExecutorSettingConvUIToParas);
		systemExecutorSettingMap
				.setConvUIToMethod(SystemExecutorSettingManager.METHOD_ConvUIToSystemExecutorSetting);
		uiModelNodeMapList.add(systemExecutorSettingMap);

		// UI Model Configure of node:[AuthorizationObject]
		UIModelNodeMapConfigure authorizationObjectMap = new UIModelNodeMapConfigure();
		authorizationObjectMap.setSeName(AuthorizationObject.SENAME);
		authorizationObjectMap.setNodeName(AuthorizationObject.NODENAME);
		authorizationObjectMap.setNodeInstID(AuthorizationObject.SENAME);
		authorizationObjectMap.setBaseNodeInstID(SystemExecutorSetting.SENAME);
		authorizationObjectMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		authorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		List<SearchConfigConnectCondition> authorizationObjectConditionList = new ArrayList<>();
		SearchConfigConnectCondition authorizationObjectCondition0 = new SearchConfigConnectCondition();
		authorizationObjectCondition0.setSourceFieldName("refAOUUID");
		authorizationObjectCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		authorizationObjectConditionList.add(authorizationObjectCondition0);
		authorizationObjectMap
				.setConnectionConditions(authorizationObjectConditionList);
		Class<?>[] authorizationObjectConvToUIParas = {
				AuthorizationObject.class, SystemExecutorSettingUIModel.class };
		authorizationObjectMap
				.setConvToUIMethodParas(authorizationObjectConvToUIParas);
		authorizationObjectMap
				.setConvToUIMethod(SystemExecutorSettingManager.METHOD_ConvAuthorizationObjectToUI);
		uiModelNodeMapList.add(authorizationObjectMap);
		
		
		
		systemExecutorSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(systemExecutorSettingExtensionUnion);
		return resultList;
	}

}

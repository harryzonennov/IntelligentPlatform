package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ActionCodeManager;
import com.company.IntelligentPlatform.common.service.AuthorizationObjectManager;
import com.company.IntelligentPlatform.common.service.NavigationGroupSettingManager;
import com.company.IntelligentPlatform.common.service.NavigationSystemSettingManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class NavigationGroupSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected NavigationItemSettingServiceUIModelExtension navigationItemSettingServiceUIModelExtension;

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected NavigationGroupSettingManager navigationGroupSettingManager;

	@Autowired
	protected ActionCodeManager actionCodeManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(navigationItemSettingServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion navigationGroupSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		navigationGroupSettingExtensionUnion
				.setNodeInstId(NavigationGroupSetting.NODENAME);
		navigationGroupSettingExtensionUnion
				.setNodeName(NavigationGroupSetting.NODENAME);

		// UI Model Configure of node:[NavigationGroupSetting]
		UIModelNodeMapConfigure navigationGroupSettingMap = new UIModelNodeMapConfigure();
		navigationGroupSettingMap.setSeName(NavigationGroupSetting.SENAME);
		navigationGroupSettingMap.setNodeName(NavigationGroupSetting.NODENAME);
		navigationGroupSettingMap
				.setNodeInstID(NavigationGroupSetting.NODENAME);
		navigationGroupSettingMap.setHostNodeFlag(true);
		Class<?>[] navigationGroupSettingConvToUIParas = {
				NavigationGroupSetting.class,
				NavigationGroupSettingUIModel.class };
		navigationGroupSettingMap
				.setConvToUIMethodParas(navigationGroupSettingConvToUIParas);
		navigationGroupSettingMap
				.setConvToUIMethod(NavigationGroupSettingManager.METHOD_ConvNavigationGroupSettingToUI);
		navigationGroupSettingMap.setLogicManager(navigationGroupSettingManager);
		Class<?>[] NavigationGroupSettingConvUIToParas = {
				NavigationGroupSettingUIModel.class,
				NavigationGroupSetting.class };
		navigationGroupSettingMap
				.setConvUIToMethodParas(NavigationGroupSettingConvUIToParas);
		navigationGroupSettingMap
				.setConvUIToMethod(NavigationGroupSettingManager.METHOD_ConvUIToNavigationGroupSetting);
		uiModelNodeMapList.add(navigationGroupSettingMap);

		// UI Model Configure of node:[parentSystem]
		UIModelNodeMapConfigure navigationSystemMap = new UIModelNodeMapConfigure();
		navigationSystemMap.setSeName(NavigationSystemSetting.SENAME);
		navigationSystemMap.setNodeName(NavigationSystemSetting.NODENAME);
		navigationSystemMap.setNodeInstID(NavigationSystemSetting.SENAME);
		navigationSystemMap.setBaseNodeInstID(NavigationGroupSetting.NODENAME);
		navigationSystemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		navigationSystemMap
				.setServiceEntityManager(navigationSystemSettingManager);
		navigationSystemMap.setLogicManager(navigationGroupSettingManager);
		Class<?>[] navigationSystemConvToUIParas = {
				NavigationSystemSetting.class,
				NavigationGroupSettingUIModel.class };
		navigationSystemMap
				.setConvToUIMethodParas(navigationSystemConvToUIParas);
		navigationSystemMap
				.setConvToUIMethod(NavigationGroupSettingManager.METHOD_ConvSystemToGroupUI);
		uiModelNodeMapList.add(navigationSystemMap);

		// UI Model Configure of node:[GroupDefItem]
		UIModelNodeMapConfigure groupDefItemMap = new UIModelNodeMapConfigure();
		groupDefItemMap.setSeName(NavigationItemSetting.SENAME);
		groupDefItemMap.setNodeName(NavigationItemSetting.NODENAME);
		groupDefItemMap.setNodeInstID("groupDefItem");
		groupDefItemMap.setBaseNodeInstID(NavigationGroupSetting.NODENAME);
		groupDefItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		groupDefItemMap.setServiceEntityManager(navigationSystemSettingManager);
		List<SearchConfigConnectCondition> groupDefItemConditionList = new ArrayList<>();
		SearchConfigConnectCondition groupDefItemCondition0 = new SearchConfigConnectCondition();
		groupDefItemCondition0.setSourceFieldName("refDefItemUUID");
		groupDefItemCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		groupDefItemConditionList.add(groupDefItemCondition0);
		groupDefItemMap.setConnectionConditions(groupDefItemConditionList);
		Class<?>[] groupDefItemConvToUIParas = { NavigationItemSetting.class,
				NavigationGroupSettingUIModel.class };
		groupDefItemMap.setConvToUIMethodParas(groupDefItemConvToUIParas);
		groupDefItemMap.setLogicManager(navigationGroupSettingManager);
		groupDefItemMap
				.setConvToUIMethod(NavigationGroupSettingManager.METHOD_ConvGroupDefItemToUI);
		uiModelNodeMapList.add(groupDefItemMap);

		// UI Model Configure of node:[groupAuthorizationObject]
		UIModelNodeMapConfigure groupAuthorizationObjectMap = new UIModelNodeMapConfigure();
		groupAuthorizationObjectMap.setSeName(AuthorizationObject.SENAME);
		groupAuthorizationObjectMap.setNodeName(AuthorizationObject.NODENAME);
		groupAuthorizationObjectMap.setNodeInstID("groupAuthorizationObject");
		groupAuthorizationObjectMap
				.setBaseNodeInstID(NavigationGroupSetting.NODENAME);
		groupAuthorizationObjectMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		groupAuthorizationObjectMap
				.setServiceEntityManager(authorizationObjectManager);
		Class<?>[] groupAuthorizationObjectConvToUIParas = {
				AuthorizationObject.class, NavigationGroupSettingUIModel.class };
		groupAuthorizationObjectMap
				.setConvToUIMethodParas(groupAuthorizationObjectConvToUIParas);
		groupAuthorizationObjectMap.setLogicManager(navigationGroupSettingManager);
		groupAuthorizationObjectMap
				.setConvToUIMethod(NavigationGroupSettingManager.METHOD_ConvAuthorizationObjectToGroupUI);
		uiModelNodeMapList.add(groupAuthorizationObjectMap);

		// UI Model Configure of node:[groupActionCode]
		UIModelNodeMapConfigure groupActionCodeMap = new UIModelNodeMapConfigure();
		groupActionCodeMap.setSeName(ActionCode.SENAME);
		groupActionCodeMap.setNodeName(ActionCode.NODENAME);
		groupActionCodeMap.setNodeInstID("groupActionCode");
		groupActionCodeMap.setBaseNodeInstID(NavigationGroupSetting.NODENAME);
		groupActionCodeMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		groupActionCodeMap.setServiceEntityManager(actionCodeManager);
		List<SearchConfigConnectCondition> groupActionCodeConditionList = new ArrayList<>();
		SearchConfigConnectCondition groupActionCodeCondition0 = new SearchConfigConnectCondition();
		groupActionCodeCondition0.setSourceFieldName("refAuthorActionCodeUUID");
		groupActionCodeCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		groupActionCodeConditionList.add(groupActionCodeCondition0);
		groupActionCodeMap
				.setConnectionConditions(groupActionCodeConditionList);
		Class<?>[] groupActionCodeConvToUIParas = { ActionCode.class,
				NavigationGroupSettingUIModel.class };
		groupActionCodeMap.setLogicManager(navigationGroupSettingManager);
		groupActionCodeMap.setConvToUIMethodParas(groupActionCodeConvToUIParas);
		groupActionCodeMap
				.setConvToUIMethod(NavigationSystemSettingManager.METHOD_ConvGroupActionCodeToUI);
		uiModelNodeMapList.add(groupActionCodeMap);

		navigationGroupSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(navigationGroupSettingExtensionUnion);
		return resultList;
	}

}

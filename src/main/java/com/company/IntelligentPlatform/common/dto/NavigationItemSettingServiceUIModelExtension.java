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
import com.company.IntelligentPlatform.common.service.NavigationItemSettingManager;
import com.company.IntelligentPlatform.common.service.NavigationSystemSettingManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;

@Service
public class NavigationItemSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected NavigationSystemSettingManager navigationSystemSettingManager;

	@Autowired
	protected AuthorizationObjectManager authorizationObjectManager;

	@Autowired
	protected NavigationItemSettingManager navigationItemSettingManager;

	@Autowired
	protected ActionCodeManager actionCodeManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
        return new ArrayList<>();
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion navigationItemSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		navigationItemSettingExtensionUnion
				.setNodeInstId(NavigationItemSetting.NODENAME);
		navigationItemSettingExtensionUnion
				.setNodeName(NavigationItemSetting.NODENAME);

		// UI Model Configure of node:[NavigationItemSetting]
		UIModelNodeMapConfigure navigationItemSettingMap = new UIModelNodeMapConfigure();
		navigationItemSettingMap.setSeName(NavigationItemSetting.SENAME);
		navigationItemSettingMap.setNodeName(NavigationItemSetting.NODENAME);
		navigationItemSettingMap.setNodeInstID(NavigationItemSetting.NODENAME);
		navigationItemSettingMap.setHostNodeFlag(true);
		Class<?>[] navigationItemSettingConvToUIParas = {
				NavigationItemSetting.class, NavigationItemSettingUIModel.class };
		navigationItemSettingMap
				.setConvToUIMethodParas(navigationItemSettingConvToUIParas);
		navigationItemSettingMap
				.setConvToUIMethod(NavigationItemSettingManager.METHOD_ConvNavigationItemSettingToUI);
		navigationItemSettingMap.setLogicManager(navigationItemSettingManager);
		Class<?>[] NavigationItemSettingConvUIToParas = {
				NavigationItemSettingUIModel.class, NavigationItemSetting.class };
		navigationItemSettingMap
				.setConvUIToMethodParas(NavigationItemSettingConvUIToParas);
		navigationItemSettingMap
				.setConvUIToMethod(NavigationItemSettingManager.METHOD_ConvUIToNavigationItemSetting);
		uiModelNodeMapList.add(navigationItemSettingMap);

		// UI Model Configure of node:[parentGroup]
		UIModelNodeMapConfigure navigationGroupMap = new UIModelNodeMapConfigure();
		navigationGroupMap.setSeName(NavigationGroupSetting.SENAME);
		navigationGroupMap.setNodeName(NavigationGroupSetting.NODENAME);
		navigationGroupMap.setNodeInstID(NavigationGroupSetting.SENAME);
		navigationGroupMap.setBaseNodeInstID(NavigationItemSetting.NODENAME);
		navigationGroupMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] navigationGroupConvToUIParas = {
				NavigationGroupSetting.class,
				NavigationItemSettingUIModel.class };
		navigationGroupMap.setConvToUIMethodParas(navigationGroupConvToUIParas);
		navigationGroupMap.setLogicManager(navigationItemSettingManager);
		navigationGroupMap
				.setConvToUIMethod(NavigationItemSettingManager.METHOD_ConvGroupToItemUI);
		uiModelNodeMapList.add(navigationGroupMap);

		// UI Model Configure of node:[DefaultChildItemSetting]
		UIModelNodeMapConfigure defaultChildItemSettingMap = new UIModelNodeMapConfigure();
		defaultChildItemSettingMap.setSeName(NavigationItemSetting.SENAME);
		defaultChildItemSettingMap.setNodeName(NavigationItemSetting.NODENAME);
		defaultChildItemSettingMap.setNodeInstID("DefaultChildItemSetting");
		defaultChildItemSettingMap
				.setBaseNodeInstID(NavigationItemSetting.NODENAME);
		defaultChildItemSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		defaultChildItemSettingMap
				.setServiceEntityManager(navigationSystemSettingManager);
		defaultChildItemSettingMap.setLogicManager(navigationItemSettingManager);
		List<SearchConfigConnectCondition> defaultChildItemSettingConditionList = new ArrayList<>();
		SearchConfigConnectCondition defaultChildItemSettingCondition0 = new SearchConfigConnectCondition();
		defaultChildItemSettingCondition0.setSourceFieldName("refDefItemUUID");
		defaultChildItemSettingCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		defaultChildItemSettingConditionList
				.add(defaultChildItemSettingCondition0);
		defaultChildItemSettingMap
				.setConnectionConditions(defaultChildItemSettingConditionList);
		Class<?>[] defaultChildItemSettingConvToUIParas = {
				NavigationItemSetting.class, NavigationItemSettingUIModel.class };
		defaultChildItemSettingMap
				.setConvToUIMethodParas(defaultChildItemSettingConvToUIParas);
		defaultChildItemSettingMap
				.setConvToUIMethod(NavigationItemSettingManager.METHOD_ConvDefaultChildItemSettingToUI);
		uiModelNodeMapList.add(defaultChildItemSettingMap);

		// UI Model Configure of node:[ParentItemSetting]
		UIModelNodeMapConfigure parentItemSettingMap = new UIModelNodeMapConfigure();
		parentItemSettingMap.setSeName(NavigationItemSetting.SENAME);
		parentItemSettingMap.setNodeName(NavigationItemSetting.NODENAME);
		parentItemSettingMap.setNodeInstID("parentItemSetting");
		parentItemSettingMap.setBaseNodeInstID(NavigationItemSetting.NODENAME);
		parentItemSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		parentItemSettingMap
				.setServiceEntityManager(navigationSystemSettingManager);
		parentItemSettingMap.setLogicManager(navigationItemSettingManager);
		List<SearchConfigConnectCondition> parentItemSettingConditionList = new ArrayList<>();
		SearchConfigConnectCondition parentItemSettingCondition0 = new SearchConfigConnectCondition();
		parentItemSettingCondition0.setSourceFieldName("parentElementUUID");
		parentItemSettingCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		parentItemSettingConditionList.add(parentItemSettingCondition0);
		parentItemSettingMap
				.setConnectionConditions(parentItemSettingConditionList);
		Class<?>[] parentItemSettingConvToUIParas = {
				NavigationItemSetting.class, NavigationItemSettingUIModel.class };
		parentItemSettingMap
				.setConvToUIMethodParas(parentItemSettingConvToUIParas);
		parentItemSettingMap
				.setConvToUIMethod(NavigationItemSettingManager.METHOD_ConvParentItemSettingToUI);
		uiModelNodeMapList.add(parentItemSettingMap);

		// UI Model Configure of node:[ItemAuthorization]
		UIModelNodeMapConfigure itemAuthorizationMap = new UIModelNodeMapConfigure();
		itemAuthorizationMap.setSeName(AuthorizationObject.SENAME);
		itemAuthorizationMap.setNodeName(AuthorizationObject.NODENAME);
		itemAuthorizationMap.setNodeInstID("itemAuthorization");
		itemAuthorizationMap.setBaseNodeInstID(NavigationItemSetting.NODENAME);
		itemAuthorizationMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		itemAuthorizationMap
				.setServiceEntityManager(authorizationObjectManager);
		itemAuthorizationMap.setLogicManager(navigationItemSettingManager);
		List<SearchConfigConnectCondition> itemAuthorizationConditionList = new ArrayList<>();
		SearchConfigConnectCondition itemAuthorizationCondition0 = new SearchConfigConnectCondition();
		itemAuthorizationCondition0
				.setSourceFieldName("refSimAuthorObjectUUID");
		itemAuthorizationCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		itemAuthorizationConditionList.add(itemAuthorizationCondition0);
		itemAuthorizationMap
				.setConnectionConditions(itemAuthorizationConditionList);
		Class<?>[] itemAuthorizationConvToUIParas = {
				AuthorizationObject.class, NavigationItemSettingUIModel.class };
		itemAuthorizationMap
				.setConvToUIMethodParas(itemAuthorizationConvToUIParas);
		itemAuthorizationMap
				.setConvToUIMethod(NavigationItemSettingManager.METHOD_ConvItemAuthorizationToUI);
		uiModelNodeMapList.add(itemAuthorizationMap);

		// UI Model Configure of node:[ItemActionCode]
		UIModelNodeMapConfigure itemActionCodeMap = new UIModelNodeMapConfigure();
		itemActionCodeMap.setSeName(ActionCode.SENAME);
		itemActionCodeMap.setNodeName(ActionCode.NODENAME);
		itemActionCodeMap.setNodeInstID("itemActionCode");
		itemActionCodeMap.setBaseNodeInstID(NavigationItemSetting.NODENAME);
		itemActionCodeMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		itemActionCodeMap.setServiceEntityManager(actionCodeManager);
		List<SearchConfigConnectCondition> itemActionCodeConditionList = new ArrayList<>();
		SearchConfigConnectCondition itemActionCodeCondition0 = new SearchConfigConnectCondition();
		itemActionCodeCondition0.setSourceFieldName("refAuthorActionCodeUUID");
		itemActionCodeCondition0
				.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		itemActionCodeConditionList.add(itemActionCodeCondition0);
		itemActionCodeMap.setConnectionConditions(itemActionCodeConditionList);
		itemActionCodeMap.setLogicManager(navigationItemSettingManager);
		Class<?>[] itemActionCodeConvToUIParas = { ActionCode.class,
				NavigationItemSettingUIModel.class };
		itemActionCodeMap.setConvToUIMethodParas(itemActionCodeConvToUIParas);
		itemActionCodeMap
				.setConvToUIMethod(NavigationItemSettingManager.METHOD_ConvItemActionCodeToUI);
		uiModelNodeMapList.add(itemActionCodeMap);
		navigationItemSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(navigationItemSettingExtensionUnion);
		return resultList;
	}

}

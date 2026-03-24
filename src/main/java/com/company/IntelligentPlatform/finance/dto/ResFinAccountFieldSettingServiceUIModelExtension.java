package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.SystemResourceManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;

@Service
public class ResFinAccountFieldSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected SystemResourceManager systemResourceManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion resFinAccountFieldSettingUnion = new ServiceUIModelExtensionUnion();
		resFinAccountFieldSettingUnion
				.setNodeInstId(ResFinAccountFieldSetting.NODENAME);
		resFinAccountFieldSettingUnion
				.setNodeName(ResFinAccountFieldSetting.NODENAME);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountFieldSettingMap = new UIModelNodeMapConfigure();
		resFinAccountFieldSettingMap
				.setSeName(ResFinAccountFieldSetting.SENAME);
		resFinAccountFieldSettingMap
				.setNodeName(ResFinAccountFieldSetting.NODENAME);
		resFinAccountFieldSettingMap
				.setNodeInstID(ResFinAccountFieldSetting.NODENAME);
		resFinAccountFieldSettingMap.setHostNodeFlag(true);
		resFinAccountFieldSettingMap
				.setServiceEntityManager(systemResourceManager);
		Class<?>[] resFinAccountFieldSettingConvToUIParas = {
				ResFinAccountFieldSetting.class,
				ResFinAccountFieldSettingUIModel.class };
		resFinAccountFieldSettingMap
				.setConvToUIMethodParas(resFinAccountFieldSettingConvToUIParas);
		resFinAccountFieldSettingMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResFinAccountFieldSettingToUI);
		Class<?>[] ResFinAccountFieldSettingConvUIToParas = {
				ResFinAccountFieldSettingUIModel.class,
				ResFinAccountFieldSetting.class };
		resFinAccountFieldSettingMap
				.setConvUIToMethodParas(ResFinAccountFieldSettingConvUIToParas);
		resFinAccountFieldSettingMap
				.setConvUIToMethod(SystemResourceManager.METHOD_ConvUIToResFinAccountFieldSetting);
		uiModelNodeMapList.add(resFinAccountFieldSettingMap);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountSettingMap = new UIModelNodeMapConfigure();
		resFinAccountSettingMap.setSeName(ResFinAccountSetting.SENAME);
		resFinAccountSettingMap.setNodeName(ResFinAccountSetting.NODENAME);
		resFinAccountSettingMap.setNodeInstID(ResFinAccountSetting.NODENAME);
		resFinAccountSettingMap
				.setBaseNodeInstID(ResFinAccountFieldSetting.NODENAME);
		resFinAccountSettingMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		resFinAccountSettingMap.setServiceEntityManager(systemResourceManager);
		Class<?>[] resFinAccountSettingConvToUIParas = {
				ResFinAccountSetting.class,
				ResFinAccountFieldSettingUIModel.class };
		resFinAccountSettingMap
				.setConvToUIMethodParas(resFinAccountSettingConvToUIParas);
		resFinAccountSettingMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResFinAccountSettingToFieldSettingUI);

		uiModelNodeMapList.add(resFinAccountSettingMap);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountTitleMap = new UIModelNodeMapConfigure();
		resFinAccountTitleMap.setSeName(FinAccountTitle.SENAME);
		resFinAccountTitleMap.setNodeName(FinAccountTitle.NODENAME);
		resFinAccountTitleMap.setNodeInstID(FinAccountTitle.SENAME);
		resFinAccountTitleMap
				.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		resFinAccountTitleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		resFinAccountTitleMap.setServiceEntityManager(systemResourceManager);
		Class<?>[] resFinAccountTitleConvToUIParas = { FinAccountTitle.class,
				ResFinAccountFieldSettingUIModel.class };
		resFinAccountTitleMap
				.setConvToUIMethodParas(resFinAccountTitleConvToUIParas);
		resFinAccountTitleMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvFinAccountTitleToFieldSettingUI);

		uiModelNodeMapList.add(resFinAccountTitleMap);
		
		resFinAccountFieldSettingUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(resFinAccountFieldSettingUnion);
		return resultList;
	}

}

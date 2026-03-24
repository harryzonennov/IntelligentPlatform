package com.company.IntelligentPlatform.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.service.SystemResourceManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;
import com.company.IntelligentPlatform.common.model.SystemResource;

@Service
public class ResFinAccountSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ResFinAccountFieldSettingServiceUIModelExtension resFinAccountFieldSettingServiceUIModelExtension;

	@Autowired
	protected ResFinAccountProcessCodeServiceUIModelExtension resFinAccountProcessCodeServiceUIModelExtension;

	@Autowired
	protected SystemResourceManager systemResourceManager;
	
	@Autowired
	protected FinAccountTitleManager finAccountTitleManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		resultList.add(resFinAccountFieldSettingServiceUIModelExtension);
		resultList.add(resFinAccountProcessCodeServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<UIModelNodeMapConfigure>();
		ServiceUIModelExtensionUnion resFinAccountSettingUnion = new ServiceUIModelExtensionUnion();
		resFinAccountSettingUnion.setNodeInstId(ResFinAccountSetting.NODENAME);
		resFinAccountSettingUnion.setNodeName(ResFinAccountSetting.NODENAME);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountSettingMap = new UIModelNodeMapConfigure();
		resFinAccountSettingMap.setSeName(ResFinAccountSetting.SENAME);
		resFinAccountSettingMap.setNodeName(ResFinAccountSetting.NODENAME);
		resFinAccountSettingMap.setNodeInstID(ResFinAccountSetting.NODENAME);
		resFinAccountSettingMap.setHostNodeFlag(true);
		resFinAccountSettingMap.setServiceEntityManager(systemResourceManager);
		Class<?>[] resFinAccountSettingConvToUIParas = {
				ResFinAccountSetting.class, ResFinAccountSettingUIModel.class };
		resFinAccountSettingMap
				.setConvToUIMethodParas(resFinAccountSettingConvToUIParas);
		resFinAccountSettingMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResFinAccountSettingToUI);
		Class<?>[] ResFinAccountSettingConvUIToParas = {
				ResFinAccountSettingUIModel.class, ResFinAccountSetting.class };
		resFinAccountSettingMap
				.setConvUIToMethodParas(ResFinAccountSettingConvUIToParas);
		resFinAccountSettingMap
				.setConvUIToMethod(SystemResourceManager.METHOD_ConvUIToResFinAccountSetting);
		uiModelNodeMapList.add(resFinAccountSettingMap);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinAccountTitleMap = new UIModelNodeMapConfigure();
		resFinAccountTitleMap.setSeName(FinAccountTitle.SENAME);
		resFinAccountTitleMap.setNodeName(FinAccountTitle.NODENAME);
		resFinAccountTitleMap.setNodeInstID(FinAccountTitle.SENAME);
		resFinAccountTitleMap.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		resFinAccountTitleMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_REFTO_SOURCE);
		resFinAccountTitleMap.setServiceEntityManager(finAccountTitleManager);
		Class<?>[] resFinAccountTitleConvToUIParas = { FinAccountTitle.class,
				ResFinAccountSettingUIModel.class };
		resFinAccountTitleMap
				.setConvToUIMethodParas(resFinAccountTitleConvToUIParas);
		resFinAccountTitleMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvFinAccountTitleToSettingUI);

		uiModelNodeMapList.add(resFinAccountTitleMap);

		// UI Model Configure of node:[ResFinAccountSetting]
		UIModelNodeMapConfigure resFinSystemResourceMap = new UIModelNodeMapConfigure();
		resFinSystemResourceMap.setSeName(SystemResource.SENAME);
		resFinSystemResourceMap.setNodeName(SystemResource.NODENAME);
		resFinSystemResourceMap.setNodeInstID(SystemResource.SENAME);
		resFinSystemResourceMap
				.setBaseNodeInstID(ResFinAccountSetting.NODENAME);
		resFinSystemResourceMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		resFinSystemResourceMap.setServiceEntityManager(systemResourceManager);
		Class<?>[] resResFinSystemResourceConvToUIParas = {
				SystemResource.class, ResFinAccountSettingUIModel.class };
		resFinSystemResourceMap
				.setConvToUIMethodParas(resResFinSystemResourceConvToUIParas);
		resFinSystemResourceMap
				.setConvToUIMethod(SystemResourceManager.METHOD_ConvResFinSystemResToSettingUI);

		uiModelNodeMapList.add(resFinSystemResourceMap);

		resFinAccountSettingUnion.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(resFinAccountSettingUnion);
		return resultList;
	}

}

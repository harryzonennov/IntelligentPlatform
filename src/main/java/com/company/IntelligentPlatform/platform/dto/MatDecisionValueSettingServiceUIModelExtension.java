package com.company.IntelligentPlatform.platform.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.dto.MatDecisionValueSettingUIModel;
import com.company.IntelligentPlatform.platform.service.MatDecisionValueSettingManager;
import com.company.IntelligentPlatform.platform.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.platform.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.platform.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.platform.controller.UIModelNodeMapConfigure;

@Service
public class MatDecisionValueSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MatDecisionValueSettingManager matDecisionValueSettingManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion matDecisionValueSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		matDecisionValueSettingExtensionUnion
				.setNodeInstId(MatDecisionValueSetting.NODENAME);
		matDecisionValueSettingExtensionUnion
				.setNodeName(MatDecisionValueSetting.NODENAME);

		// UI Model Configure of node:[MatDecisionValueSetting]
		UIModelNodeMapConfigure matDecisionValueSettingMap = new UIModelNodeMapConfigure();
		matDecisionValueSettingMap.setSeName(MatDecisionValueSetting.SENAME);
		matDecisionValueSettingMap
				.setNodeName(MatDecisionValueSetting.NODENAME);
		matDecisionValueSettingMap
				.setNodeInstID(MatDecisionValueSetting.NODENAME);
		matDecisionValueSettingMap.setHostNodeFlag(true);
		Class<?>[] matDecisionValueSettingConvToUIParas = {
				MatDecisionValueSetting.class,
				MatDecisionValueSettingUIModel.class };
		matDecisionValueSettingMap
				.setConvToUIMethodParas(matDecisionValueSettingConvToUIParas);
		matDecisionValueSettingMap
				.setConvToUIMethod(MatDecisionValueSettingManager.METHOD_ConvMatDecisionValueSettingToUI);
		matDecisionValueSettingMap.setLogicManager(matDecisionValueSettingManager);
		Class<?>[] MatDecisionValueSettingConvUIToParas = {
				MatDecisionValueSettingUIModel.class,
				MatDecisionValueSetting.class };
		matDecisionValueSettingMap
				.setConvUIToMethodParas(MatDecisionValueSettingConvUIToParas);
		matDecisionValueSettingMap
				.setConvUIToMethod(MatDecisionValueSettingManager.METHOD_ConvUIToMatDecisionValueSetting);
		uiModelNodeMapList.add(matDecisionValueSettingMap);

		// UI Model Configure of node:[MatConfigHeaderCondition]
		UIModelNodeMapConfigure materialConfigureTemplateConditionMap = new UIModelNodeMapConfigure();
		materialConfigureTemplateConditionMap
				.setSeName(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateConditionMap.setBaseNodeInstID(MatDecisionValueSetting.NODENAME);
		materialConfigureTemplateConditionMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		materialConfigureTemplateConditionMap
				.setNodeName(MaterialConfigureTemplate.NODENAME);
		materialConfigureTemplateConditionMap
				.setNodeInstID(MaterialConfigureTemplate.SENAME);
		Class<?>[] materialConfigureTemplateConditionConvToUIParas = {
				MaterialConfigureTemplate.class,
				MatDecisionValueSettingUIModel.class };
		materialConfigureTemplateConditionMap
				.setConvToUIMethodParas(materialConfigureTemplateConditionConvToUIParas);
		materialConfigureTemplateConditionMap
				.setConvToUIMethod(MatDecisionValueSettingManager.METHOD_ConvConfigureTemplateToDecisionValueUI);
		materialConfigureTemplateConditionMap.setLogicManager(matDecisionValueSettingManager);
		uiModelNodeMapList.add(materialConfigureTemplateConditionMap);

		matDecisionValueSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(matDecisionValueSettingExtensionUnion);
		return resultList;
	}

}

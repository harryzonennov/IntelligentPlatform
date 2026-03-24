package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MatConfigExtPropertyManager;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.model.RegisteredProductExtendProperty;
import com.company.IntelligentPlatform.common.model.StandardMaterialUnit;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class MatConfigExtPropertySettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MatConfigExtPropertyManager matConfigExtPropertyManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion matConfigExtPropertySettingExtensionUnion = new ServiceUIModelExtensionUnion();
		matConfigExtPropertySettingExtensionUnion
				.setNodeInstId(MatConfigExtPropertySetting.NODENAME);
		matConfigExtPropertySettingExtensionUnion
				.setNodeName(MatConfigExtPropertySetting.NODENAME);

		// UI Model Configure of node:[MatConfigExtPropertySetting]
		UIModelNodeMapConfigure matConfigExtPropertySettingMap = new UIModelNodeMapConfigure();
		matConfigExtPropertySettingMap
				.setSeName(MatConfigExtPropertySetting.SENAME);
		matConfigExtPropertySettingMap
				.setNodeName(MatConfigExtPropertySetting.NODENAME);
		matConfigExtPropertySettingMap
				.setNodeInstID(MatConfigExtPropertySetting.NODENAME);
		matConfigExtPropertySettingMap.setHostNodeFlag(true);
		Class<?>[] matConfigExtPropertySettingConvToUIParas = {
				MatConfigExtPropertySetting.class,
				MatConfigExtPropertySettingUIModel.class };
		matConfigExtPropertySettingMap
				.setConvToUIMethodParas(matConfigExtPropertySettingConvToUIParas);
		matConfigExtPropertySettingMap
				.setConvToUIMethod(MatConfigExtPropertyManager.METHOD_ConvMatConfigExtPropertySettingToUI);
		Class<?>[] MatConfigExtPropertySettingConvUIToParas = {
				MatConfigExtPropertySettingUIModel.class,
				MatConfigExtPropertySetting.class };
		matConfigExtPropertySettingMap.setLogicManager(matConfigExtPropertyManager);
		matConfigExtPropertySettingMap
				.setConvUIToMethodParas(MatConfigExtPropertySettingConvUIToParas);
		matConfigExtPropertySettingMap
				.setConvUIToMethod(MatConfigExtPropertyManager.METHOD_ConvUIToMatConfigExtPropertySetting);
		uiModelNodeMapList.add(matConfigExtPropertySettingMap);

		// UI Model Configure of node:[StandardMaterialUnit]
		UIModelNodeMapConfigure standardMaterialUnitMap = new UIModelNodeMapConfigure();
		standardMaterialUnitMap.setSeName(StandardMaterialUnit.SENAME);
		standardMaterialUnitMap.setNodeName(StandardMaterialUnit.NODENAME);
		standardMaterialUnitMap
				.setNodeInstID(RegisteredProductExtendProperty.NODENAME);
		standardMaterialUnitMap
				.setBaseNodeInstID(MatConfigExtPropertySetting.NODENAME);
		standardMaterialUnitMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);



		// UI Model Configure of node:[MatConfigHeaderCondition]
		UIModelNodeMapConfigure materialConfigureTemplateConditionMap = new UIModelNodeMapConfigure();
		materialConfigureTemplateConditionMap
				.setSeName(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateConditionMap
				.setNodeName(MaterialConfigureTemplate.NODENAME);

		materialConfigureTemplateConditionMap.setBaseNodeInstID(MatConfigExtPropertySetting.NODENAME);
		materialConfigureTemplateConditionMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		materialConfigureTemplateConditionMap
				.setNodeInstID(MaterialConfigureTemplate.SENAME);
		Class<?>[] materialConfigureTemplateConditionConvToUIParas = {
				MaterialConfigureTemplate.class,
				MatConfigExtPropertySettingUIModel.class };
		materialConfigureTemplateConditionMap
				.setConvToUIMethodParas(materialConfigureTemplateConditionConvToUIParas);
		materialConfigureTemplateConditionMap.setLogicManager(matConfigExtPropertyManager);
		materialConfigureTemplateConditionMap
				.setConvToUIMethod(MatConfigExtPropertyManager.METHOD_ConvConfigureTemplateToExtPropertyUI);
		uiModelNodeMapList.add(materialConfigureTemplateConditionMap);

		matConfigExtPropertySettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(matConfigExtPropertySettingExtensionUnion);
		return resultList;
	}

}

package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MatConfigHeaderConditionManager;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class MatConfigHeaderConditionServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MatConfigHeaderConditionManager matConfigHeaderConditionManager;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion matConfigHeaderConditionExtensionUnion = new ServiceUIModelExtensionUnion();
		matConfigHeaderConditionExtensionUnion
				.setNodeInstId(MatConfigHeaderCondition.NODENAME);
		matConfigHeaderConditionExtensionUnion
				.setNodeName(MatConfigHeaderCondition.NODENAME);

		// UI Model Configure of node:[MatConfigHeaderCondition]
		UIModelNodeMapConfigure matConfigHeaderConditionMap = new UIModelNodeMapConfigure();
		matConfigHeaderConditionMap.setSeName(MatConfigHeaderCondition.SENAME);
		matConfigHeaderConditionMap
				.setNodeName(MatConfigHeaderCondition.NODENAME);
		matConfigHeaderConditionMap
				.setNodeInstID(MatConfigHeaderCondition.NODENAME);
		matConfigHeaderConditionMap.setHostNodeFlag(true);
		Class<?>[] matConfigHeaderConditionConvToUIParas = {
				MatConfigHeaderCondition.class,
				MatConfigHeaderConditionUIModel.class };
		matConfigHeaderConditionMap
				.setConvToUIMethodParas(matConfigHeaderConditionConvToUIParas);
		matConfigHeaderConditionMap
				.setConvToUIMethod(MatConfigHeaderConditionManager.METHOD_ConvMatConfigHeaderConditionToUI);
		matConfigHeaderConditionMap.setLogicManager(matConfigHeaderConditionManager);
		Class<?>[] MatConfigHeaderConditionConvUIToParas = {
				MatConfigHeaderConditionUIModel.class,
				MatConfigHeaderCondition.class };
		matConfigHeaderConditionMap
				.setConvUIToMethodParas(MatConfigHeaderConditionConvUIToParas);
		matConfigHeaderConditionMap
				.setConvUIToMethod(MatConfigHeaderConditionManager.METHOD_ConvUIToMatConfigHeaderCondition);
		uiModelNodeMapList.add(matConfigHeaderConditionMap);

		// UI Model Configure of node:[MatConfigHeaderCondition]
		UIModelNodeMapConfigure materialConfigureTemplateConditionMap = new UIModelNodeMapConfigure();
		materialConfigureTemplateConditionMap
				.setSeName(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateConditionMap
				.setNodeName(MaterialConfigureTemplate.NODENAME);
		materialConfigureTemplateConditionMap
				.setNodeInstID(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateConditionMap.setBaseNodeInstID(MatConfigHeaderCondition.NODENAME);
		materialConfigureTemplateConditionMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_TO_CHILD);
		Class<?>[] materialConfigureTemplateConditionConvToUIParas = {
				MaterialConfigureTemplate.class,
				MatConfigHeaderConditionUIModel.class };
		materialConfigureTemplateConditionMap
				.setConvToUIMethodParas(materialConfigureTemplateConditionConvToUIParas);
		materialConfigureTemplateConditionMap
				.setConvToUIMethod(MatConfigHeaderConditionManager.METHOD_ConvConfigureTemplateToHeaderConditionUI);
		materialConfigureTemplateConditionMap.setLogicManager(matConfigHeaderConditionManager);
		uiModelNodeMapList.add(materialConfigureTemplateConditionMap);

		matConfigHeaderConditionExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(matConfigHeaderConditionExtensionUnion);
		return resultList;
	}

}

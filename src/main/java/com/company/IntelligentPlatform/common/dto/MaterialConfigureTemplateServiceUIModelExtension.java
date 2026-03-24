package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.MaterialConfigureTemplateUIModel;
import com.company.IntelligentPlatform.common.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;

@Service
public class MaterialConfigureTemplateServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MatDecisionValueSettingServiceUIModelExtension matDecisionValueSettingServiceUIModelExtension;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MatConfigHeaderConditionServiceUIModelExtension matConfigHeaderConditionServiceUIModelExtension;

	@Autowired
	protected MatConfigExtPropertySettingServiceUIModelExtension matConfigExtPropertySettingServiceUIModelExtension;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(matDecisionValueSettingServiceUIModelExtension);
		resultList.add(matConfigHeaderConditionServiceUIModelExtension);
		resultList.add(matConfigExtPropertySettingServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion materialConfigureTemplateExtensionUnion = new ServiceUIModelExtensionUnion();
		materialConfigureTemplateExtensionUnion
				.setNodeInstId(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateExtensionUnion
				.setNodeName(MaterialConfigureTemplate.NODENAME);

		// UI Model Configure of node:[MaterialConfigureTemplate]
		UIModelNodeMapConfigure materialConfigureTemplateMap = new UIModelNodeMapConfigure();
		materialConfigureTemplateMap
				.setSeName(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateMap
				.setNodeName(MaterialConfigureTemplate.NODENAME);
		materialConfigureTemplateMap
				.setNodeInstID(MaterialConfigureTemplate.SENAME);
		materialConfigureTemplateMap.setHostNodeFlag(true);
		Class<?>[] materialConfigureTemplateConvToUIParas = {
				MaterialConfigureTemplate.class,
				MaterialConfigureTemplateUIModel.class };
		materialConfigureTemplateMap
				.setConvToUIMethodParas(materialConfigureTemplateConvToUIParas);
		materialConfigureTemplateMap
				.setConvToUIMethod(MaterialConfigureTemplateManager.METHOD_ConvMaterialConfigureTemplateToUI);
		Class<?>[] MaterialConfigureTemplateConvUIToParas = {
				MaterialConfigureTemplateUIModel.class,
				MaterialConfigureTemplate.class };
		materialConfigureTemplateMap
				.setConvUIToMethodParas(MaterialConfigureTemplateConvUIToParas);
		materialConfigureTemplateMap
				.setConvUIToMethod(MaterialConfigureTemplateManager.METHOD_ConvUIToMaterialConfigureTemplate);
		uiModelNodeMapList.add(materialConfigureTemplateMap);
		materialConfigureTemplateExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(materialConfigureTemplateExtensionUnion);
		return resultList;
	}

}

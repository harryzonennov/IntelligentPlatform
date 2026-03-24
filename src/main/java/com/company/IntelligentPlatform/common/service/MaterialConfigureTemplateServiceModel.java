package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class MaterialConfigureTemplateServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = MaterialConfigureTemplate.NODENAME, nodeInstId = MaterialConfigureTemplate.SENAME)
	protected MaterialConfigureTemplate materialConfigureTemplate;

	@IServiceModuleFieldConfig(nodeName = MatDecisionValueSetting.NODENAME, nodeInstId = MatDecisionValueSetting.NODENAME)
	protected List<MatDecisionValueSettingServiceModel> matDecisionValueSettingList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = MatConfigHeaderCondition.NODENAME, nodeInstId = MatConfigHeaderCondition.NODENAME)
	protected List<MatConfigHeaderConditionServiceModel> matConfigHeaderConditionList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = MatConfigExtPropertySetting.NODENAME, nodeInstId = MatConfigExtPropertySetting.NODENAME)
	protected List<MatConfigExtPropertySettingServiceModel> matConfigExtPropertySettingList = new ArrayList<>();

	public MaterialConfigureTemplate getMaterialConfigureTemplate() {
		return materialConfigureTemplate;
	}

	public void setMaterialConfigureTemplate(MaterialConfigureTemplate materialConfigureTemplate) {
		this.materialConfigureTemplate = materialConfigureTemplate;
	}

	public List<MatDecisionValueSettingServiceModel> getMatDecisionValueSettingList() {
		return matDecisionValueSettingList;
	}

	public void setMatDecisionValueSettingList(List<MatDecisionValueSettingServiceModel> matDecisionValueSettingList) {
		this.matDecisionValueSettingList = matDecisionValueSettingList;
	}

	public List<MatConfigHeaderConditionServiceModel> getMatConfigHeaderConditionList() {
		return matConfigHeaderConditionList;
	}

	public void setMatConfigHeaderConditionList(
			List<MatConfigHeaderConditionServiceModel> matConfigHeaderConditionList) {
		this.matConfigHeaderConditionList = matConfigHeaderConditionList;
	}

	public List<MatConfigExtPropertySettingServiceModel> getMatConfigExtPropertySettingList() {
		return matConfigExtPropertySettingList;
	}

	public void setMatConfigExtPropertySettingList(
			List<MatConfigExtPropertySettingServiceModel> matConfigExtPropertySettingList) {
		this.matConfigExtPropertySettingList = matConfigExtPropertySettingList;
	}
}

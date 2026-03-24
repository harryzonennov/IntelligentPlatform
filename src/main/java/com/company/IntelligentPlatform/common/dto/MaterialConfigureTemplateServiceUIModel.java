package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.service.MaterialConfigureTemplateManager;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;

@Component
public class MaterialConfigureTemplateServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = MaterialConfigureTemplate.NODENAME, nodeInstId = MaterialConfigureTemplate.SENAME)
	protected MaterialConfigureTemplateUIModel materialConfigureTemplateUIModel;

	@IServiceUIModuleFieldConfig(nodeName = MatDecisionValueSetting.NODENAME, nodeInstId = MatDecisionValueSetting.NODENAME)
	protected List<MatDecisionValueSettingServiceUIModel> matDecisionValueSettingUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = MatConfigHeaderCondition.NODENAME, nodeInstId = MatConfigHeaderCondition.NODENAME)
	protected List<MatConfigHeaderConditionServiceUIModel> matConfigHeaderConditionUIModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = MatConfigExtPropertySetting.NODENAME, nodeInstId = MatConfigExtPropertySetting.NODENAME)
	protected List<MatConfigExtPropertySettingServiceUIModel> matConfigExtPropertySettingUIModelList =
			new ArrayList<>();

	public MaterialConfigureTemplateUIModel getMaterialConfigureTemplateUIModel() {
		return this.materialConfigureTemplateUIModel;
	}

	public void setMaterialConfigureTemplateUIModel(MaterialConfigureTemplateUIModel materialConfigureTemplateUIModel) {
		this.materialConfigureTemplateUIModel = materialConfigureTemplateUIModel;
	}

	public List<MatDecisionValueSettingServiceUIModel> getMatDecisionValueSettingUIModelList() {
		return matDecisionValueSettingUIModelList;
	}

	public void setMatDecisionValueSettingUIModelList(
			List<MatDecisionValueSettingServiceUIModel> matDecisionValueSettingUIModelList) {
		this.matDecisionValueSettingUIModelList = matDecisionValueSettingUIModelList;
	}

	public List<MatConfigHeaderConditionServiceUIModel> getMatConfigHeaderConditionUIModelList() {
		return matConfigHeaderConditionUIModelList;
	}

	public void setMatConfigHeaderConditionUIModelList(
			List<MatConfigHeaderConditionServiceUIModel> matConfigHeaderConditionUIModelList) {
		this.matConfigHeaderConditionUIModelList = matConfigHeaderConditionUIModelList;
	}

	public List<MatConfigExtPropertySettingServiceUIModel> getMatConfigExtPropertySettingUIModelList() {
		return matConfigExtPropertySettingUIModelList;
	}

	public void setMatConfigExtPropertySettingUIModelList(
			List<MatConfigExtPropertySettingServiceUIModel> matConfigExtPropertySettingUIModelList) {
		this.matConfigExtPropertySettingUIModelList = matConfigExtPropertySettingUIModelList;
	}
}

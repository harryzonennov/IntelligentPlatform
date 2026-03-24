package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MatConfigExtPropertySettingUIModel extends SEUIComModel {

	@ISEDropDownResourceMapping(resouceMapping = "MatConfigExtPropertySetting_fieldType", valueFieldName = "null")
	protected String fieldType;
	
	protected int qualityInspectFlag;
	
	protected String qualityInspectValue;
	
	protected int measureFlag; 
	
	protected String measureFlagValue;
	
	protected String refUnitUUID;
	
	protected String refUnitValue;
	
	protected String templateId;
	
	protected String templateName;

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

	public String getQualityInspectValue() {
		return qualityInspectValue;
	}

	public void setQualityInspectValue(String qualityInspectValue) {
		this.qualityInspectValue = qualityInspectValue;
	}

	public int getMeasureFlag() {
		return measureFlag;
	}

	public void setMeasureFlag(int measureFlag) {
		this.measureFlag = measureFlag;
	}

	public String getMeasureFlagValue() {
		return measureFlagValue;
	}

	public void setMeasureFlagValue(String measureFlagValue) {
		this.measureFlagValue = measureFlagValue;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefUnitValue() {
		return refUnitValue;
	}

	public void setRefUnitValue(String refUnitValue) {
		this.refUnitValue = refUnitValue;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}

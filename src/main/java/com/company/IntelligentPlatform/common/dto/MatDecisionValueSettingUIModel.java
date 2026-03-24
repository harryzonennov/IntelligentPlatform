package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MatDecisionValueSettingUIModel extends SEUIComModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "MatDecisionValueSetting_valueUsage", valueFieldName = "valueUsageValue")
	protected int valueUsage;
	
	protected String valueUsageValue;
	
	protected String rawValue;
	
	protected String valueLabel;
	
	protected String valueId;
	
	protected String valueName;
	
	protected String templateId;
	
	protected String templateName;

	public int getValueUsage() {
		return this.valueUsage;
	}

	public String getValueUsageValue() {
		return valueUsageValue;
	}

	public void setValueUsageValue(String valueUsageValue) {
		this.valueUsageValue = valueUsageValue;
	}

	public void setValueUsage(int valueUsage) {
		this.valueUsage = valueUsage;
	}

	public String getRawValue() {
		return this.rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	public String getValueLabel() {
		return valueLabel;
	}

	public void setValueLabel(String valueLabel) {
		this.valueLabel = valueLabel;
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
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

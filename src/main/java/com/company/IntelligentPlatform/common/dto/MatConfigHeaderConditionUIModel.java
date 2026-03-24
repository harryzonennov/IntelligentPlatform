package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MatConfigHeaderConditionUIModel extends SEUIComModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "MatConfigHeaderCondition_fieldName", valueFieldName = "null")
	protected String fieldName;

	protected String refNodeInstId;
	
	protected String refNodeInstValue;

	protected String fieldValue;

	@ISEDropDownResourceMapping(resouceMapping = "MatConfigHeaderCondition_logicOperator", valueFieldName = "null")
	protected int logicOperator;
	
	protected String logicOperatorValue;
	
	protected String templateId;
	
	protected String templateName;
	
	protected String valueId;
	
	protected String valueName;

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRefNodeInstId() {
		return this.refNodeInstId;
	}

	public void setRefNodeInstId(String refNodeInstId) {
		this.refNodeInstId = refNodeInstId;
	}

	public String getRefNodeInstValue() {
		return refNodeInstValue;
	}

	public void setRefNodeInstValue(String refNodeInstValue) {
		this.refNodeInstValue = refNodeInstValue;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getLogicOperator() {
		return this.logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

	public String getLogicOperatorValue() {
		return logicOperatorValue;
	}

	public void setLogicOperatorValue(String logicOperatorValue) {
		this.logicOperatorValue = logicOperatorValue;
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

}

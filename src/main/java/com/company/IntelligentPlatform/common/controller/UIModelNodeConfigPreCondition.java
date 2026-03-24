package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;

public class UIModelNodeConfigPreCondition {

	protected String fieldName;

	protected Object fieldValue;
	
	protected String valueConstantName;

	protected int operator = SEFieldSearchConfig.OPERATOR_EQUAL;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public String getValueConstantName() {
		return valueConstantName;
	}

	public void setValueConstantName(String valueConstantName) {
		this.valueConstantName = valueConstantName;
	}

}

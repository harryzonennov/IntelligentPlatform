package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;

import java.util.List;

public class SearchConfigPreCondition {

	protected String fieldName;

	protected Object fieldValue;
	
	protected String nodeInstId;

	protected List<?> multipleValueList;

	protected int operator = SEFieldSearchConfig.OPERATOR_EQUAL;

	protected int logicCondOperator = SEFieldSearchConfig.LOGIC_OPERATOR_AND;

	public SearchConfigPreCondition() {
		this.operator = SEFieldSearchConfig.OPERATOR_EQUAL;
		this.logicCondOperator = SEFieldSearchConfig.LOGIC_OPERATOR_AND;
	}

	public SearchConfigPreCondition(Object fieldValue, String fieldName,
			String nodeInstId) {
		super();
		this.fieldValue = fieldValue;
		this.fieldName = fieldName;
		this.nodeInstId = nodeInstId;
	}

	public SearchConfigPreCondition(String fieldName, Object fieldValue,
			String nodeInstId, int operator, int logicCondOperator) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.nodeInstId = nodeInstId;
		this.operator = operator;
		this.logicCondOperator = logicCondOperator;
	}

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

	public int getLogicCondOperator() {
		return logicCondOperator;
	}

	public void setLogicCondOperator(int logicCondOperator) {
		this.logicCondOperator = logicCondOperator;
	}

	public String getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}

	public List<?> getMultipleValueList() {
		return multipleValueList;
	}

	public void setMultipleValueList(List<?> multipleValueList) {
		this.multipleValueList = multipleValueList;
	}
}

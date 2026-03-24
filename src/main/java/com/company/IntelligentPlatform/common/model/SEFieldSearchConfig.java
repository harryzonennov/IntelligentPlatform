package com.company.IntelligentPlatform.common.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SEFieldSearchConfig {

	/**
	 * Search operator: equal to [low value]
	 */
	public static final int OPERATOR_EQUAL = 1;

	/**
	 * Search operator: between [low value] and [high value]
	 */
	public static final int OPERATOR_BETWEEN = 2;

	/**
	 * Search operator: not between [low value] and [high value]
	 */
	public static final int OPERATOR_NOT_BETWEEN = 3;

	/**
	 * Search operator: greater than or equal to [low value]
	 */
	public static final int OPERATOR_GREATER_EQ = 4;

	/**
	 * Search operator: greater than [low value]
	 */
	public static final int OPERATOR_GREATER = 5;

	/**
	 * Search operator: less than or equal to [low value]
	 */
	public static final int OPERATOR_LESS_EQ = 6;

	/**
	 * Search operator: less than [low value]
	 */
	public static final int OPERATOR_LESS = 7;

	/**
	 * Search operator: not equal to [low value]
	 */
	public static final int OPERATOR_NOT_EQ = 8;
	/**
	 * Search operator: currently not use in search
	 */
	public static final int OPERATOR_CONTAINS = 9;
	
	public static final int LOGIC_OPERATOR_AND = 1;
	
	public static final int LOGIC_OPERATOR_OR = 2;

	protected String fieldName;

	protected Field lowValueField;

	protected Field highValueField;

	protected Object lowValue;

	protected Object highValue;

	protected int operator;

	protected String nodeName;

	protected String seName;
	
	/**
	 * To store the multi-possible value list
	 */
	protected List<?> valueList = new ArrayList<>();

	public SEFieldSearchConfig() {
		this.operator = OPERATOR_EQUAL;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Field getLowValueField() {
		return lowValueField;
	}

	public void setLowValueField(Field lowValueField) {
		this.lowValueField = lowValueField;
	}

	public Field getHighValueField() {
		return highValueField;
	}

	public void setHighValueField(Field highValueField) {
		this.highValueField = highValueField;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public Object getLowValue() {
		return lowValue;
	}

	public void setLowValue(Object lowValue) {
		this.lowValue = lowValue;
	}

	public Object getHighValue() {
		return highValue;
	}

	public void setHighValue(Object highValue) {
		this.highValue = highValue;
	}

	public List<?> getValueList() {
		return valueList;
	}

	public void setValueList(List<?> valueList) {
		this.valueList = valueList;
	}

}

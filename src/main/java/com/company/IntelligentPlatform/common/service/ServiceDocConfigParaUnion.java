package com.company.IntelligentPlatform.common.service;

import java.util.Map;

/**
 * Service document configure parameter Union
 * @author Zhang,hang
 *
 */
public class ServiceDocConfigParaUnion {
	
	public static final int VALUEMODE_SINGLEVALUE = 1;
	
	public static final int VALUEMODE_MULTIVALUE = 2;
	
	public static final int VALUEMODE_DROPDOWN = 3;
	
	public static final int VALUEMODE_BETWEEN = 4;

	public static final int DIRECT_INPUT = 1;
	
	public static final int DIRECT_OUTPUT = 2;
	
	public static final int OUTAMOUNT_MODE_FULL = 1;
	
	public static final int OUTAMOUNT_MODE_AVE_DAY = 2;
	
	public static final int OUTAMOUNT_MODE_AVE_WEEK = 3;
	
	public static final int OUTAMOUNT_MODE_AVE_MONTH = 4;
	
	protected String label;
	
	protected String fieldName;
	
	// Field label is traversed from node
	protected String fieldLabel;
	
	protected Object highValue;
	
	protected Object fieldValue;
	
	protected Class<?> fieldTypeClass;
	
	protected int valueMode;
	
	protected int paraDirection;
	
	protected boolean editable;
	
	protected boolean mandatoryFlag;
	
	protected Map<?, ?> dropdownMap;	
	
	protected Map<String, String> dataProviderMap;
	
	protected Map<Integer, String> consumerValueModeMap;
	
	public ServiceDocConfigParaUnion(){
		this.paraDirection = DIRECT_INPUT;
		this.valueMode = VALUEMODE_SINGLEVALUE;
		this.editable = false;	
		this.mandatoryFlag = false;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public int getValueMode() {
		return valueMode;
	}

	public void setValueMode(int valueMode) {
		this.valueMode = valueMode;
	}

	public int getParaDirection() {
		return paraDirection;
	}

	public void setParaDirection(int paraDirection) {
		this.paraDirection = paraDirection;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}


	public Class<?> getFieldTypeClass() {
		return fieldTypeClass;
	}

	public void setFieldTypeClass(Class<?> fieldTypeClass) {
		this.fieldTypeClass = fieldTypeClass;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Object getHighValue() {
		return highValue;
	}

	public void setHighValue(Object highValue) {
		this.highValue = highValue;
	}

	public Map<?, ?> getDropdownMap() {
		return dropdownMap;
	}

	public void setDropdownMap(Map<?, ?> dropdownMap) {
		this.dropdownMap = dropdownMap;
	}

	public Map<String, String> getDataProviderMap() {
		return dataProviderMap;
	}

	public void setDataProviderMap(Map<String, String> dataProviderMap) {
		this.dataProviderMap = dataProviderMap;
	}

	public Map<Integer, String> getConsumerValueModeMap() {
		return consumerValueModeMap;
	}

	public void setConsumerValueModeMap(Map<Integer, String> consumerValueModeMap) {
		this.consumerValueModeMap = consumerValueModeMap;
	}

	public boolean isMandatoryFlag() {
		return mandatoryFlag;
	}

	public void setMandatoryFlag(boolean mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}
	
}

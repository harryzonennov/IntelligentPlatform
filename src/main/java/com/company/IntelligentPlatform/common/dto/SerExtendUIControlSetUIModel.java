package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SerExtendUIControlSetUIModel extends SEUIComModel{	
	
	protected String fieldName;
	
	protected String fieldType;
	
	protected String fieldTypeValue;
	
	protected String refNodeInstId;
	
	protected String refFieldUUID;
	
	protected String refSEName;
	
	protected String refNodeName;
	
    protected String sectionId;
	
    protected String screenId;
    
    protected int displayIndex;
    
    protected String inputControlType;
    
    protected String inputControlTypeValue;
    
    protected String controlDomId;
	
	protected String storeModelName;
	
	protected String storeValue;
    
    protected String getMetaDataUrl;
    
    protected String refMetaCodeUUID;
    
    protected String refMetaCodeId;
    
    protected String refMetaCodeName;
    
    protected int visibleSwitch;
    
    protected String visibleSwitchValue;
    
    protected String visibleExpression;
    
    protected String visibleActionCode;
    
    protected int enableSwitch;
    
    protected String enableSwitchValue;
    
    protected String enableExpression;
    
    protected String enableActionCode;
    
    protected String formatSelectMethod;
    
    protected String defaultValue;
    
    protected String defaultValueExpression;
    
    protected int systemCategory;
    
    protected String systemCategoryValue;
	
	protected int customI18nSwitch;
	
	protected int rowNumber;
    
    protected String customI18nSwitchValue;
    
    protected String lanKey;
    
    protected String labelValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTypeValue() {
		return fieldTypeValue;
	}

	public void setFieldTypeValue(String fieldTypeValue) {
		this.fieldTypeValue = fieldTypeValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getRefFieldUUID() {
		return refFieldUUID;
	}

	public void setRefFieldUUID(String refFieldUUID) {
		this.refFieldUUID = refFieldUUID;
	}

	public String getRefNodeInstId() {
		return refNodeInstId;
	}

	public void setRefNodeInstId(String refNodeInstId) {
		this.refNodeInstId = refNodeInstId;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	public String getInputControlType() {
		return inputControlType;
	}

	public void setInputControlType(String inputControlType) {
		this.inputControlType = inputControlType;
	}

	public String getInputControlTypeValue() {
		return inputControlTypeValue;
	}

	public void setInputControlTypeValue(String inputControlTypeValue) {
		this.inputControlTypeValue = inputControlTypeValue;
	}

	public String getGetMetaDataUrl() {
		return getMetaDataUrl;
	}

	public void setGetMetaDataUrl(String getMetaDataUrl) {
		this.getMetaDataUrl = getMetaDataUrl;
	}	

	public String getRefMetaCodeUUID() {
		return refMetaCodeUUID;
	}

	public void setRefMetaCodeUUID(String refMetaCodeUUID) {
		this.refMetaCodeUUID = refMetaCodeUUID;
	}

	public String getRefMetaCodeId() {
		return refMetaCodeId;
	}

	public void setRefMetaCodeId(String refMetaCodeId) {
		this.refMetaCodeId = refMetaCodeId;
	}

	public String getRefMetaCodeName() {
		return refMetaCodeName;
	}

	public void setRefMetaCodeName(String refMetaCodeName) {
		this.refMetaCodeName = refMetaCodeName;
	}

	public String getFormatSelectMethod() {
		return formatSelectMethod;
	}

	public void setFormatSelectMethod(String formatSelectMethod) {
		this.formatSelectMethod = formatSelectMethod;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public String getControlDomId() {
		return controlDomId;
	}

	public void setControlDomId(String controlDomId) {
		this.controlDomId = controlDomId;
	}

	public String getStoreModelName() {
		return storeModelName;
	}

	public void setStoreModelName(String storeModelName) {
		this.storeModelName = storeModelName;
	}

	public String getStoreValue() {
		return storeValue;
	}

	public void setStoreValue(String storeValue) {
		this.storeValue = storeValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultValueExpression() {
		return defaultValueExpression;
	}

	public void setDefaultValueExpression(String defaultValueExpression) {
		this.defaultValueExpression = defaultValueExpression;
	}

	public int getVisibleSwitch() {
		return visibleSwitch;
	}

	public void setVisibleSwitch(int visibleSwitch) {
		this.visibleSwitch = visibleSwitch;
	}

	public String getVisibleSwitchValue() {
		return visibleSwitchValue;
	}

	public void setVisibleSwitchValue(String visibleSwitchValue) {
		this.visibleSwitchValue = visibleSwitchValue;
	}

	public String getVisibleExpression() {
		return visibleExpression;
	}

	public void setVisibleExpression(String visibleExpression) {
		this.visibleExpression = visibleExpression;
	}

	public String getVisibleActionCode() {
		return visibleActionCode;
	}

	public void setVisibleActionCode(String visibleActionCode) {
		this.visibleActionCode = visibleActionCode;
	}

	public int getEnableSwitch() {
		return enableSwitch;
	}

	public void setEnableSwitch(int enableSwitch) {
		this.enableSwitch = enableSwitch;
	}

	public String getEnableSwitchValue() {
		return enableSwitchValue;
	}

	public void setEnableSwitchValue(String enableSwitchValue) {
		this.enableSwitchValue = enableSwitchValue;
	}

	public String getEnableExpression() {
		return enableExpression;
	}

	public void setEnableExpression(String enableExpression) {
		this.enableExpression = enableExpression;
	}

	public String getEnableActionCode() {
		return enableActionCode;
	}

	public void setEnableActionCode(String enableActionCode) {
		this.enableActionCode = enableActionCode;
	}

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
	}

	public String getSystemCategoryValue() {
		return systemCategoryValue;
	}

	public void setSystemCategoryValue(String systemCategoryValue) {
		this.systemCategoryValue = systemCategoryValue;
	}

	public int getCustomI18nSwitch() {
		return customI18nSwitch;
	}

	public void setCustomI18nSwitch(int customI18nSwitch) {
		this.customI18nSwitch = customI18nSwitch;
	}

	public String getCustomI18nSwitchValue() {
		return customI18nSwitchValue;
	}

	public void setCustomI18nSwitchValue(String customI18nSwitchValue) {
		this.customI18nSwitchValue = customI18nSwitchValue;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getLanKey() {
		return lanKey;
	}

	public void setLanKey(String lanKey) {
		this.lanKey = lanKey;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	
}

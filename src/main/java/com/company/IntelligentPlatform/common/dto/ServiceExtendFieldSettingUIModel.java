package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceExtendFieldSettingUIModel extends SEUIComModel {

	protected boolean searchFlag;

	@ISEDropDownResourceMapping(resouceMapping = "ServiceExtendField_fieldType", valueFieldName = "fieldTypeValue")
	protected String fieldType;
	
	protected String fieldTypeValue;

	protected String parentNodeId;

	protected String storeModelName;

	protected String fieldName;

	protected String fieldLabel;

	protected int fieldMaxLength;

	protected boolean hideInEditor;

	protected boolean hideInList;

	protected boolean hideInSearchPanel;

	protected int systemCategory;

	protected int customI18nSwitch;

	protected String customI18nSwitchValue;
	
	protected String inputControlType;
	
	protected String inputControlTypeValue;
	
	protected int activeSwitch;

	protected String activeSwitchValue;

	protected String systemCategoryValue;

	/**
	 * Indicate weather this field is extended field or standard field.
	 */
	protected boolean extendedFieldFlag;

	protected boolean customI18nFlag;

	protected String initialValue;

	protected String initialPrevModelName;
	
	protected int usedFlag;
	
	protected String getMetaDataUrl;
    
    protected String refMetaCodeUUID;
    
    protected int visibleSwitch;
    
    protected String visibleSwitchValue;
    
    protected String visibleExpression;
    
    protected String visibleActionCode;
    
    protected int enableSwitch;
    
    protected String enableSwitchValue;
    
    protected String enableExpression;
    
    protected String enableActionCode;
    
    protected String defaultValue;
    
    protected String defaultValueExpression;
    
    protected String formatSelectMethod;
    
    protected String labelValue;

	public boolean getSearchFlag() {
		return this.searchFlag;
	}

	public void setSearchFlag(boolean searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public String getFieldTypeValue() {
		return fieldTypeValue;
	}

	public void setFieldTypeValue(String fieldTypeValue) {
		this.fieldTypeValue = fieldTypeValue;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStoreModelName() {
		return this.storeModelName;
	}

	public void setStoreModelName(String storeModelName) {
		this.storeModelName = storeModelName;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldMaxLength() {
		return fieldMaxLength;
	}

	public void setFieldMaxLength(int fieldMaxLength) {
		this.fieldMaxLength = fieldMaxLength;
	}

	public boolean getHideInEditor() {
		return hideInEditor;
	}

	public void setHideInEditor(boolean hideInEditor) {
		this.hideInEditor = hideInEditor;
	}

	public boolean getHideInList() {
		return hideInList;
	}

	public void setHideInList(boolean hideInList) {
		this.hideInList = hideInList;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public boolean getHideInSearchPanel() {
		return hideInSearchPanel;
	}

	public void setHideInSearchPanel(boolean hideInSearchPanel) {
		this.hideInSearchPanel = hideInSearchPanel;
	}

	public boolean getExtendedFieldFlag() {
		return extendedFieldFlag;
	}

	public void setExtendedFieldFlag(boolean extendedFieldFlag) {
		this.extendedFieldFlag = extendedFieldFlag;
	}

	public boolean getCustomI18nFlag() {
		return customI18nFlag;
	}

	public void setCustomI18nFlag(boolean customI18nFlag) {
		this.customI18nFlag = customI18nFlag;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public String getInitialPrevModelName() {
		return initialPrevModelName;
	}

	public void setInitialPrevModelName(String initialPrevModelName) {
		this.initialPrevModelName = initialPrevModelName;
	}
	
	public int getCustomI18nSwitch() {
		return customI18nSwitch;
	}

	public void setCustomI18nSwitch(int customI18nSwitch) {
		this.customI18nSwitch = customI18nSwitch;
	}

	public int getActiveSwitch() {
		return activeSwitch;
	}

	public void setActiveSwitch(int activeSwitch) {
		this.activeSwitch = activeSwitch;
	}

	public String getActiveSwitchValue() {
		return activeSwitchValue;
	}

	public void setActiveSwitchValue(String activeSwitchValue) {
		this.activeSwitchValue = activeSwitchValue;
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

	public String getCustomI18nSwitchValue() {
		return customI18nSwitchValue;
	}

	public void setCustomI18nSwitchValue(String customI18nSwitchValue) {
		this.customI18nSwitchValue = customI18nSwitchValue;
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

	public int getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(int usedFlag) {
		this.usedFlag = usedFlag;
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

	public String getFormatSelectMethod() {
		return formatSelectMethod;
	}

	public void setFormatSelectMethod(String formatSelectMethod) {
		this.formatSelectMethod = formatSelectMethod;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

}

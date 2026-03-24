package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceExtendFieldSetting extends ServiceEntityNode{
	
	public static final String NODENAME = IServiceModelConstants.ServiceExtendFieldSetting;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;

	public static final String FIELDTYPE_INT = int.class.getSimpleName();
	
	public static final String FIELDTYPE_DOUBLE = double.class.getSimpleName();
	
	public static final String FIELDTYPE_STRING = String.class.getSimpleName();
	
	public static final String FIELDTYPE_DATE = Date.class.getSimpleName();
	
	public ServiceExtendFieldSetting() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.fieldMaxLength = 100;
		this.systemCategory = StandardSystemCategoryProxy.CATE_SYSTEM_STANDARD;
		this.customI18nSwitch = StandardSwitchProxy.SWITCH_OFF;
		this.activeSwitch = StandardSwitchProxy.SWITCH_ON;
		this.inputControlType = SerExtendUIControlSet.CONTRTYPE_INPUT;
	}
	
	protected String fieldName;
	
	protected String fieldType;

	protected String fieldLabel;
	
	protected int fieldMaxLength;
	
	protected String storeModelName;

	protected boolean searchFlag;
	
	protected boolean hideInEditor;
	
	protected boolean hideInList;
	
	protected boolean hideInSearchPanel;
	
	/**
	 * Indicate weather this field is extended field or standard field.
	 */
	protected boolean extendedFieldFlag;
	
	protected boolean customI18nFlag;
	
	/**
	 * Indicate weather this field is extended field or standard field.
	 */
	protected int systemCategory;
	
	protected int customI18nSwitch;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_300)
	protected String initialValue;
	
	protected String initialPrevModelName;
	
	protected int activeSwitch;
	
	protected String inputControlType;
	 
    protected String getMetaDataUrl;
    
    protected String refMetaCodeUUID;
        
    protected int visibleSwitch;
    
    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String visibleExpression;
    
    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String visibleActionCode;
    
    protected int enableSwitch;
    
    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String enableExpression;
    
    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String enableActionCode;
    
    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
    protected String defaultValue;
    
    @ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
    protected String defaultValueExpression;
    
    protected String formatSelectMethod;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getFieldMaxLength() {
		return fieldMaxLength;
	}

	public void setFieldMaxLength(int fieldMaxLength) {
		this.fieldMaxLength = fieldMaxLength;
	}

	public String getStoreModelName() {
		return storeModelName;
	}

	public void setStoreModelName(String storeModelName) {
		this.storeModelName = storeModelName;
	}

	public boolean getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(boolean searchFlag) {
		this.searchFlag = searchFlag;
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

	public int getSystemCategory() {
		return systemCategory;
	}

	public void setSystemCategory(int systemCategory) {
		this.systemCategory = systemCategory;
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

	public String getInputControlType() {
		return inputControlType;
	}

	public void setInputControlType(String inputControlType) {
		this.inputControlType = inputControlType;
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

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
}

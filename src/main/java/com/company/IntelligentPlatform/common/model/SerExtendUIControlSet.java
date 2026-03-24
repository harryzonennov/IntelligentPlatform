package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SerExtendUIControlSet extends ServiceEntityNode{	

	public static final String NODENAME = IServiceModelConstants.SerExtendUIControlSet;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;
	
	public static final String CONTRTYPE_INPUT = "input-element";
	
	public static final String CONTRTYPE_TEXTAREA = "textarea-element";
	
	public static final String CONTRTYPE_SELECT2 = "select2-element";
	
	public static final String FIELD_REFFEILDUUID = "refFieldUUID";

	public SerExtendUIControlSet() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_200)
    protected String sectionId;
    
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_200)
    protected String screenId;
    
    protected int displayIndex;
    
    protected String inputControlType;
    
    protected String controlDomId;
    
    protected String getMetaDataUrl;
    
    protected String refMetaCodeUUID;
    
    protected String refFieldUUID;
    
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
    
    protected int rowNumber;    

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
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

	public String getControlDomId() {
		return controlDomId;
	}

	public void setControlDomId(String controlDomId) {
		this.controlDomId = controlDomId;
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

	public String getFormatSelectMethod() {
		return formatSelectMethod;
	}

	public void setFormatSelectMethod(String formatSelectMethod) {
		this.formatSelectMethod = formatSelectMethod;
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

	public String getRefFieldUUID() {
		return refFieldUUID;
	}

	public void setRefFieldUUID(String refFieldUUID) {
		this.refFieldUUID = refFieldUUID;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
}

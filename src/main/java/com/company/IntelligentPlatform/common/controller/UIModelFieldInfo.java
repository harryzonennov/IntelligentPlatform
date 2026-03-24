package com.company.IntelligentPlatform.common.controller;

public class UIModelFieldInfo {

	protected String uiFieldName;

	protected Class<?> uiFieldType;

	protected String seFieldName;

	protected Class<?> seFieldType;
	
	protected String seSetMethodName;
	
	protected String seGetMethodName;
	
    protected String uiSetMethodName;
	
	protected String uiGetMethodName;
	
	protected String labelFieldName;
	
	protected String resouceMapping;
	
	protected String valueFieldName;
	
	protected boolean showOnList = true;
	
	protected boolean showOnEditor = true;
	
	protected boolean textAreaFlag;
	
	protected boolean hiddenFlag;
	
	protected boolean uiReadOnlyFlag;
	
	protected boolean searchFlag = true;
	
	protected String dateStringField;
	
	protected String tabId;
	
	protected String nodeInstIDConstant;
	
	protected String secId;
	
	protected String showFieldID;
	
	protected String uiModelClassName;
	
	protected int fieldCategory;
	
	protected String sourceFieldName;
	
	protected String refNodeInstId;
	
	protected int inputType;
	
    protected String refSelectModel;
	
	protected String refSelectMethod;
	
	protected int refSelectType;
	
	protected String refSelectURL;
	
	protected String client;

	public String getUiFieldName() {
		return uiFieldName;
	}

	public void setUiFieldName(String uiFieldName) {
		this.uiFieldName = uiFieldName;
	}

	public Class<?> getUiFieldType() {
		return uiFieldType;
	}

	public void setUiFieldType(Class<?> uiFieldType) {
		this.uiFieldType = uiFieldType;
	}

	public Class<?> getSeFieldType() {
		return seFieldType;
	}

	public void setSeFieldType(Class<?> seFieldType) {
		this.seFieldType = seFieldType;
	}

	public String getSeFieldName() {
		return seFieldName;
	}

	public void setSeFieldName(String seFieldName) {
		this.seFieldName = seFieldName;
	}

	public String getSeSetMethodName() {
		return seSetMethodName;
	}

	public void setSeSetMethodName(String seSetMethodName) {
		this.seSetMethodName = seSetMethodName;
	}

	public String getSeGetMethodName() {
		return seGetMethodName;
	}

	public void setSeGetMethodName(String seGetMethodName) {
		this.seGetMethodName = seGetMethodName;
	}

	public String getUiSetMethodName() {
		return uiSetMethodName;
	}

	public void setUiSetMethodName(String uiSetMethodName) {
		this.uiSetMethodName = uiSetMethodName;
	}

	public String getUiGetMethodName() {
		return uiGetMethodName;
	}

	public void setUiGetMethodName(String uiGetMethodName) {
		this.uiGetMethodName = uiGetMethodName;
	}

	public String getLabelFieldName() {
		return labelFieldName;
	}

	public void setLabelFieldName(String labelFieldName) {
		this.labelFieldName = labelFieldName;
	}

	public String getResouceMapping() {
		return resouceMapping;
	}

	public void setResouceMapping(String resouceMapping) {
		this.resouceMapping = resouceMapping;
	}

	public String getValueFieldName() {
		return valueFieldName;
	}

	public void setValueFieldName(String valueFieldName) {
		this.valueFieldName = valueFieldName;
	}

	public boolean isShowOnList() {
		return showOnList;
	}

	public void setShowOnList(boolean showOnList) {
		this.showOnList = showOnList;
	}

	public boolean isShowOnEditor() {
		return showOnEditor;
	}

	public void setShowOnEditor(boolean showOnEditor) {
		this.showOnEditor = showOnEditor;
	}

	public boolean isTextAreaFlag() {
		return textAreaFlag;
	}

	public void setTextAreaFlag(boolean textAreaFlag) {
		this.textAreaFlag = textAreaFlag;
	}

	public boolean isHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(boolean hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}

	public String getDateStringField() {
		return dateStringField;
	}

	public void setDateStringField(String dateStringField) {
		this.dateStringField = dateStringField;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getNodeInstIDConstant() {
		return nodeInstIDConstant;
	}

	public void setNodeInstIDConstant(String nodeInstIDConstant) {
		this.nodeInstIDConstant = nodeInstIDConstant;
	}

	public boolean getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(boolean searchFlag) {
		this.searchFlag = searchFlag;
	}

	public boolean isUiReadOnlyFlag() {
		return uiReadOnlyFlag;
	}

	public void setUiReadOnlyFlag(boolean uiReadOnlyFlag) {
		this.uiReadOnlyFlag = uiReadOnlyFlag;
	}

	public String getShowFieldID() {
		return showFieldID;
	}

	public void setShowFieldID(String showFieldID) {
		this.showFieldID = showFieldID;
	}

	public String getUiModelClassName() {
		return uiModelClassName;
	}

	public void setUiModelClassName(String uiModelClassName) {
		this.uiModelClassName = uiModelClassName;
	}

	public int getFieldCategory() {
		return fieldCategory;
	}

	public void setFieldCategory(int fieldCategory) {
		this.fieldCategory = fieldCategory;
	}

	public String getSourceFieldName() {
		return sourceFieldName;
	}

	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}

	public String getRefNodeInstId() {
		return refNodeInstId;
	}

	public void setRefNodeInstId(String refNodeInstId) {
		this.refNodeInstId = refNodeInstId;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public String getRefSelectModel() {
		return refSelectModel;
	}

	public void setRefSelectModel(String refSelectModel) {
		this.refSelectModel = refSelectModel;
	}

	public String getRefSelectMethod() {
		return refSelectMethod;
	}

	public void setRefSelectMethod(String refSelectMethod) {
		this.refSelectMethod = refSelectMethod;
	}

	public int getRefSelectType() {
		return refSelectType;
	}

	public void setRefSelectType(int refSelectType) {
		this.refSelectType = refSelectType;
	}

	public String getRefSelectURL() {
		return refSelectURL;
	}

	public void setRefSelectURL(String refSelectURL) {
		this.refSelectURL = refSelectURL;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}	

}

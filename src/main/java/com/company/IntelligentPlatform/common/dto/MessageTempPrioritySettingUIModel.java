package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MessageTempPrioritySettingUIModel extends SEUIComModel {

	protected String iconStyle;
	
	protected double endValue;
	
	protected String parentNodeId;
	
	protected String refPrioritySettingUUID;

	protected String colorStyle;
	
	protected double startValue;

	protected String actionCode;
	
	protected int priorityCode;

	protected String priorityCodeValue;

	protected int messageLevelCode;

	protected String messageLevelCodeValue;
	
	protected int collectionCategory;

	protected String dataSourceProviderId;

	protected int dataOffsetDirection;

	protected double dataOffsetValue;

	protected String dataOffsetUnit;

	protected String fieldName;

	protected String fieldValue;

	protected int switchFlag;

	protected String messageTitle;

	protected String messageContent;

	public String getIconStyle() {
		return this.iconStyle;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}

	public double getEndValue() {
		return this.endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getRefPrioritySettingUUID() {
		return this.refPrioritySettingUUID;
	}

	public void setRefPrioritySettingUUID(String refPrioritySettingUUID) {
		this.refPrioritySettingUUID = refPrioritySettingUUID;
	}

	public String getColorStyle() {
		return this.colorStyle;
	}

	public void setColorStyle(String colorStyle) {
		this.colorStyle = colorStyle;
	}

	public double getStartValue() {
		return this.startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public int getPriorityCode() {
		return this.priorityCode;
	}

	public int getMessageLevelCode() {
		return messageLevelCode;
	}

	public void setMessageLevelCode(int messageLevelCode) {
		this.messageLevelCode = messageLevelCode;
	}

	public String getMessageLevelCodeValue() {
		return messageLevelCodeValue;
	}

	public void setMessageLevelCodeValue(String messageLevelCodeValue) {
		this.messageLevelCodeValue = messageLevelCodeValue;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getPriorityCodeValue() {
		return priorityCodeValue;
	}

	public void setPriorityCodeValue(String priorityCodeValue) {
		this.priorityCodeValue = priorityCodeValue;
	}

	public int getCollectionCategory() {
		return this.collectionCategory;
	}

	public void setCollectionCategory(int collectionCategory) {
		this.collectionCategory = collectionCategory;
	}

	public String getDataSourceProviderId() {
		return dataSourceProviderId;
	}

	public void setDataSourceProviderId(String dataSourceProviderId) {
		this.dataSourceProviderId = dataSourceProviderId;
	}

	public int getDataOffsetDirection() {
		return dataOffsetDirection;
	}

	public void setDataOffsetDirection(int dataOffsetDirection) {
		this.dataOffsetDirection = dataOffsetDirection;
	}

	public double getDataOffsetValue() {
		return dataOffsetValue;
	}

	public void setDataOffsetValue(double dataOffsetValue) {
		this.dataOffsetValue = dataOffsetValue;
	}

	public String getDataOffsetUnit() {
		return dataOffsetUnit;
	}

	public void setDataOffsetUnit(String dataOffsetUnit) {
		this.dataOffsetUnit = dataOffsetUnit;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
}

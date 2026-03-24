package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class MessageTempPrioritySetting extends ReferenceNode {
	
	public static final String NODENAME = IServiceModelConstants.MessageTempPrioritySetting;

	public static final String SENAME = IServiceModelConstants.MessageTemplate;
	
	public MessageTempPrioritySetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
	}
	
	protected int priorityCode;

	protected int messageLevelCode;
	
	protected double startValue;
	
	protected double endValue;
	
	protected String colorStyle;
	
	protected String iconStyle;

	protected String actionCode;
	
	protected String refPrioritySettingUUID;

	protected String dataSourceProviderId;

	protected int dataOffsetDirection;

	protected double dataOffsetValue;

	protected String dataOffsetUnit;

	protected String fieldName;

	protected String fieldValue;

	protected int switchFlag;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_200)
	protected String messageTitle;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_2000)
	protected String messageContent;

	public int getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public int getMessageLevelCode() {
		return messageLevelCode;
	}

	public void setMessageLevelCode(int messageLevelCode) {
		this.messageLevelCode = messageLevelCode;
	}

	public double getStartValue() {
		return startValue;
	}

	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public String getColorStyle() {
		return colorStyle;
	}

	public void setColorStyle(String colorStyle) {
		this.colorStyle = colorStyle;
	}

	public String getIconStyle() {
		return iconStyle;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}

	public String getRefPrioritySettingUUID() {
		return refPrioritySettingUUID;
	}

	public void setRefPrioritySettingUUID(String refPrioritySettingUUID) {
		this.refPrioritySettingUUID = refPrioritySettingUUID;
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

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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

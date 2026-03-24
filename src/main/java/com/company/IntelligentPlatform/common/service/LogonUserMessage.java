package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.StandardErrorTypeProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class LogonUserMessage extends ServiceEntityNode{
	
	public static int MESSAGE_TYPE_NOTIFICATION = StandardErrorTypeProxy.MESSAGE_TYPE_NOTIFICATION;
	
	public static int MESSAGE_TYPE_WARNING = StandardErrorTypeProxy.MESSAGE_TYPE_WARNING;
	
	public static int MESSAGE_TYPE_ERROR = StandardErrorTypeProxy.MESSAGE_TYPE_ERROR;
	
	protected int category;
	
	protected String targetUUID;
	
	protected String targetURL;
	
	protected String targetID;
	
	protected String targetName;
	
	protected String targetNote;
	
	protected int targetStatus;
	
	protected String targetStatusValue;
	
	protected int messageType;
	
	protected String messageTypeValue;
	
	protected String warnNote;
	
	protected String errorNote;	

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getTargetURL() {
		return targetURL;
	}

	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}

	public String getTargetID() {
		return targetID;
	}

	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetNote() {
		return targetNote;
	}

	public void setTargetNote(String targetNote) {
		this.targetNote = targetNote;
	}

	public int getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(int targetStatus) {
		this.targetStatus = targetStatus;
	}

	public String getTargetStatusValue() {
		return targetStatusValue;
	}

	public void setTargetStatusValue(String targetStatusValue) {
		this.targetStatusValue = targetStatusValue;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getMessageTypeValue() {
		return messageTypeValue;
	}

	public void setMessageTypeValue(String messageTypeValue) {
		this.messageTypeValue = messageTypeValue;
	}

	public String getWarnNote() {
		return warnNote;
	}

	public void setWarnNote(String warnNote) {
		this.warnNote = warnNote;
	}

	public String getErrorNote() {
		return errorNote;
	}

	public void setErrorNote(String errorNote) {
		this.errorNote = errorNote;
	}

	public String getTargetUUID() {
		return targetUUID;
	}

	public void setTargetUUID(String targetUUID) {
		this.targetUUID = targetUUID;
	}

}

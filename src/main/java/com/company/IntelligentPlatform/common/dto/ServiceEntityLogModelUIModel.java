package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;

public class ServiceEntityLogModelUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "refNodeName", seName = ServiceEntityLogModel.SENAME, nodeName = ServiceEntityLogModel.NODENAME, nodeInstID = ServiceEntityLogModel.SENAME, showOnList = false, searchFlag = false, tabId = TABID_BASIC)
	protected String refNodeName;
	
	protected String refSEName;

	@ISEUIModelMapping(fieldName = "processMode", seName = ServiceEntityLogModel.SENAME, nodeName = ServiceEntityLogModel.NODENAME, nodeInstID = ServiceEntityLogModel.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceEntityLogModel_processMode", valueFieldName = "processModeValue")
	protected int processMode;
	
	@ISEUIModelMapping(fieldName = "messageType", seName = ServiceEntityLogModel.SENAME, nodeName = ServiceEntityLogModel.NODENAME, nodeInstID = ServiceEntityLogModel.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceEntityLogModel_messageType", valueFieldName = "processModeValue")
	protected int messageType;

	@ISEUIModelMapping(fieldName = "refUUID", seName = ServiceEntityLogModel.SENAME, nodeName = ServiceEntityLogModel.NODENAME, nodeInstID = ServiceEntityLogModel.SENAME, showOnList = false, tabId = TABID_BASIC)
	protected String refUUID;
	
	protected String processModeValue;
	
	protected String createdBy;
	
	protected String messageTypeValue;
	
	protected String refLogonUserId;
	
	protected String refLogonUserName;
	
	protected String lastUpdateTime;

	public int getMessageType() {
		return this.messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getRefNodeName() {
		return this.refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public int getProcessMode() {
		return this.processMode;
	}

	public void setProcessMode(int processMode) {
		this.processMode = processMode;
	}

	public String getRefUUID() {
		return this.refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getProcessModeValue() {
		return processModeValue;
	}

	public void setProcessModeValue(String processModeValue) {
		this.processModeValue = processModeValue;
	}

	public String getMessageTypeValue() {
		return messageTypeValue;
	}

	public void setMessageTypeValue(String messageTypeValue) {
		this.messageTypeValue = messageTypeValue;
	}

	public String getRefLogonUserId() {
		return refLogonUserId;
	}

	public void setRefLogonUserId(String refLogonUserId) {
		this.refLogonUserId = refLogonUserId;
	}

	public String getRefLogonUserName() {
		return refLogonUserName;
	}

	public void setRefLogonUserName(String refLogonUserName) {
		this.refLogonUserName = refLogonUserName;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

}

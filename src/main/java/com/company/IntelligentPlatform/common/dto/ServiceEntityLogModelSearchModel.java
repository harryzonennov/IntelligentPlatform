package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogModel;

@Component
public class ServiceEntityLogModelSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "messageType", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected int messageType;

	@BSearchFieldConfig(fieldName = "processMode", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected int processMode;

	@BSearchFieldConfig(fieldName = "refUUID", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected String refUUID;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected String id;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected String name;
	
	@BSearchFieldConfig(fieldName = "refSEName", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected String refSEName;
	
	@BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceEntityLogModel.NODENAME, seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected String refNodeName;
	
	@BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME)
	protected String refLogonUserId;
	
	@BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID = LogonUser.SENAME)
	protected String refLogonUserName;	
	
	@BSearchFieldConfig(fieldName = "lastUpdateTime", nodeName = ServiceEntityLogModel.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH,
			seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected Date lastUpdateTimeHigh;
	
	protected String lastUpdateTimeStrHigh;
	
	@BSearchFieldConfig(fieldName = "lastUpdateTime", nodeName = ServiceEntityLogModel.NODENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW,
			seName = ServiceEntityLogModel.SENAME, nodeInstID = ServiceEntityLogModel.SENAME)
	protected Date lastUpdateTimeLow;
	
	protected String lastUpdateTimeStrLow;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
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

	public Date getLastUpdateTimeHigh() {
		return lastUpdateTimeHigh;
	}

	public void setLastUpdateTimeHigh(Date lastUpdateTimeHigh) {
		this.lastUpdateTimeHigh = lastUpdateTimeHigh;
	}

	public String getLastUpdateTimeStrHigh() {
		return lastUpdateTimeStrHigh;
	}

	public void setLastUpdateTimeStrHigh(String lastUpdateTimeStrHigh) {
		this.lastUpdateTimeStrHigh = lastUpdateTimeStrHigh;
	}

	public Date getLastUpdateTimeLow() {
		return lastUpdateTimeLow;
	}

	public void setLastUpdateTimeLow(Date lastUpdateTimeLow) {
		this.lastUpdateTimeLow = lastUpdateTimeLow;
	}

	public String getLastUpdateTimeStrLow() {
		return lastUpdateTimeStrLow;
	}

	public void setLastUpdateTimeStrLow(String lastUpdateTimeStrLow) {
		this.lastUpdateTimeStrLow = lastUpdateTimeStrLow;
	}

}

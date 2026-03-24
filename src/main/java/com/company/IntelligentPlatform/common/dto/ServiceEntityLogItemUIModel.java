package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityLogItem;

public class ServiceEntityLogItemUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "refNodeName", seName = ServiceEntityLogItem.SENAME, nodeName = ServiceEntityLogItem.NODENAME, nodeInstID = ServiceEntityLogItem.NODENAME, showOnList = false, searchFlag = false, tabId = TABID_BASIC)
	protected String refNodeName;

	@ISEUIModelMapping(fieldName = "refUUID", seName = ServiceEntityLogItem.SENAME, nodeName = ServiceEntityLogItem.NODENAME, nodeInstID = ServiceEntityLogItem.NODENAME, showOnList = false, tabId = TABID_BASIC)
	protected String refUUID;

	@ISEUIModelMapping(fieldName = "newValue", seName = ServiceEntityLogItem.SENAME, nodeName = ServiceEntityLogItem.NODENAME, nodeInstID = ServiceEntityLogItem.NODENAME, tabId = TABID_BASIC)
	protected String newValue;

	@ISEUIModelMapping(fieldName = "fieldType", seName = ServiceEntityLogItem.SENAME, nodeName = ServiceEntityLogItem.NODENAME, nodeInstID = ServiceEntityLogItem.NODENAME, tabId = TABID_BASIC)
	protected String fieldType;

	@ISEUIModelMapping(fieldName = "oldValue", seName = ServiceEntityLogItem.SENAME, nodeName = ServiceEntityLogItem.NODENAME, nodeInstID = ServiceEntityLogItem.NODENAME, tabId = TABID_BASIC)
	protected String oldValue;
	
	protected String refSEName;

	public String getRefNodeName() {
		return this.refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getRefUUID() {
		return this.refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

}

package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceExtendFieldSetting;
import com.company.IntelligentPlatform.common.model.ServiceExtensionSetting;

public class ServiceExtendFieldSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "searchFlag", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME, 
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected boolean searchFlag;

	@BSearchFieldConfig(fieldName = "fieldType", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME, 
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected String fieldType;
	
	
	protected String parentNodeId;

	@BSearchFieldConfig(fieldName = "uuid", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME,
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected String uuid;
	
	@BSearchFieldConfig(fieldName = "parentNodeUUID", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME,
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected String parentNodeUUID;
	
	@BSearchFieldConfig(fieldName = "rootNodeUUID", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME,
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected String rootNodeUUID;

	@BSearchFieldConfig(fieldName = "storeModelName", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME,
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected String storeModelName;

	@BSearchFieldConfig(fieldName = "fieldName", seName = ServiceExtendFieldSetting.SENAME, nodeName = ServiceExtendFieldSetting.NODENAME, 
			nodeInstID = ServiceExtendFieldSetting.NODENAME)
	protected String fieldName;
	
	@BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, 
			nodeInstID = ServiceExtensionSetting.SENAME)
	protected String refNodeName;

	@BSearchFieldConfig(fieldName = "refSEName", nodeName = ServiceExtensionSetting.NODENAME, seName = ServiceExtensionSetting.SENAME, 
			nodeInstID = ServiceExtensionSetting.SENAME)
	protected String refSEName;
	
	@BSearchFieldConfig(fieldName = "id", seName = ServiceExtensionSetting.SENAME, nodeName = ServiceExtensionSetting.NODENAME, 
			nodeInstID = ServiceExtensionSetting.NODENAME)
	protected String refNodeInstId;

	public boolean getSearchFlag() {
		return this.searchFlag;
	}

	public void setSearchFlag(boolean searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getRootNodeUUID() {
		return rootNodeUUID;
	}

	public void setRootNodeUUID(String rootNodeUUID) {
		this.rootNodeUUID = rootNodeUUID;
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

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeInstId() {
		return refNodeInstId;
	}

	public void setRefNodeInstId(String refNodeInstId) {
		this.refNodeInstId = refNodeInstId;
	}

}

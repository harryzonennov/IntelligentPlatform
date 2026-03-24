package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.SystemExecutorSetting;

@Component
public class SystemExecutorSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "refAOUUID", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected String refAOUUID;

	@BSearchFieldConfig(fieldName = "refActionCode", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected String refActionCode;

	@BSearchFieldConfig(fieldName = "id", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "executeBatchNumber", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected int executeBatchNumber;

	@BSearchFieldConfig(fieldName = "executionType", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected int executionType;

	@BSearchFieldConfig(fieldName = "refPreExecuteSettingUUID", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected String refPreExecuteSettingUUID;

	@BSearchFieldConfig(fieldName = "name", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "uuid", nodeName = SystemExecutorSetting.NODENAME, seName = SystemExecutorSetting.SENAME, nodeInstID = SystemExecutorSetting.SENAME)
	protected String uuid;

	@BSearchFieldConfig(fieldName = "id", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String authorizationObjectId;

	@BSearchFieldConfig(fieldName = "name", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String authorizationObjectName;

	public String getRefAOUUID() {
		return refAOUUID;
	}

	public void setRefAOUUID(String refAOUUID) {
		this.refAOUUID = refAOUUID;
	}

	public String getRefActionCode() {
		return refActionCode;
	}

	public void setRefActionCode(String refActionCode) {
		this.refActionCode = refActionCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getExecuteBatchNumber() {
		return executeBatchNumber;
	}

	public void setExecuteBatchNumber(int executeBatchNumber) {
		this.executeBatchNumber = executeBatchNumber;
	}

	public int getExecutionType() {
		return executionType;
	}

	public void setExecutionType(int executionType) {
		this.executionType = executionType;
	}

	public String getRefPreExecuteSettingUUID() {
		return refPreExecuteSettingUUID;
	}

	public void setRefPreExecuteSettingUUID(String refPreExecuteSettingUUID) {
		this.refPreExecuteSettingUUID = refPreExecuteSettingUUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAuthorizationObjectId() {
		return authorizationObjectId;
	}

	public void setAuthorizationObjectId(String authorizationObjectId) {
		this.authorizationObjectId = authorizationObjectId;
	}

	public String getAuthorizationObjectName() {
		return authorizationObjectName;
	}

	public void setAuthorizationObjectName(String authorizationObjectName) {
		this.authorizationObjectName = authorizationObjectName;
	}
	

}

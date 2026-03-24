package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * AuthorizationObject UI Model
 **
 * @author
 * @date Sat Jan 30 12:02:23 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Component
public class AuthorizationObjectSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "id", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String name;
	
	@BSearchFieldConfig(fieldName = "simObjectArray", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String simObjectArray;

	@BSearchFieldConfig(fieldName = "authorizationObjectType", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationObjecSearch_type", valueFieldName = "")
	protected int authorizationObjectType;

	@BSearchFieldConfig(fieldName = "name", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String authorizationGroupName;

	@BSearchFieldConfig(fieldName = "id", nodeName = AuthorizationGroup.NODENAME, seName = AuthorizationGroup.SENAME, nodeInstID = AuthorizationGroup.SENAME)
	protected String authorizationGroupId;

	@BSearchFieldConfig(fieldName = "refAGUUID", nodeName = AuthorizationObject.NODENAME, seName = AuthorizationObject.SENAME, nodeInstID = AuthorizationObject.SENAME)
	protected String refAGUUID;

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

	public String getSimObjectArray() {
		return simObjectArray;
	}

	public void setSimObjectArray(String simObjectArray) {
		this.simObjectArray = simObjectArray;
	}

	public int getAuthorizationObjectType() {
		return authorizationObjectType;
	}

	public void setAuthorizationObjectType(int authorizationObjectType) {
		this.authorizationObjectType = authorizationObjectType;
	}

	public String getAuthorizationGroupName() {
		return authorizationGroupName;
	}

	public void setAuthorizationGroupName(String authorizationGroupName) {
		this.authorizationGroupName = authorizationGroupName;
	}

	public String getAuthorizationGroupId() {
		return authorizationGroupId;
	}

	public void setAuthorizationGroupId(String authorizationGroupId) {
		this.authorizationGroupId = authorizationGroupId;
	}

	public String getRefAGUUID() {
		return refAGUUID;
	}

	public void setRefAGUUID(String refAGUUID) {
		this.refAGUUID = refAGUUID;
	}
}

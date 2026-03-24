package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class AuthorizationObjectUIModel extends SEUIComModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationObject_type", valueFieldName = "authorizationObjectTypeValue")
	protected int authorizationObjectType;
	
	protected String authorizationObjectTypeValue;

	protected String[] simObjectArray;
	
	protected String refAGUUID;
	
	protected String authorizationGroupName;
	
	protected String authorizationGroupId;
	
	protected String serviceEntityName;
	
	protected String nodeName;

	protected int subSystemAuthorNeed;

	protected String subSystemAuthorNeedLabel;

	protected int systemAuthorCheck;

	protected String systemAuthorCheckLabel;
	
	protected List<ActionCodeUIModel> actionCodeList = new ArrayList<>();

	public int getAuthorizationObjectType() {
		return authorizationObjectType;
	}

	public void setAuthorizationObjectType(int authorizationObjectType) {
		this.authorizationObjectType = authorizationObjectType;
	}

	public String getAuthorizationObjectTypeValue() {
		return authorizationObjectTypeValue;
	}

	public void setAuthorizationObjectTypeValue(String authorizationObjectTypeValue) {
		this.authorizationObjectTypeValue = authorizationObjectTypeValue;
	}

	public List<ActionCodeUIModel> getActionCodeList() {
		return actionCodeList;
	}

	public void setActionCodeList(List<ActionCodeUIModel> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}

	public String[] getSimObjectArray() {
		return simObjectArray;
	}

	public void setSimObjectArray(String[] simObjectArray) {
		this.simObjectArray = simObjectArray;
	}

	public String getRefAGUUID() {
		return refAGUUID;
	}

	public void setRefAGUUID(String refAGUUID) {
		this.refAGUUID = refAGUUID;
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

	public String getServiceEntityName() {
		return serviceEntityName;
	}

	public void setServiceEntityName(String serviceEntityName) {
		this.serviceEntityName = serviceEntityName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getSubSystemAuthorNeed() {
		return subSystemAuthorNeed;
	}

	public void setSubSystemAuthorNeed(int subSystemAuthorNeed) {
		this.subSystemAuthorNeed = subSystemAuthorNeed;
	}

	public String getSubSystemAuthorNeedLabel() {
		return subSystemAuthorNeedLabel;
	}

	public void setSubSystemAuthorNeedLabel(String subSystemAuthorNeedLabel) {
		this.subSystemAuthorNeedLabel = subSystemAuthorNeedLabel;
	}

	public int getSystemAuthorCheck() {
		return systemAuthorCheck;
	}

	public void setSystemAuthorCheck(int systemAuthorCheck) {
		this.systemAuthorCheck = systemAuthorCheck;
	}

	public String getSystemAuthorCheckLabel() {
		return systemAuthorCheckLabel;
	}

	public void setSystemAuthorCheckLabel(String systemAuthorCheckLabel) {
		this.systemAuthorCheckLabel = systemAuthorCheckLabel;
	}
}

package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class RoleAuthorizationUIModel extends SEUIComModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationObject_type", valueFieldName = "authorizationObjectTypeValue")
	protected int authorizationObjectType;
	
	protected String authorizationObjectTypeValue;
	
	protected String groupName;
	
	@ISEDropDownResourceMapping(resouceMapping = "RoleAuthorization_processType", valueFieldName = "processTypeValue")
	protected int processType; 
	
	protected String processTypeValue;
	
	protected String refUUID;

	protected String refAGUUID;
	
	protected String[] actionCodeArray;

	protected String[] actionCodeValueArray;

	protected String[] actionCodeIdArray;
	
	protected List<ActionCodeUIModel> actionCodeList = new ArrayList<>();
	
	protected String simObjectArray;
	
	protected String refSEName;
	
	protected String refNodeName;

	protected int subSystemAuthorNeed;

	protected String subSystemAuthorNeedLabel;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ActionCodeUIModel> getActionCodeList() {
		return actionCodeList;
	}

	public void setActionCodeList(List<ActionCodeUIModel> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

	public String getProcessTypeValue() {
		return processTypeValue;
	}

	public void setProcessTypeValue(String processTypeValue) {
		this.processTypeValue = processTypeValue;
	}

	public String[] getActionCodeArray() {
		return actionCodeArray;
	}

	public void setActionCodeArray(String[] actionCodeArray) {
		this.actionCodeArray = actionCodeArray;
	}

	public String[] getActionCodeValueArray() {
		return actionCodeValueArray;
	}

	public void setActionCodeValueArray(String[] actionCodeValueArray) {
		this.actionCodeValueArray = actionCodeValueArray;
	}

	public String[] getActionCodeIdArray() {
		return actionCodeIdArray;
	}

	public void setActionCodeIdArray(String[] actionCodeIdArray) {
		this.actionCodeIdArray = actionCodeIdArray;
	}

	public String getSimObjectArray() {
		return simObjectArray;
	}

	public void setSimObjectArray(String simObjectArray) {
		this.simObjectArray = simObjectArray;
	}

	public String getRefSEName() {
		return refSEName;
	}

	public void setRefSEName(String refSEName) {
		this.refSEName = refSEName;
	}

	public String getRefNodeName() {
		return refNodeName;
	}

	public void setRefNodeName(String refNodeName) {
		this.refNodeName = refNodeName;
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

	public String getRefAGUUID() {
		return refAGUUID;
	}

	public void setRefAGUUID(String refAGUUID) {
		this.refAGUUID = refAGUUID;
	}
}

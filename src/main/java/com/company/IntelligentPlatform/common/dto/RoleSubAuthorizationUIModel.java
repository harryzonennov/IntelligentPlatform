package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

import java.util.ArrayList;
import java.util.List;

public class RoleSubAuthorizationUIModel extends SEUIComModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationObject_type", valueFieldName = "authorizationObjectTypeValue")
	protected int authorizationObjectType;
	
	protected String authorizationObjectTypeValue;
	
	protected String groupName;
	
	@ISEDropDownResourceMapping(resouceMapping = "RoleAuthorization_processType", valueFieldName = "processTypeValue")
	protected int processType; 
	
	protected String processTypeValue;
	
	protected String refUUID;
	
	protected String[] actionCodeArray;

	protected String[] actionCodeValueArray;

	protected String[] actionCodeIdArray;
	
	protected List<ActionCodeUIModel> actionCodeList = new ArrayList<>();
	
	protected String simObjectArray;
	
	protected String refSEName;
	
	protected String refNodeName;

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

}

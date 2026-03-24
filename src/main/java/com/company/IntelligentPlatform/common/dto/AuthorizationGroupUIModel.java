package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.AuthorizationGroup;

public class AuthorizationGroupUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "crossGroupProcessType", seName = AuthorizationGroup.SENAME, nodeName = AuthorizationGroup.NODENAME, nodeInstID = AuthorizationGroup.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationGroup_crossGroupProcessType", valueFieldName = "authorizationObjectTypeValue")
	protected int crossGroupProcessType;
	
	protected String crossGroupProcessTypeValue;

	@ISEUIModelMapping(fieldName = "innerProcessType", seName = AuthorizationGroup.SENAME, nodeName = AuthorizationGroup.NODENAME, nodeInstID = AuthorizationGroup.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "AuthorizationGroup_innerProcessType", valueFieldName = "authorizationObjectTypeValue")
	protected int innerProcessType;
	
	protected String innerProcessTypeValue;

	public int getCrossGroupProcessType() {
		return this.crossGroupProcessType;
	}

	public void setCrossGroupProcessType(int crossGroupProcessType) {
		this.crossGroupProcessType = crossGroupProcessType;
	}

	public int getInnerProcessType() {
		return this.innerProcessType;
	}

	public void setInnerProcessType(int innerProcessType) {
		this.innerProcessType = innerProcessType;
	}

	public String getCrossGroupProcessTypeValue() {
		return crossGroupProcessTypeValue;
	}

	public void setCrossGroupProcessTypeValue(String crossGroupProcessTypeValue) {
		this.crossGroupProcessTypeValue = crossGroupProcessTypeValue;
	}

	public String getInnerProcessTypeValue() {
		return innerProcessTypeValue;
	}

	public void setInnerProcessTypeValue(String innerProcessTypeValue) {
		this.innerProcessTypeValue = innerProcessTypeValue;
	}

}

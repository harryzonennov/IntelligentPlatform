package com.company.IntelligentPlatform.common.controller;

import java.util.List;

public class ActionCodeJSONModel {
	
	protected String baseUUID;
	
	protected List<ActionCodeUUIDUnion> actionCodeList;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public List<ActionCodeUUIDUnion> getActionCodeList() {
		return actionCodeList;
	}

	public void setActionCodeList(List<ActionCodeUUIDUnion> actionCodeList) {
		this.actionCodeList = actionCodeList;
	}

}

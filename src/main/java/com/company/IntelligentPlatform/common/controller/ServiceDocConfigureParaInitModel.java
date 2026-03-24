package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;

public class ServiceDocConfigureParaInitModel extends SimpleSEJSONRequest{
	
	private String parentGroupUUID;

	public String getParentGroupUUID() {
		return parentGroupUUID;
	}

	public void setParentGroupUUID(String parentGroupUUID) {
		this.parentGroupUUID = parentGroupUUID;
	}

}

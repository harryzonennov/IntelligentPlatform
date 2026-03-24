package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ResourceAuthorization;

public class ResourceAuthorizationUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "refNodeName", seName = ResourceAuthorization.SENAME, nodeName = ResourceAuthorization.NODENAME, nodeInstID = ResourceAuthorization.NODENAME, showOnList = false, searchFlag = false, tabId = "resFinAccountFieldSetting")
	protected String refNodeName;

	@ISEUIModelMapping(fieldName = "refUUID", seName = ResourceAuthorization.SENAME, nodeName = ResourceAuthorization.NODENAME, nodeInstID = ResourceAuthorization.NODENAME, showOnList = false, tabId = "resFinAccountFieldSetting")
	protected String refUUID;

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

}

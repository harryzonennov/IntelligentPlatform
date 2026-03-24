package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class EmpLogonUserUIModel extends SEUIComModel {

	protected String refUUID;

	protected String refSEName;

	protected String refNodeName;

	protected String logonUserId;

	protected String logonUserName;

	protected int mainFlag;

	protected String mainFlagValue;

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
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

	public String getLogonUserId() {
		return logonUserId;
	}

	public void setLogonUserId(String logonUserId) {
		this.logonUserId = logonUserId;
	}

	public String getLogonUserName() {
		return logonUserName;
	}

	public void setLogonUserName(String logonUserName) {
		this.logonUserName = logonUserName;
	}

	public int getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(int mainFlag) {
		this.mainFlag = mainFlag;
	}

	public String getMainFlagValue() {
		return mainFlagValue;
	}

	public void setMainFlagValue(String mainFlagValue) {
		this.mainFlagValue = mainFlagValue;
	}
}

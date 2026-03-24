package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocActionConfigureUIModel extends SEUIComModel {

	protected int homeDocType;

	protected String homeDocTypeValue;

	protected String parentDocId;

	protected String parenDocName;

	protected String jsonContent;

	protected int customSwitch;

	protected String customSwitchValue;

	public String getJsonContent() {
		return jsonContent;
	}

	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}

	public int getHomeDocType() {
		return homeDocType;
	}

	public void setHomeDocType(int homeDocType) {
		this.homeDocType = homeDocType;
	}

	public String getHomeDocTypeValue() {
		return homeDocTypeValue;
	}

	public void setHomeDocTypeValue(String homeDocTypeValue) {
		this.homeDocTypeValue = homeDocTypeValue;
	}

	public String getParentDocId() {
		return parentDocId;
	}

	public void setParentDocId(String parentDocId) {
		this.parentDocId = parentDocId;
	}

	public String getParenDocName() {
		return parenDocName;
	}

	public void setParenDocName(String parenDocName) {
		this.parenDocName = parenDocName;
	}

	public int getCustomSwitch() {
		return customSwitch;
	}

	public void setCustomSwitch(int customSwitch) {
		this.customSwitch = customSwitch;
	}

	public String getCustomSwitchValue() {
		return customSwitchValue;
	}

	public void setCustomSwitchValue(String customSwitchValue) {
		this.customSwitchValue = customSwitchValue;
	}
}

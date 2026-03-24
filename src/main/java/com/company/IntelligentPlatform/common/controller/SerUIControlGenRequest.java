package com.company.IntelligentPlatform.common.controller;

public class SerUIControlGenRequest {
	
	protected int displayIndex;
	
	protected String baseUUID;
	
	protected String inputControlType;
	
	protected String refFieldUUID;

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getRefFieldUUID() {
		return refFieldUUID;
	}

	public void setRefFieldUUID(String refFieldUUID) {
		this.refFieldUUID = refFieldUUID;
	}

	public String getInputControlType() {
		return inputControlType;
	}

	public void setInputControlType(String inputControlType) {
		this.inputControlType = inputControlType;
	}

}

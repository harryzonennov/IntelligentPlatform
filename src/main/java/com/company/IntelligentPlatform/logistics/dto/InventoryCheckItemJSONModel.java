package com.company.IntelligentPlatform.logistics.dto;

public class InventoryCheckItemJSONModel {
	
	protected String uuid;
	
	protected String resultAmount;
	
	protected String resultDeclaredValue;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(String resultAmount) {
		this.resultAmount = resultAmount;
	}

	public String getResultDeclaredValue() {
		return resultDeclaredValue;
	}

	public void setResultDeclaredValue(String resultDeclaredValue) {
		this.resultDeclaredValue = resultDeclaredValue;
	}

}

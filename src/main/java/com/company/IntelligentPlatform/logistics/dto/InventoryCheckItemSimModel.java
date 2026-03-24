package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class InventoryCheckItemSimModel extends SEUIComModel{
	
	protected String itemUUID;
	
	protected double resultAmount;
	
	protected String resultUnitUUID;
	
	protected double resultDeclaredValue;

	public String getItemUUID() {
		return itemUUID;
	}

	public void setItemUUID(String itemUUID) {
		this.itemUUID = itemUUID;
	}

	public double getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(double resultAmount) {
		this.resultAmount = resultAmount;
	}

	public String getResultUnitUUID() {
		return resultUnitUUID;
	}

	public void setResultUnitUUID(String resultUnitUUID) {
		this.resultUnitUUID = resultUnitUUID;
	}

	public double getResultDeclaredValue() {
		return resultDeclaredValue;
	}

	public void setResultDeclaredValue(double resultDeclaredValue) {
		this.resultDeclaredValue = resultDeclaredValue;
	}
	
}

package com.company.IntelligentPlatform.common.controller;

import java.util.Map;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocComDataValueSettingModel extends SEUIComModel{
	
	protected String baseUUID;
	
	protected double offsetValue;
	
	protected int offsetDirection;
	
	protected String offsetUnit;
	
	protected Map<?, ?> offsetUnitMap;
	
	protected String dataProviderComments;
	
	protected String dataProviderID;
	
	protected String standardAmount;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public double getOffsetValue() {
		return offsetValue;
	}

	public void setOffsetValue(double offsetValue) {
		this.offsetValue = offsetValue;
	}

	public Map<?, ?> getOffsetUnitMap() {
		return offsetUnitMap;
	}

	public void setOffsetUnitMap(Map<?, ?> offsetUnitMap) {
		this.offsetUnitMap = offsetUnitMap;
	}

	public String getDataProviderComments() {
		return dataProviderComments;
	}

	public void setDataProviderComments(String dataProviderComments) {
		this.dataProviderComments = dataProviderComments;
	}

	public String getDataProviderID() {
		return dataProviderID;
	}

	public void setDataProviderID(String dataProviderID) {
		this.dataProviderID = dataProviderID;
	}

	public int getOffsetDirection() {
		return offsetDirection;
	}

	public void setOffsetDirection(int offsetDirection) {
		this.offsetDirection = offsetDirection;
	}

	public String getOffsetUnit() {
		return offsetUnit;
	}

	public String getStandardAmount() {
		return standardAmount;
	}

	public void setStandardAmount(String standardAmount) {
		this.standardAmount = standardAmount;
	}

	public void setOffsetUnit(String offsetUnit) {
		this.offsetUnit = offsetUnit;
	}

}

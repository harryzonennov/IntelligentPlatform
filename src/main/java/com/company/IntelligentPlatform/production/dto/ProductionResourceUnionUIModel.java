package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProductionResourceUnionUIModel extends SEUIComModel {

	protected String refCostCenterUUID;

	protected String refCostCenterID;

	protected String refCostCenterName;

	protected int keyResourceFlag;
	
	protected String keyResourceValue;

	@ISEDropDownResourceMapping(resouceMapping = "ProductionResourceUnion_resourceType", valueFieldName = "resourceTypeValue")
	protected int resourceType;

	protected String resourceTypeValue;

	protected double utilizationRate;

	protected double efficiency;

	protected int dailyShift;	

	protected double costInhour;
	
	protected double workHoursInShift;

	public String getRefCostCenterUUID() {
		return refCostCenterUUID;
	}

	public void setRefCostCenterUUID(String refCostCenterUUID) {
		this.refCostCenterUUID = refCostCenterUUID;
	}

	public String getRefCostCenterID() {
		return refCostCenterID;
	}

	public void setRefCostCenterID(String refCostCenterID) {
		this.refCostCenterID = refCostCenterID;
	}

	public String getRefCostCenterName() {
		return refCostCenterName;
	}

	public void setRefCostCenterName(String refCostCenterName) {
		this.refCostCenterName = refCostCenterName;
	}
	
	public int getKeyResourceFlag() {
		return keyResourceFlag;
	}

	public void setKeyResourceFlag(int keyResourceFlag) {
		this.keyResourceFlag = keyResourceFlag;
	}

	public String getKeyResourceValue() {
		return keyResourceValue;
	}

	public void setKeyResourceValue(String keyResourceValue) {
		this.keyResourceValue = keyResourceValue;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceTypeValue() {
		return resourceTypeValue;
	}

	public void setResourceTypeValue(String resourceTypeValue) {
		this.resourceTypeValue = resourceTypeValue;
	}

	public double getUtilizationRate() {
		return utilizationRate;
	}

	public void setUtilizationRate(double utilizationRate) {
		this.utilizationRate = utilizationRate;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	public int getDailyShift() {
		return dailyShift;
	}

	public void setDailyShift(int dailyShift) {
		this.dailyShift = dailyShift;
	}

	public double getCostInhour() {
		return costInhour;
	}

	public void setCostInhour(double costInhour) {
		this.costInhour = costInhour;
	}

	public double getWorkHoursInShift() {
		return workHoursInShift;
	}

	public void setWorkHoursInShift(double workHoursInShift) {
		this.workHoursInShift = workHoursInShift;
	}

}

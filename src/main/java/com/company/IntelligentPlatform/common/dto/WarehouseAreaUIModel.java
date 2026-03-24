package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class WarehouseAreaUIModel extends SEUIComModel {

	protected String areaUUID;
	
	protected String warehouseId;
	
	protected String warehouseUUID;
	
	protected String radioFreqReaderID;
	
	protected double space;
	
	protected double grossWeight;

	protected int switchFlag;
	
	protected String switchFlagValue;
	
	protected double length;
	
	protected double width;
	
	protected double area;
	
	protected double height;
	
	protected double volume;

	protected boolean hasFootStep;
	
	protected boolean restrictedGoodsFlag;
	
	protected boolean forbiddenGoodsFlag;
	
	protected int operationMode;

	public String getAreaUUID() {
		return areaUUID;
	}

	public void setAreaUUID(String areaUUID) {
		this.areaUUID = areaUUID;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseUUID() {
		return warehouseUUID;
	}

	public void setWarehouseUUID(String warehouseUUID) {
		this.warehouseUUID = warehouseUUID;
	}

	public String getRadioFreqReaderID() {
		return radioFreqReaderID;
	}

	public void setRadioFreqReaderID(String radioFreqReaderID) {
		this.radioFreqReaderID = radioFreqReaderID;
	}

	public double getSpace() {
		return space;
	}

	public void setSpace(double space) {
		this.space = space;
	}

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getSwitchFlagValue() {
		return switchFlagValue;
	}

	public void setSwitchFlagValue(String switchFlagValue) {
		this.switchFlagValue = switchFlagValue;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean getHasFootStep() {
		return hasFootStep;
	}

	public void setHasFootStep(boolean hasFootStep) {
		this.hasFootStep = hasFootStep;
	}

	public boolean getRestrictedGoodsFlag() {
		return restrictedGoodsFlag;
	}

	public void setRestrictedGoodsFlag(boolean restrictedGoodsFlag) {
		this.restrictedGoodsFlag = restrictedGoodsFlag;
	}

	public boolean getForbiddenGoodsFlag() {
		return forbiddenGoodsFlag;
	}

	public void setForbiddenGoodsFlag(boolean forbiddenGoodsFlag) {
		this.forbiddenGoodsFlag = forbiddenGoodsFlag;
	}

	public int getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(int operationMode) {
		this.operationMode = operationMode;
	}

}

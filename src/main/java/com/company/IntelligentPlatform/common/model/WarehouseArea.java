package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class WarehouseArea extends ServiceEntityNode{

	public final static String NODENAME = IServiceModelConstants.WarehouseArea;

	public final static String SENAME = Warehouse.SENAME;

	protected int storeType;

	protected int operationType;

	protected double space;

	protected double grossWeight;

	protected int switchFlag;

    protected double length;

	protected double width;

	protected double height;

	protected double area;

	protected double volume;

    protected boolean hasFootStep;

	protected boolean restrictedGoodsFlag;

	protected boolean forbiddenGoodsFlag;

	protected int operationMode;

	public WarehouseArea(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
	}

	public int getStoreType() {
		return storeType;
	}

	public void setStoreType(int storeType) {
		this.storeType = storeType;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
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

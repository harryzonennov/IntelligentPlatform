package com.company.IntelligentPlatform.common.service;

public class SimpleDataOffsetUnion {
	
	protected double offsetValue;
	
	protected Object offsetUnit;
	
	protected int offsetDirection;

	public double getOffsetValue() {
		return offsetValue;
	}

	public void setOffsetValue(double offsetValue) {
		this.offsetValue = offsetValue;
	}

	public Object getOffsetUnit() {
		return offsetUnit;
	}

	public void setOffsetUnit(Object offsetUnit) {
		this.offsetUnit = offsetUnit;
	}

	public int getOffsetDirection() {
		return offsetDirection;
	}

	public void setOffsetDirection(int offsetDirection) {
		this.offsetDirection = offsetDirection;
	}

}

package com.company.IntelligentPlatform.common.service;


public class StorageCoreUnit implements Cloneable{
	
	protected String refMaterialSKUUUID;
	
	protected String refUnitUUID;
	
	protected double amount;

	protected int direction;
	
	public static int OPERATOR_ADD = 1;
	
	public static int OPERATOR_MINUS = 2;
	
	public StorageCoreUnit(){
		this.direction = OPERATOR_ADD;
	}

	public StorageCoreUnit(String refMaterialSKUUUID, String refUnitUUID,
			double amount) {
		super();
		this.refMaterialSKUUUID = refMaterialSKUUUID;
		this.refUnitUUID = refUnitUUID;
		this.amount = amount;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(final int direction) {
		this.direction = direction;
	}

	public Object clone() {
		Object o = null;
		try {
			o = (StorageCoreUnit) super.clone();
		} catch (CloneNotSupportedException e) {
			// Should raise exception
			return null;
		}
		return o;
	}

}

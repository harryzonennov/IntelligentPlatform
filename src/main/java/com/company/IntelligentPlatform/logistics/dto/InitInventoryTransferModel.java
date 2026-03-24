package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class InitInventoryTransferModel extends SEUIComModel{
	
	protected String outWarehouseUUID;
	
	protected String outWarehouseName;
	
	protected String outWarehouseID;
	
    protected String inWarehouseUUID;
	
	protected String inWarehouseName;
	
	protected String inWarehouseID;

	public String getOutWarehouseUUID() {
		return outWarehouseUUID;
	}

	public void setOutWarehouseUUID(String outWarehouseUUID) {
		this.outWarehouseUUID = outWarehouseUUID;
	}

	public String getOutWarehouseName() {
		return outWarehouseName;
	}

	public void setOutWarehouseName(String outWarehouseName) {
		this.outWarehouseName = outWarehouseName;
	}

	public String getOutWarehouseID() {
		return outWarehouseID;
	}

	public void setOutWarehouseID(String outWarehouseID) {
		this.outWarehouseID = outWarehouseID;
	}

	public String getInWarehouseUUID() {
		return inWarehouseUUID;
	}

	public void setInWarehouseUUID(String inWarehouseUUID) {
		this.inWarehouseUUID = inWarehouseUUID;
	}

	public String getInWarehouseName() {
		return inWarehouseName;
	}

	public void setInWarehouseName(String inWarehouseName) {
		this.inWarehouseName = inWarehouseName;
	}

	public String getInWarehouseID() {
		return inWarehouseID;
	}

	public void setInWarehouseID(String inWarehouseID) {
		this.inWarehouseID = inWarehouseID;
	}
	
}

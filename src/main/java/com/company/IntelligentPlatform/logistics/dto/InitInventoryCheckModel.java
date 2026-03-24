package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class InitInventoryCheckModel extends SEUIComModel{
	
	protected String uuid;
	
	protected String refWarehouseUUID;
	
	protected String warehouseID;
	
	protected String warehouseName;
	
	protected String refWarehouseAreaUUID;
	
	protected String warehouseAreaID;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getWarehouseAreaID() {
		return warehouseAreaID;
	}

	public void setWarehouseAreaID(String warehouseAreaID) {
		this.warehouseAreaID = warehouseAreaID;
	}
	

}

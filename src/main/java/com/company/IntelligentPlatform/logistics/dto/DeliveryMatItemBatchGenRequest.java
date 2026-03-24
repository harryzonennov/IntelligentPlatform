package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;

public class DeliveryMatItemBatchGenRequest extends DocumentMatItemBatchGenRequest{
	
	protected String refWarehouseUUID;
	
	protected String refWarehouseAreaUUID;

	protected int excludeReserved;

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

	public int getExcludeReserved() {
		return excludeReserved;
	}

	public void setExcludeReserved(int excludeReserved) {
		this.excludeReserved = excludeReserved;
	}
}

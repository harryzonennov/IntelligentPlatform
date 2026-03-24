package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.service.StorageCoreUnit;

/**
 * Request mode to calculate Available store amount
 *
 * @author Zhang,Hang
 *
 */
public class StoreAvailableStoreItemResponse {

	protected WarehouseStoreItem warehouseStoreItem;

	protected StorageCoreUnit availableAmount;

	protected boolean reservedDocConsidered;

	protected String reservedDocUUID;

	public StoreAvailableStoreItemResponse(){

	}

	public StoreAvailableStoreItemResponse(WarehouseStoreItem warehouseStoreItem, StorageCoreUnit availableAmount,
										   boolean reservedDocConsidered, String reservedDocUUID) {
		this.warehouseStoreItem = warehouseStoreItem;
		this.availableAmount = availableAmount;
		this.reservedDocConsidered = reservedDocConsidered;
		this.reservedDocUUID = reservedDocUUID;
	}

	public WarehouseStoreItem getWarehouseStoreItem() {
		return warehouseStoreItem;
	}

	public void setWarehouseStoreItem(WarehouseStoreItem warehouseStoreItem) {
		this.warehouseStoreItem = warehouseStoreItem;
	}

	public StorageCoreUnit getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(StorageCoreUnit availableAmount) {
		this.availableAmount = availableAmount;
	}

	public boolean isReservedDocConsidered() {
		return reservedDocConsidered;
	}

	public void setReservedDocConsidered(boolean reservedDocConsidered) {
		this.reservedDocConsidered = reservedDocConsidered;
	}

	public String getReservedDocUUID() {
		return reservedDocUUID;
	}

	public void setReservedDocUUID(String reservedDocUUID) {
		this.reservedDocUUID = reservedDocUUID;
	}
}

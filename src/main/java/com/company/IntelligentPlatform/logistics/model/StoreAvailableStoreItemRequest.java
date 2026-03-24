package com.company.IntelligentPlatform.logistics.model;


/**
 * Request mode to calculate Available store amount
 *
 * @author Zhang,Hang
 *
 */
public class StoreAvailableStoreItemRequest {

	protected WarehouseStoreItem warehouseStoreItem;

	/*
	 * This parameter could be mulitple value, seperated with','
	 */
	protected String homeOutItemUUID;

	protected boolean reservedDocConsidered;

	protected String reservedDocUUID;

	public StoreAvailableStoreItemRequest(){

	}

	public StoreAvailableStoreItemRequest(
			WarehouseStoreItem warehouseStoreItem, String homeOutItemUUID,
			boolean reservedDocConsidered) {
		super();
		this.warehouseStoreItem = warehouseStoreItem;
		this.homeOutItemUUID = homeOutItemUUID;
		this.reservedDocConsidered = reservedDocConsidered;
	}

	public StoreAvailableStoreItemRequest(
			WarehouseStoreItem warehouseStoreItem, String homeOutItemUUID,
			boolean reservedDocConsidered, String reservedDocUUID) {
		super();
		this.warehouseStoreItem = warehouseStoreItem;
		this.homeOutItemUUID = homeOutItemUUID;
		this.reservedDocConsidered = reservedDocConsidered;
		this.reservedDocUUID = reservedDocUUID;
	}

	public WarehouseStoreItem getWarehouseStoreItem() {
		return warehouseStoreItem;
	}

	public void setWarehouseStoreItem(WarehouseStoreItem warehouseStoreItem) {
		this.warehouseStoreItem = warehouseStoreItem;
	}

	public String getHomeOutItemUUID() {
		return homeOutItemUUID;
	}

	public void setHomeOutItemUUID(String homeOutItemUUID) {
		this.homeOutItemUUID = homeOutItemUUID;
	}

	public boolean getReservedDocConsidered() {
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

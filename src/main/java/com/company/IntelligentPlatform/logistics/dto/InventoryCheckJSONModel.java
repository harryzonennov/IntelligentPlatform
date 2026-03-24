package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class InventoryCheckJSONModel extends SEUIComModel{
	
	protected String uuid;
	
	protected String id;
	
	protected String approveNote;
	
	protected InventoryCheckItemJSONModel[] inventoryCheckItemList;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApproveNote() {
		return approveNote;
	}

	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}

	public InventoryCheckItemJSONModel[] getInventoryCheckItemList() {
		return inventoryCheckItemList;
	}

	public void setInventoryCheckItemList(
			InventoryCheckItemJSONModel[] inventoryCheckItemList) {
		this.inventoryCheckItemList = inventoryCheckItemList;
	}

}

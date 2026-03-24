package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;

public class DeliveryRequestAmountRequest extends
		DocumentMatItemBatchGenRequest {

	private StorageCoreUnit storageCoreUnit;

	private String reservedMatItemUUID;

	private int reservedDocType;

	public String getReservedMatItemUUID() {
		return reservedMatItemUUID;
	}

	public void setReservedMatItemUUID(String reservedMatItemUUID) {
		this.reservedMatItemUUID = reservedMatItemUUID;
	}

	public StorageCoreUnit getStorageCoreUnit() {
		return storageCoreUnit;
	}

	public void setStorageCoreUnit(StorageCoreUnit storageCoreUnit) {
		this.storageCoreUnit = storageCoreUnit;
	}

	public int getReservedDocType() {
		return reservedDocType;
	}

	public void setReservedDocType(int reservedDocType) {
		this.reservedDocType = reservedDocType;
	}
}

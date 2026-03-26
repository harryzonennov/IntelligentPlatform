package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.dto.ScanBarcodeResponseModel;

public class InboundScanBarcodeResponseModel extends ScanBarcodeResponseModel{
	
	protected String baseWarehouseUUID;
	
	protected String baseWarehouseName;
	
	protected String baseWarehouseId;
	
	protected String inboundId;

	public String getBaseWarehouseUUID() {
		return baseWarehouseUUID;
	}

	public void setBaseWarehouseUUID(String baseWarehouseUUID) {
		this.baseWarehouseUUID = baseWarehouseUUID;
	}

	public String getBaseWarehouseName() {
		return baseWarehouseName;
	}

	public void setBaseWarehouseName(String baseWarehouseName) {
		this.baseWarehouseName = baseWarehouseName;
	}

	public String getBaseWarehouseId() {
		return baseWarehouseId;
	}

	public void setBaseWarehouseId(String baseWarehouseId) {
		this.baseWarehouseId = baseWarehouseId;
	}

	public String getInboundId() {
		return inboundId;
	}

	public void setInboundId(String inboundId) {
		this.inboundId = inboundId;
	}

}

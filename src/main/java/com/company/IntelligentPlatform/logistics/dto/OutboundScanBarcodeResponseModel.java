package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.dto.ScanBarcodeResponseModel;

public class OutboundScanBarcodeResponseModel extends ScanBarcodeResponseModel{
	
	protected String baseWarehouseUUID;
	
	protected String baseWarehouseName;
	
	protected String baseWarehouseId;
	
	protected String outboundId;

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

	public String getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(String outboundId) {
		this.outboundId = outboundId;
	}
	
}

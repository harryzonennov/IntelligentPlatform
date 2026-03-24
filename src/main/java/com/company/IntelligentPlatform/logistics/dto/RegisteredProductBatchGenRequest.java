package com.company.IntelligentPlatform.logistics.dto;

import java.util.List;

public class RegisteredProductBatchGenRequest{
	
	protected String refMaterialSKUUUID;
	
	protected String baseUUID;
	
	protected List<String> serialIdList;

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public List<String> getSerialIdList() {
		return serialIdList;
	}

	public void setSerialIdList(List<String> serialIdList) {
		this.serialIdList = serialIdList;
	}

}

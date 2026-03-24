package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class OutboundItemRequestJSONModel extends SEUIComModel {
	
	protected String uuid;
	
	protected String amount;
	
	protected String refUnitUUID;
	
	protected String baseUUID;
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

}

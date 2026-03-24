package com.company.IntelligentPlatform.common.model;

public class MultiItemJSONResponse {

	protected String baseUUID;

	protected String[] itemUUID;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String[] getItemUUID() {
		return itemUUID;
	}

	public void setItemUUID(String[] itemUUID) {
		this.itemUUID = itemUUID;
	}

}

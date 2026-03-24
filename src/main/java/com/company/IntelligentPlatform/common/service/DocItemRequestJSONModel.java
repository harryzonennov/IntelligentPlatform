package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class DocItemRequestJSONModel extends SEUIComModel {
	
	protected String docItemUUID;
	
	protected String amount;
	
	protected String refUnitUUID;
	
	protected String baseUUID;

	protected int documentType;

	protected String client;

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

	public String getDocItemUUID() {
		return docItemUUID;
	}

	public void setDocItemUUID(String docItemUUID) {
		this.docItemUUID = docItemUUID;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	@Override
	public String getClient() {
		return client;
	}

	@Override
	public void setClient(String client) {
		this.client = client;
	}
}

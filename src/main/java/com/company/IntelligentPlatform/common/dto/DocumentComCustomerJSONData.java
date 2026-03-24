package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.ServiceEntityJSONData;

public class DocumentComCustomerJSONData extends ServiceEntityJSONData {
	
	protected String baseUUID;
	
	protected String uuid;
	
	protected String contactPersonUUID;
	
	protected int customerType;

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getContactPersonUUID() {
		return contactPersonUUID;
	}

	public void setContactPersonUUID(String contactPersonUUID) {
		this.contactPersonUUID = contactPersonUUID;
	}

	public int getCustomerType() {
		return customerType;
	}

	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}

}

package com.company.IntelligentPlatform.finance.dto;

public class FinAccountObjectJSONModel {
	
	protected String uuid;
	
	protected String name;
	
	protected String id;
	
	protected int accountType;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	
}

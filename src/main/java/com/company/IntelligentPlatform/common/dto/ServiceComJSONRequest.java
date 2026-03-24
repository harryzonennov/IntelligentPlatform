package com.company.IntelligentPlatform.common.dto;

public class ServiceComJSONRequest {
	
	protected String userId;
	
	protected String password;
	
	protected String dataPackage;
	
	protected int operationCode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDataPackage() {
		return dataPackage;
	}

	public void setDataPackage(String dataPackage) {
		this.dataPackage = dataPackage;
	}

	public int getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(int operationCode) {
		this.operationCode = operationCode;
	}

}

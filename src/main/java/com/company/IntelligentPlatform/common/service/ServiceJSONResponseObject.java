package com.company.IntelligentPlatform.common.service;

public class ServiceJSONResponseObject {

	protected int errorCode;
	
	protected Object content;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}

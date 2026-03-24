package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.StandardErrorTypeProxy;

public class ServiceDocActiveMessageItemUIModel extends SEUIComModel {
	
	protected int errorType;
	
    protected String errorTypeValue;
	
	protected String messageContent;
	
	public ServiceDocActiveMessageItemUIModel(){
		this.errorType = StandardErrorTypeProxy.MESSAGE_TYPE_NOTIFICATION;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getErrorTypeValue() {
		return errorTypeValue;
	}

	public void setErrorTypeValue(String errorTypeValue) {
		this.errorTypeValue = errorTypeValue;
	}

}

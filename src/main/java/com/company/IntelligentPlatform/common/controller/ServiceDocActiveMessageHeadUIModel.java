package com.company.IntelligentPlatform.common.controller;

import java.util.List;

import com.company.IntelligentPlatform.common.service.StandardErrorTypeProxy;

public class ServiceDocActiveMessageHeadUIModel {

	protected int errorType;

	protected String errorTypeValue;
	
	protected String baseUUID;
	
	protected List<ServiceDocActiveMessageItemUIModel> itemList;	
	
	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public ServiceDocActiveMessageHeadUIModel(){
		this.errorType = StandardErrorTypeProxy.MESSAGE_TYPE_NOTIFICATION;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getErrorTypeValue() {
		return errorTypeValue;
	}

	public void setErrorTypeValue(String errorTypeValue) {
		this.errorTypeValue = errorTypeValue;
	}

	public List<ServiceDocActiveMessageItemUIModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<ServiceDocActiveMessageItemUIModel> itemList) {
		this.itemList = itemList;
	}

}

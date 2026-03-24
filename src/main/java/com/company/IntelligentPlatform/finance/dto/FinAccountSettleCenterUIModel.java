package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class FinAccountSettleCenterUIModel extends SEUIComModel{
	
	protected double allRecordedAmount;
	
	protected double allAmount;
	
	protected double allToRecordAmount;
	
	protected int allDocumentAmount;
	
	protected String accountObjectName;
	
	protected String accountObjectId;
	
	protected String accountObjectTypeValue;
	
	protected int accountObjectType;
	
	protected String accountObjectUUID;
	
	public FinAccountSettleCenterUIModel() {
		allRecordedAmount = 0;
		allAmount = 0;
		allToRecordAmount = 0;
		allDocumentAmount = 0;				
	}	

	public String getAccountObjectId() {
		return accountObjectId;
	}

	public void setAccountObjectId(String accountObjectId) {
		this.accountObjectId = accountObjectId;
	}

	public int getAccountObjectType() {
		return accountObjectType;
	}

	public void setAccountObjectType(int accountObjectType) {
		this.accountObjectType = accountObjectType;
	}

	public String getAccountObjectUUID() {
		return accountObjectUUID;
	}

	public void setAccountObjectUUID(String accountObjectUUID) {
		this.accountObjectUUID = accountObjectUUID;
	}

	public double getAllRecordedAmount() {
		return allRecordedAmount;
	}

	public void setAllRecordedAmount(double allRecordedAmount) {
		this.allRecordedAmount = allRecordedAmount;
	}

	public double getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(double allAmount) {
		this.allAmount = allAmount;
	}

	public double getAllToRecordAmount() {
		return allToRecordAmount;
	}

	public void setAllToRecordAmount(double allToRecordAmount) {
		this.allToRecordAmount = allToRecordAmount;
	}

	public int getAllDocumentAmount() {
		return allDocumentAmount;
	}

	public void setAllDocumentAmount(int allDocumentAmount) {
		this.allDocumentAmount = allDocumentAmount;
	}

	public String getAccountObjectName() {
		return accountObjectName;
	}

	public void setAccountObjectName(String accountObjectName) {
		this.accountObjectName = accountObjectName;
	}

	public String getAccountObjectTypeValue() {
		return accountObjectTypeValue;
	}

	public void setAccountObjectTypeValue(String accountObjectTypeValue) {
		this.accountObjectTypeValue = accountObjectTypeValue;
	}

}

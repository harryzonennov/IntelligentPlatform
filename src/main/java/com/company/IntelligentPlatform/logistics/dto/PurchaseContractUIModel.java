package com.company.IntelligentPlatform.logistics.dto;


import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class PurchaseContractUIModel extends DocumentUIModel {

	protected String currencyCode;
	
	protected String signDate;
	
	protected String requireExecutionDate;

	protected double grossPrice;

	protected double grossPriceDisplay;

	protected String contractDetails;
	
	protected String refFinAccountUUID;
	
	protected String refFinAccountId;

	protected String purchaseBatchNumber;
	
	protected String productionBatchNumber;

	public PurchaseContractUIModel() {
		super();
	}

	public String getSignDate() {
		return this.signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getRequireExecutionDate() {
		return requireExecutionDate;
	}

	public void setRequireExecutionDate(String requireExecutionDate) {
		this.requireExecutionDate = requireExecutionDate;
	}

	public int getPriorityCode() {
		return this.priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public double getGrossPriceDisplay() {
		return grossPriceDisplay;
	}

	public void setGrossPriceDisplay(double grossPriceDisplay) {
		this.grossPriceDisplay = grossPriceDisplay;
	}

	public String getContractDetails() {
		return this.contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRefFinAccountUUID() {
		return refFinAccountUUID;
	}

	public void setRefFinAccountUUID(String refFinAccountUUID) {
		this.refFinAccountUUID = refFinAccountUUID;
	}

	public String getRefFinAccountId() {
		return refFinAccountId;
	}

	public void setRefFinAccountId(String refFinAccountId) {
		this.refFinAccountId = refFinAccountId;
	}

	public String getPurchaseBatchNumber() {
		return purchaseBatchNumber;
	}

	public void setPurchaseBatchNumber(String purchaseBatchNumber) {
		this.purchaseBatchNumber = purchaseBatchNumber;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

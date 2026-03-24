package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class SalesContractUIModel extends DocumentUIModel {

	protected String signDate;
	
	protected String requireExecutionDate;
	
	protected String planExecutionDate;
	
	protected double grossPrice;

	protected double grossPriceDisplay;
	
	protected String contractDetails;
	
	@ISEDropDownResourceMapping(resouceMapping = "SalesContract_contractType", valueFieldName = "contractTypeValue")
	protected int contractType;

	protected String contractTypeValue;

	protected String refPrevOrderUUID;

	protected int refPrevOrderType;

	protected String refPrevOrderId;
	
	protected String currencyCode;

    protected String refFinAccountUUID;
    
    protected String refFinAccountId;
	
	protected String productionBatchNumber;

	public String getSignDate() {
		return signDate;
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

	public String getPlanExecutionDate() {
		return planExecutionDate;
	}

	public void setPlanExecutionDate(String planExecutionDate) {
		this.planExecutionDate = planExecutionDate;
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
		return contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public int getContractType() {
		return contractType;
	}

	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	public String getContractTypeValue() {
		return contractTypeValue;
	}

	public void setContractTypeValue(String contractTypeValue) {
		this.contractTypeValue = contractTypeValue;
	}

	public String getRefPrevOrderUUID() {
		return refPrevOrderUUID;
	}

	public void setRefPrevOrderUUID(String refPrevOrderUUID) {
		this.refPrevOrderUUID = refPrevOrderUUID;
	}

	public int getRefPrevOrderType() {
		return refPrevOrderType;
	}

	public void setRefPrevOrderType(int refPrevOrderType) {
		this.refPrevOrderType = refPrevOrderType;
	}

	public String getRefPrevOrderId() {
		return refPrevOrderId;
	}

	public void setRefPrevOrderId(String refPrevOrderId) {
		this.refPrevOrderId = refPrevOrderId;
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

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}
}
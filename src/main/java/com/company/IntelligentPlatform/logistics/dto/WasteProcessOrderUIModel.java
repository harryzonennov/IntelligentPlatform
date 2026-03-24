package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class WasteProcessOrderUIModel extends DocumentUIModel {

	protected String currencyCode;

	protected double grossPrice;

	protected double grossPriceDisplay;

	@ISEDropDownResourceMapping(resouceMapping = "WasteProcessOrder_processType", valueFieldName = "processTypeValue")
	protected int processType;

	protected String processTypeValue;

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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

	public String getProcessTypeValue() {
		return processTypeValue;
	}

	public void setProcessTypeValue(String processTypeValue) {
		this.processTypeValue = processTypeValue;
	}
}

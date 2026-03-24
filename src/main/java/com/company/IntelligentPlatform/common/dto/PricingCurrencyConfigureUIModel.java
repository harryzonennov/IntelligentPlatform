package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class PricingCurrencyConfigureUIModel extends SEUIComModel {

	protected String currencyCode;

	protected double exchangeRate;
	
	protected boolean activeFlag;

	protected boolean defaultCurrency;
	
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public boolean getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(boolean defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

}

package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class PricingCurrencyConfigure extends ServiceEntityNode{
	
	public static final String NODENAME = IServiceModelConstants.PricingCurrencyConfigure;

	public static final String SENAME = IServiceModelConstants.PricingSetting;
	
	public PricingCurrencyConfigure() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
		this.activeFlag = true;
	}
	
	protected String currencyCode;
	
	protected double exchangeRate;
	
	protected boolean defaultCurrency;
	
	protected boolean activeFlag;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getExchangeRate() {
		return exchangeRate;
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

package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class PricingSetting extends ServiceEntityNode {	
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.PricingSetting;
	
	public PricingSetting() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}
	
	protected boolean multiCurrencyFlag;
	
	protected double defaultTaxRate;
	
	protected String defCurrencyCode;

	public boolean getMultiCurrencyFlag() {
		return multiCurrencyFlag;
	}

	public void setMultiCurrencyFlag(boolean multiCurrencyFlag) {
		this.multiCurrencyFlag = multiCurrencyFlag;
	}

	public double getDefaultTaxRate() {
		return defaultTaxRate;
	}

	public void setDefaultTaxRate(double defaultTaxRate) {
		this.defaultTaxRate = defaultTaxRate;
	}

	public String getDefCurrencyCode() {
		return defCurrencyCode;
	}

	public void setDefCurrencyCode(String defCurrencyCode) {
		this.defCurrencyCode = defCurrencyCode;
	}

}

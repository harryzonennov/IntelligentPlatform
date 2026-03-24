package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class PricingSettingUIModel extends SEUIComModel {


    protected double defaultTaxRate;


    protected boolean multiCurrencyFlag;


    protected String defCurrencyCode;


    public double getDefaultTaxRate() {
        return this.defaultTaxRate;
    }


    public void setDefaultTaxRate(double defaultTaxRate) {
        this.defaultTaxRate = defaultTaxRate;
    }


    public boolean getMultiCurrencyFlag() {
        return this.multiCurrencyFlag;
    }


    public void setMultiCurrencyFlag(boolean multiCurrencyFlag) {
        this.multiCurrencyFlag = multiCurrencyFlag;
    }


    public String getDefCurrencyCode() {
        return this.defCurrencyCode;
    }


    public void setDefCurrencyCode(String defCurrencyCode) {
        this.defCurrencyCode = defCurrencyCode;
    }


}

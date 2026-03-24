package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;

import java.util.List;

/**
 * This model is used for input serial id batch, for one specific template material SKU for several document material items.
 */
public class SerialIdInputModel {

    protected String refTemplateMaterialSKUUUID;

    protected MaterialStockKeepUnit refTemplateMaterialSKU;

    protected double grossAmount;

    protected String grossAmountLabel;

    protected List<String> serialIdList;

    public String getRefTemplateMaterialSKUUUID() {
        return refTemplateMaterialSKUUUID;
    }

    public void setRefTemplateMaterialSKUUUID(String refTemplateMaterialSKUUUID) {
        this.refTemplateMaterialSKUUUID = refTemplateMaterialSKUUUID;
    }

    public List<String> getSerialIdList() {
        return serialIdList;
    }

    public void setSerialIdList(List<String> serialIdList) {
        this.serialIdList = serialIdList;
    }

    public MaterialStockKeepUnit getRefTemplateMaterialSKU() {
        return refTemplateMaterialSKU;
    }

    public void setRefTemplateMaterialSKU(MaterialStockKeepUnit refTemplateMaterialSKU) {
        this.refTemplateMaterialSKU = refTemplateMaterialSKU;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getGrossAmountLabel() {
        return grossAmountLabel;
    }

    public void setGrossAmountLabel(String grossAmountLabel) {
        this.grossAmountLabel = grossAmountLabel;
    }
}

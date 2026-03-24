package com.company.IntelligentPlatform.common.dto;

/**
 * Super Class of Doc Item init model
 */
public class BasicDocItemInitModel {

    protected String baseUUID;

    protected String parentNodeUUID;

    protected String uuid;

    protected String refMaterialSKUUUID;

    protected String refMaterialSKUId;

    protected String refMaterialSKUName;

    protected int supplyType;

    protected double amount;

    protected String refUnitUUID;

    protected String amountLabel;

    public String getBaseUUID() {
        return baseUUID;
    }

    public void setBaseUUID(String baseUUID) {
        this.baseUUID = baseUUID;
    }

    public String getParentNodeUUID() {
        return parentNodeUUID;
    }

    public void setParentNodeUUID(String parentNodeUUID) {
        this.parentNodeUUID = parentNodeUUID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRefMaterialSKUUUID() {
        return refMaterialSKUUUID;
    }

    public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
        this.refMaterialSKUUUID = refMaterialSKUUUID;
    }

    public String getRefMaterialSKUId() {
        return refMaterialSKUId;
    }

    public void setRefMaterialSKUId(String refMaterialSKUId) {
        this.refMaterialSKUId = refMaterialSKUId;
    }

    public String getRefMaterialSKUName() {
        return refMaterialSKUName;
    }

    public void setRefMaterialSKUName(String refMaterialSKUName) {
        this.refMaterialSKUName = refMaterialSKUName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRefUnitUUID() {
        return refUnitUUID;
    }

    public void setRefUnitUUID(String refUnitUUID) {
        this.refUnitUUID = refUnitUUID;
    }

    public String getAmountLabel() {
        return amountLabel;
    }

    public void setAmountLabel(String amountLabel) {
        this.amountLabel = amountLabel;
    }

    public int getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(int supplyType) {
        this.supplyType = supplyType;
    }
}

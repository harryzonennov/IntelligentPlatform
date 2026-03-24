package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class BillOfMaterialOrderUIModel extends DocumentUIModel {

    @ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialOrder_status", valueFieldName = "statusValue")
    protected int status;

    protected String statusValue;

    @ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialOrder_leadTimeCalMode", valueFieldName = "")
    protected int leadTimeCalMode;

    protected String note;

    protected String refMaterialSKUUUID;

    protected String refMaterialSKUId;

    protected String refMaterialSKUName;

    protected String refPackageStandard;

    protected int supplyType;

    protected String supplyTypeValue;

    protected int materialCategory;

    protected int materialCategoryValue;

    protected double amount;

    protected String refUnitUUID;

    protected String refUnitName;

    protected int itemCategory;

    protected String itemCategoryValue;

    protected String amountLabel;

    protected String searchItemId;

    protected String searchItemSKUName;

    protected String searchItemSKUID;

    @ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialItem_viewType", valueFieldName = "")
    protected int itemViewType;

    protected String refRouteOrderUUID;

    protected String refRouteOrderId;

    protected String refRouteOrderName;

    protected String refWocUUID;

    protected String refWocId;

    protected String refWocName;

    protected String refTemplateUUID;

    protected String refTemplateId;

    protected String refTemplateName;

    protected int versionNumber;

    protected int patchNumber;

    protected String genVersion;

    public BillOfMaterialOrderUIModel() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public int getLeadTimeCalMode() {
        return leadTimeCalMode;
    }

    public void setLeadTimeCalMode(int leadTimeCalMode) {
        this.leadTimeCalMode = leadTimeCalMode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getRefUnitName() {
        return refUnitName;
    }

    public void setRefUnitName(String refUnitName) {
        this.refUnitName = refUnitName;
    }

    public int getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(int materialCategory) {
        this.materialCategory = materialCategory;
    }

    public int getMaterialCategoryValue() {
        return materialCategoryValue;
    }

    public void setMaterialCategoryValue(int materialCategoryValue) {
        this.materialCategoryValue = materialCategoryValue;
    }

    public int getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(int itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemCategoryValue() {
        return itemCategoryValue;
    }

    public void setItemCategoryValue(String itemCategoryValue) {
        this.itemCategoryValue = itemCategoryValue;
    }

    public String getAmountLabel() {
        return amountLabel;
    }

    public void setAmountLabel(String amountLabel) {
        this.amountLabel = amountLabel;
    }

    public String getSearchItemId() {
        return searchItemId;
    }

    public void setSearchItemId(String searchItemId) {
        this.searchItemId = searchItemId;
    }

    public String getSearchItemSKUName() {
        return searchItemSKUName;
    }

    public void setSearchItemSKUName(String searchItemSKUName) {
        this.searchItemSKUName = searchItemSKUName;
    }

    public String getSearchItemSKUID() {
        return searchItemSKUID;
    }

    public void setSearchItemSKUID(String searchItemSKUID) {
        this.searchItemSKUID = searchItemSKUID;
    }

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    public String getRefRouteOrderUUID() {
        return refRouteOrderUUID;
    }

    public void setRefRouteOrderUUID(String refRouteOrderUUID) {
        this.refRouteOrderUUID = refRouteOrderUUID;
    }

    public String getRefRouteOrderId() {
        return refRouteOrderId;
    }

    public String getRefPackageStandard() {
        return refPackageStandard;
    }

    public void setRefPackageStandard(String refPackageStandard) {
        this.refPackageStandard = refPackageStandard;
    }

    public int getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(int supplyType) {
        this.supplyType = supplyType;
    }

    public String getSupplyTypeValue() {
        return supplyTypeValue;
    }

    public void setSupplyTypeValue(String supplyTypeValue) {
        this.supplyTypeValue = supplyTypeValue;
    }

    public String getRefRouteOrderName() {
        return refRouteOrderName;
    }

    public void setRefRouteOrderName(String refRouteOrderName) {
        this.refRouteOrderName = refRouteOrderName;
    }

    public void setRefRouteOrderId(String refRouteOrderId) {
        this.refRouteOrderId = refRouteOrderId;
    }

    public String getRefWocUUID() {
        return refWocUUID;
    }

    public void setRefWocUUID(String refWocUUID) {
        this.refWocUUID = refWocUUID;
    }

    public String getRefWocId() {
        return refWocId;
    }

    public void setRefWocId(String refWocId) {
        this.refWocId = refWocId;
    }

    public String getRefWocName() {
        return refWocName;
    }

    public void setRefWocName(String refWocName) {
        this.refWocName = refWocName;
    }

    public String getRefTemplateUUID() {
        return refTemplateUUID;
    }

    public void setRefTemplateUUID(String refTemplateUUID) {
        this.refTemplateUUID = refTemplateUUID;
    }

    public String getRefTemplateId() {
        return refTemplateId;
    }

    public void setRefTemplateId(String refTemplateId) {
        this.refTemplateId = refTemplateId;
    }

    public String getRefTemplateName() {
        return refTemplateName;
    }

    public void setRefTemplateName(String refTemplateName) {
        this.refTemplateName = refTemplateName;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getPatchNumber() {
        return patchNumber;
    }

    public void setPatchNumber(int patchNumber) {
        this.patchNumber = patchNumber;
    }

    public String getGenVersion() {
        return genVersion;
    }

    public void setGenVersion(String genVersion) {
        this.genVersion = genVersion;
    }
}

package com.company.IntelligentPlatform.common.controller;


import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class DocMatItemUIModel extends SEUIComModel {

    protected String parentDocId;

    protected String parentDocName;

    protected int parentDocStatus;

    protected String parentDocStatusValue;

    protected String reservedMatItemUUID;

    protected int reservedDocType;

    protected String reservedDocTypeValue;

    protected String reservedDocId;

    protected String reservedDocName;

    protected int prevDocType;

    protected String prevDocTypeValue;

    protected String prevDocMatItemUUID;

    protected String prevDocId;

    protected String prevDocName;

    protected int prevDocStatus;

    protected int nextDocType;

    protected String nextDocTypeValue;

    protected String nextDocMatItemUUID;

    protected String nextDocId;

    protected String nextDocName;

    protected int nextDocStatus;

    protected int prevProfDocType;

    protected String prevProfDocTypeValue;

    protected String prevProfDocMatItemUUID;

    protected String prevProfDocMatItemArrayUUID;

    protected String prevProfDocId;

    protected String prevProfDocName;

    protected int prevProfDocStatus;

    protected String prevProfDocStatusValue;

    protected int nextProfDocType;

    protected String nextProfDocTypeValue;

    protected String nextProfDocMatItemUUID;

    protected String nextProfDocMatItemArrayUUID;

    protected String nextProfDocId;

    protected String nextProfDocName;

    protected int nextProfDocStatus;

    protected double amount;

    protected String amountLabel;

    protected String refUnitUUID;

    protected String refUnitName;

    protected String refMaterialSKUUUID;

    protected String refMaterialSKUId;

    protected String refMaterialSKUName;

    protected String packageStandard;

    protected String refMaterialTemplateUUID;

    protected int refMaterialTraceMode;

    protected String refMaterialTraceModeValue;

    protected String serialId;

    protected double itemPrice;

    protected double unitPrice;

    protected double itemPriceDisplay;

    protected double unitPriceDisplay;

    protected String productionBatchNumber;

    protected String purchaseBatchNumber;

    protected int materialStatus;

    protected String materialStatusValue;

    protected int homeDocumentType;

    protected String homeDocumentTypeValue;

    protected int itemStatus;

    protected String itemStatusValue;

    // Whether necessary to edit serial id
    protected int editSerialIdFlag = StandardSwitchProxy.SWITCH_OFF;

    // Whether serial id for relative material is empty
    protected int emptySerialIdFlag = StandardSwitchProxy.SWITCH_OFF;

    public String getParentDocId() {
        return parentDocId;
    }

    public void setParentDocId(String parentDocId) {
        this.parentDocId = parentDocId;
    }

    public String getParentDocName() {
        return parentDocName;
    }

    public void setParentDocName(String parentDocName) {
        this.parentDocName = parentDocName;
    }

    public int getParentDocStatus() {
        return parentDocStatus;
    }

    public void setParentDocStatus(int parentDocStatus) {
        this.parentDocStatus = parentDocStatus;
    }

    public String getParentDocStatusValue() {
        return parentDocStatusValue;
    }

    public void setParentDocStatusValue(String parentDocStatusValue) {
        this.parentDocStatusValue = parentDocStatusValue;
    }

    public String getReservedMatItemUUID() {
        return reservedMatItemUUID;
    }

    public void setReservedMatItemUUID(String reservedMatItemUUID) {
        this.reservedMatItemUUID = reservedMatItemUUID;
    }

    public int getReservedDocType() {
        return reservedDocType;
    }

    public void setReservedDocType(int reservedDocType) {
        this.reservedDocType = reservedDocType;
    }

    public String getReservedDocTypeValue() {
        return reservedDocTypeValue;
    }

    public void setReservedDocTypeValue(String reservedDocTypeValue) {
        this.reservedDocTypeValue = reservedDocTypeValue;
    }

    public String getReservedDocId() {
        return reservedDocId;
    }

    public void setReservedDocId(String reservedDocId) {
        this.reservedDocId = reservedDocId;
    }

    public String getReservedDocName() {
        return reservedDocName;
    }

    public void setReservedDocName(String reservedDocName) {
        this.reservedDocName = reservedDocName;
    }

    public int getPrevDocType() {
        return prevDocType;
    }

    public void setPrevDocType(int prevDocType) {
        this.prevDocType = prevDocType;
    }

    public String getPrevDocTypeValue() {
        return prevDocTypeValue;
    }

    public void setPrevDocTypeValue(String prevDocTypeValue) {
        this.prevDocTypeValue = prevDocTypeValue;
    }

    public String getPrevDocMatItemUUID() {
        return prevDocMatItemUUID;
    }

    public void setPrevDocMatItemUUID(String prevDocMatItemUUID) {
        this.prevDocMatItemUUID = prevDocMatItemUUID;
    }

    public String getPrevDocId() {
        return prevDocId;
    }

    public void setPrevDocId(String prevDocId) {
        this.prevDocId = prevDocId;
    }

    public String getPrevDocName() {
        return prevDocName;
    }

    public void setPrevDocName(String prevDocName) {
        this.prevDocName = prevDocName;
    }

    public int getNextDocType() {
        return nextDocType;
    }

    public void setNextDocType(int nextDocType) {
        this.nextDocType = nextDocType;
    }

    public String getNextDocTypeValue() {
        return nextDocTypeValue;
    }

    public void setNextDocTypeValue(String nextDocTypeValue) {
        this.nextDocTypeValue = nextDocTypeValue;
    }

    public String getNextDocMatItemUUID() {
        return nextDocMatItemUUID;
    }

    public void setNextDocMatItemUUID(String nextDocMatItemUUID) {
        this.nextDocMatItemUUID = nextDocMatItemUUID;
    }

    public String getNextDocId() {
        return nextDocId;
    }

    public void setNextDocId(String nextDocId) {
        this.nextDocId = nextDocId;
    }

    public String getNextDocName() {
        return nextDocName;
    }

    public void setNextDocName(String nextDocName) {
        this.nextDocName = nextDocName;
    }

    public int getPrevDocStatus() {
        return prevDocStatus;
    }

    public void setPrevDocStatus(int prevDocStatus) {
        this.prevDocStatus = prevDocStatus;
    }

    public int getNextDocStatus() {
        return nextDocStatus;
    }

    public void setNextDocStatus(int nextDocStatus) {
        this.nextDocStatus = nextDocStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountLabel() {
        return amountLabel;
    }

    public void setAmountLabel(String amountLabel) {
        this.amountLabel = amountLabel;
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

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getRefMaterialTemplateUUID() {
        return refMaterialTemplateUUID;
    }

    public void setRefMaterialTemplateUUID(String refMaterialTemplateUUID) {
        this.refMaterialTemplateUUID = refMaterialTemplateUUID;
    }

    public int getRefMaterialTraceMode() {
        return refMaterialTraceMode;
    }

    public void setRefMaterialTraceMode(int refMaterialTraceMode) {
        this.refMaterialTraceMode = refMaterialTraceMode;
    }

    public String getRefMaterialTraceModeValue() {
        return refMaterialTraceModeValue;
    }

    public void setRefMaterialTraceModeValue(String refMaterialTraceModeValue) {
        this.refMaterialTraceModeValue = refMaterialTraceModeValue;
    }

    public String getPackageStandard() {
        return packageStandard;
    }

    public void setPackageStandard(String packageStandard) {
        this.packageStandard = packageStandard;
    }

    public String getProductionBatchNumber() {
        return productionBatchNumber;
    }

    public void setProductionBatchNumber(String productionBatchNumber) {
        this.productionBatchNumber = productionBatchNumber;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemPriceDisplay() {
        return itemPriceDisplay;
    }

    public void setItemPriceDisplay(final double itemPriceDisplay) {
        this.itemPriceDisplay = itemPriceDisplay;
    }

    public double getUnitPriceDisplay() {
        return unitPriceDisplay;
    }

    public void setUnitPriceDisplay(final double unitPriceDisplay) {
        this.unitPriceDisplay = unitPriceDisplay;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPurchaseBatchNumber() {
        return purchaseBatchNumber;
    }

    public void setPurchaseBatchNumber(String purchaseBatchNumber) {
        this.purchaseBatchNumber = purchaseBatchNumber;
    }

    public int getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(int materialStatus) {
        this.materialStatus = materialStatus;
    }

    public String getMaterialStatusValue() {
        return materialStatusValue;
    }

    public void setMaterialStatusValue(String materialStatusValue) {
        this.materialStatusValue = materialStatusValue;
    }

    public int getHomeDocumentType() {
        return homeDocumentType;
    }

    public void setHomeDocumentType(int homeDocumentType) {
        this.homeDocumentType = homeDocumentType;
    }

    public String getHomeDocumentTypeValue() {
        return homeDocumentTypeValue;
    }

    public void setHomeDocumentTypeValue(String homeDocumentTypeValue) {
        this.homeDocumentTypeValue = homeDocumentTypeValue;
    }

    public int getPrevProfDocType() {
        return prevProfDocType;
    }

    public void setPrevProfDocType(int prevProfDocType) {
        this.prevProfDocType = prevProfDocType;
    }

    public String getPrevProfDocMatItemUUID() {
        return prevProfDocMatItemUUID;
    }

    public void setPrevProfDocMatItemUUID(String prevProfDocMatItemUUID) {
        this.prevProfDocMatItemUUID = prevProfDocMatItemUUID;
    }

    public String getPrevProfDocMatItemArrayUUID() {
        return prevProfDocMatItemArrayUUID;
    }

    public void setPrevProfDocMatItemArrayUUID(String prevProfDocMatItemArrayUUID) {
        this.prevProfDocMatItemArrayUUID = prevProfDocMatItemArrayUUID;
    }

    public String getPrevProfDocId() {
        return prevProfDocId;
    }

    public void setPrevProfDocId(String prevProfDocId) {
        this.prevProfDocId = prevProfDocId;
    }

    public String getPrevProfDocName() {
        return prevProfDocName;
    }

    public void setPrevProfDocName(String prevProfDocName) {
        this.prevProfDocName = prevProfDocName;
    }

    public int getPrevProfDocStatus() {
        return prevProfDocStatus;
    }

    public void setPrevProfDocStatus(int prevProfDocStatus) {
        this.prevProfDocStatus = prevProfDocStatus;
    }

    public String getPrevProfDocStatusValue() {
        return prevProfDocStatusValue;
    }

    public void setPrevProfDocStatusValue(String prevProfDocStatusValue) {
        this.prevProfDocStatusValue = prevProfDocStatusValue;
    }

    public String getPrevProfDocTypeValue() {
        return prevProfDocTypeValue;
    }

    public void setPrevProfDocTypeValue(String prevProfDocTypeValue) {
        this.prevProfDocTypeValue = prevProfDocTypeValue;
    }

    public String getNextProfDocTypeValue() {
        return nextProfDocTypeValue;
    }

    public void setNextProfDocTypeValue(String nextProfDocTypeValue) {
        this.nextProfDocTypeValue = nextProfDocTypeValue;
    }

    public int getNextProfDocType() {
        return nextProfDocType;
    }

    public void setNextProfDocType(int nextProfDocType) {
        this.nextProfDocType = nextProfDocType;
    }

    public String getNextProfDocMatItemUUID() {
        return nextProfDocMatItemUUID;
    }

    public void setNextProfDocMatItemUUID(String nextProfDocMatItemUUID) {
        this.nextProfDocMatItemUUID = nextProfDocMatItemUUID;
    }

    public String getNextProfDocMatItemArrayUUID() {
        return nextProfDocMatItemArrayUUID;
    }

    public void setNextProfDocMatItemArrayUUID(String nextProfDocMatItemArrayUUID) {
        this.nextProfDocMatItemArrayUUID = nextProfDocMatItemArrayUUID;
    }

    public String getNextProfDocId() {
        return nextProfDocId;
    }

    public void setNextProfDocId(String nextProfDocId) {
        this.nextProfDocId = nextProfDocId;
    }

    public String getNextProfDocName() {
        return nextProfDocName;
    }

    public void setNextProfDocName(String nextProfDocName) {
        this.nextProfDocName = nextProfDocName;
    }

    public int getNextProfDocStatus() {
        return nextProfDocStatus;
    }

    public void setNextProfDocStatus(int nextProfDocStatus) {
        this.nextProfDocStatus = nextProfDocStatus;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemStatusValue() {
        return itemStatusValue;
    }

    public void setItemStatusValue(String itemStatusValue) {
        this.itemStatusValue = itemStatusValue;
    }

    public int getEditSerialIdFlag() {
        return editSerialIdFlag;
    }

    public void setEditSerialIdFlag(int editSerialIdFlag) {
        this.editSerialIdFlag = editSerialIdFlag;
    }

    public int getEmptySerialIdFlag() {
        return emptySerialIdFlag;
    }

    public void setEmptySerialIdFlag(int emptySerialIdFlag) {
        this.emptySerialIdFlag = emptySerialIdFlag;
    }
}

package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class SalesContractMaterialItemUIModel extends DocMatItemUIModel {

    protected String requireShippingTime;

    protected String shippingPoint;

    protected String refTechStandard;

    protected String currencyCode;

    protected String requireExecutionDate;

    protected String planExecutionDate;

    protected String signDate;

    protected String documentTags;

    protected String refSettleItemUUID;

    protected String refSettleOrderId;

    protected double settleAmount;

    protected double settlePrice;

    protected String refOutboundItemUUID;

    protected String refFinAccountUUID;

    protected String refOutboundDeliveryId;

    protected double storeAmount;

    protected String refStoreUnitUUID;

    protected String refStoreUnitName;

    protected int storeCheckStatus;

    protected String storeCheckStatusValue;

    protected String deliveryDoneTime;

    protected String deliveryDoneBy;

    protected String deliveryDoneById;

    protected String deliveryDoneByName;

    protected String processDoneTime;

    protected String processDoneBy;

    protected String processDoneById;

    protected String processDoneByName;

    public String getRequireShippingTime() {
        return this.requireShippingTime;
    }

    public void setRequireShippingTime(String requireShippingTime) {
        this.requireShippingTime = requireShippingTime;
    }

    public String getShippingPoint() {
        return this.shippingPoint;
    }

    public void setShippingPoint(String shippingPoint) {
        this.shippingPoint = shippingPoint;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRequireExecutionDate() {
        return requireExecutionDate;
    }

    public void setRequireExecutionDate(String requireExecutionDate) {
        this.requireExecutionDate = requireExecutionDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getRefTechStandard() {
        return refTechStandard;
    }

    public void setRefTechStandard(String refTechStandard) {
        this.refTechStandard = refTechStandard;
    }

    public String getRefSettleItemUUID() {
        return refSettleItemUUID;
    }

    public void setRefSettleItemUUID(String refSettleItemUUID) {
        this.refSettleItemUUID = refSettleItemUUID;
    }

    public String getRefSettleOrderId() {
        return refSettleOrderId;
    }

    public void setRefSettleOrderId(String refSettleOrderId) {
        this.refSettleOrderId = refSettleOrderId;
    }

    public double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(double settleAmount) {
        this.settleAmount = settleAmount;
    }

    public double getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(double settlePrice) {
        this.settlePrice = settlePrice;
    }

    public String getRefOutboundItemUUID() {
        return refOutboundItemUUID;
    }

    public void setRefOutboundItemUUID(String refOutboundItemUUID) {
        this.refOutboundItemUUID = refOutboundItemUUID;
    }

    public String getRefOutboundDeliveryId() {
        return refOutboundDeliveryId;
    }

    public void setRefOutboundDeliveryId(String refOutboundDeliveryId) {
        this.refOutboundDeliveryId = refOutboundDeliveryId;
    }

    public String getDocumentTags() {
        return documentTags;
    }

    public void setDocumentTags(String documentTags) {
        this.documentTags = documentTags;
    }

    public double getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(double storeAmount) {
        this.storeAmount = storeAmount;
    }

    public String getRefStoreUnitUUID() {
        return refStoreUnitUUID;
    }

    public void setRefStoreUnitUUID(String refStoreUnitUUID) {
        this.refStoreUnitUUID = refStoreUnitUUID;
    }

    public String getRefStoreUnitName() {
        return refStoreUnitName;
    }

    public void setRefStoreUnitName(String refStoreUnitName) {
        this.refStoreUnitName = refStoreUnitName;
    }

    public int getStoreCheckStatus() {
        return storeCheckStatus;
    }

    public void setStoreCheckStatus(int storeCheckStatus) {
        this.storeCheckStatus = storeCheckStatus;
    }

    public String getStoreCheckStatusValue() {
        return storeCheckStatusValue;
    }

    public void setStoreCheckStatusValue(String storeCheckStatusValue) {
        this.storeCheckStatusValue = storeCheckStatusValue;
    }

    public String getRefFinAccountUUID() {
        return refFinAccountUUID;
    }

    public void setRefFinAccountUUID(String refFinAccountUUID) {
        this.refFinAccountUUID = refFinAccountUUID;
    }

    public String getDeliveryDoneTime() {
        return deliveryDoneTime;
    }

    public void setDeliveryDoneTime(final String deliveryDoneTime) {
        this.deliveryDoneTime = deliveryDoneTime;
    }

    public String getDeliveryDoneBy() {
        return deliveryDoneBy;
    }

    public void setDeliveryDoneBy(final String deliveryDoneBy) {
        this.deliveryDoneBy = deliveryDoneBy;
    }

    public String getDeliveryDoneById() {
        return deliveryDoneById;
    }

    public void setDeliveryDoneById(final String deliveryDoneById) {
        this.deliveryDoneById = deliveryDoneById;
    }

    public String getDeliveryDoneByName() {
        return deliveryDoneByName;
    }

    public void setDeliveryDoneByName(final String deliveryDoneByName) {
        this.deliveryDoneByName = deliveryDoneByName;
    }

    public String getProcessDoneTime() {
        return processDoneTime;
    }

    public void setProcessDoneTime(final String processDoneTime) {
        this.processDoneTime = processDoneTime;
    }

    public String getProcessDoneBy() {
        return processDoneBy;
    }

    public void setProcessDoneBy(final String processDoneBy) {
        this.processDoneBy = processDoneBy;
    }

    public String getProcessDoneById() {
        return processDoneById;
    }

    public void setProcessDoneById(final String processDoneById) {
        this.processDoneById = processDoneById;
    }

    public String getProcessDoneByName() {
        return processDoneByName;
    }

    public void setProcessDoneByName(final String processDoneByName) {
        this.processDoneByName = processDoneByName;
    }

    public String getPlanExecutionDate() {
        return planExecutionDate;
    }

    public void setPlanExecutionDate(String planExecutionDate) {
        this.planExecutionDate = planExecutionDate;
    }
}
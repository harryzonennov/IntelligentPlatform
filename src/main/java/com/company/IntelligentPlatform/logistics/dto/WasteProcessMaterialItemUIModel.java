package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class WasteProcessMaterialItemUIModel extends DocMatItemUIModel {

    protected int storeCheckStatus;

    protected String storeCheckStatusValue;

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
}

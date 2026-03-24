package com.company.IntelligentPlatform.production.controller;

import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;

public class ProductionDocBatchRequest extends DocumentMatItemBatchGenRequest {

    protected String productionBatchNumber;

    protected String planStartTime;

    public String getProductionBatchNumber() {
        return productionBatchNumber;
    }

    public void setProductionBatchNumber(String productionBatchNumber) {
        this.productionBatchNumber = productionBatchNumber;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }
}

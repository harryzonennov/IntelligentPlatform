package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class SalesForcastMaterialItemUIModel extends DocMatItemUIModel {

    protected String currencyCode;

    protected String planExecutionDate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPlanExecutionDate() {
        return planExecutionDate;
    }

    public void setPlanExecutionDate(String planExecutionDate) {
        this.planExecutionDate = planExecutionDate;
    }
}
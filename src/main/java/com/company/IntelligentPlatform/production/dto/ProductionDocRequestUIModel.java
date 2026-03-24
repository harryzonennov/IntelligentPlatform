package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class ProductionDocRequestUIModel extends DocMatItemUIModel {

    protected String planExecutionDate;

    protected int parentDocType;

    protected String parentDocTypeValue;

    protected String businessKey;

    public String getPlanExecutionDate() {
        return planExecutionDate;
    }

    public void setPlanExecutionDate(String planExecutionDate) {
        this.planExecutionDate = planExecutionDate;
    }

    public int getParentDocType() {
        return parentDocType;
    }

    public void setParentDocType(int parentDocType) {
        this.parentDocType = parentDocType;
    }

    public String getParentDocTypeValue() {
        return parentDocTypeValue;
    }

    public void setParentDocTypeValue(String parentDocTypeValue) {
        this.parentDocTypeValue = parentDocTypeValue;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}

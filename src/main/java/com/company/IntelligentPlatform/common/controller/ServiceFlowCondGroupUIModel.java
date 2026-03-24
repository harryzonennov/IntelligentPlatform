package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class ServiceFlowCondGroupUIModel extends SEUIComModel {

    protected int innerLogicOperator;

    protected String innerLogicOperatorValue;

    protected int externalLogicOperator;

    protected String externalLogicOperatorValue;

    protected String rootDocId;

    protected String rootDocName;

    public int getInnerLogicOperator() {
        return innerLogicOperator;
    }

    public void setInnerLogicOperator(int innerLogicOperator) {
        this.innerLogicOperator = innerLogicOperator;
    }

    public String getInnerLogicOperatorValue() {
        return innerLogicOperatorValue;
    }

    public void setInnerLogicOperatorValue(String innerLogicOperatorValue) {
        this.innerLogicOperatorValue = innerLogicOperatorValue;
    }

    public int getExternalLogicOperator() {
        return externalLogicOperator;
    }

    public void setExternalLogicOperator(int externalLogicOperator) {
        this.externalLogicOperator = externalLogicOperator;
    }

    public String getExternalLogicOperatorValue() {
        return externalLogicOperatorValue;
    }

    public void setExternalLogicOperatorValue(String externalLogicOperatorValue) {
        this.externalLogicOperatorValue = externalLogicOperatorValue;
    }

    public String getRootDocId() {
        return rootDocId;
    }

    public void setRootDocId(String rootDocId) {
        this.rootDocId = rootDocId;
    }

    public String getRootDocName() {
        return rootDocName;
    }

    public void setRootDocName(String rootDocName) {
        this.rootDocName = rootDocName;
    }
}

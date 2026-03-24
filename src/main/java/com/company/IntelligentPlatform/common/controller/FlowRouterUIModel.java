package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class FlowRouterUIModel extends SEUIComModel {

    protected int serialFlag;

    protected String serialFlagValue;

    public int getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(int serialFlag) {
        this.serialFlag = serialFlag;
    }

    public String getSerialFlagValue() {
        return serialFlagValue;
    }

    public void setSerialFlagValue(String serialFlagValue) {
        this.serialFlagValue = serialFlagValue;
    }
}

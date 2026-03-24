package com.company.IntelligentPlatform.common.service;

public class CrossDocActConfigure {

    private int homeDocType;

    private int targetDocType;

    protected int crossDocRelationType;

    private int targetActionCode;

    private int triggerHomeActionCode;

    private int triggerDocActionScenario;

    private int triggerParentMode;

    public CrossDocActConfigure() {
    }

    public CrossDocActConfigure(int homeDocType, int targetDocType,int crossDocRelationType,int triggerHomeActionCode,
                                     int targetActionCode,
                                     int triggerDocActionScenario,
                                     int triggerParentMode) {
        this.homeDocType = homeDocType;
        this.targetDocType = targetDocType;
        this.crossDocRelationType = crossDocRelationType;
        this.triggerHomeActionCode = triggerHomeActionCode;
        this.targetActionCode = targetActionCode;
        this.triggerDocActionScenario = triggerDocActionScenario;
        this.triggerParentMode = triggerParentMode;
    }

    public int getHomeDocType() {
        return homeDocType;
    }

    public void setHomeDocType(int homeDocType) {
        this.homeDocType = homeDocType;
    }

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public int getTargetActionCode() {
        return targetActionCode;
    }

    public void setTargetActionCode(int targetActionCode) {
        this.targetActionCode = targetActionCode;
    }

    public int getTriggerHomeActionCode() {
        return triggerHomeActionCode;
    }

    public void setTriggerHomeActionCode(int triggerHomeActionCode) {
        this.triggerHomeActionCode = triggerHomeActionCode;
    }

    public int getTriggerDocActionScenario() {
        return triggerDocActionScenario;
    }

    public void setTriggerDocActionScenario(int triggerDocActionScenario) {
        this.triggerDocActionScenario = triggerDocActionScenario;
    }

    public int getTriggerParentMode() {
        return triggerParentMode;
    }

    public void setTriggerParentMode(int triggerParentMode) {
        this.triggerParentMode = triggerParentMode;
    }

    public int getCrossDocRelationType() {
        return crossDocRelationType;
    }

    public void setCrossDocRelationType(int crossDocRelationType) {
        this.crossDocRelationType = crossDocRelationType;
    }
}

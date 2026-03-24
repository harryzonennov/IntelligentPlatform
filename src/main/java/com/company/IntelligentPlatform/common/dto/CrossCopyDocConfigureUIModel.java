package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class CrossCopyDocConfigureUIModel extends SEUIComModel {

    protected int sourceDocType;

    protected String sourceDocTypeValue;

    protected int targetDocType;

    protected String targetDocTypeValue;

    protected int cloneAttachmentFlag;

    protected String cloneAttachmentFlagValue;

    protected int triggerSourceActionCode;

    protected String triggerSourceActionCodeValue;

    protected int activeCustomSwitch;

    protected String activeCustomSwitchValue;

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public int getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocType(int sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public String getSourceDocTypeValue() {
        return sourceDocTypeValue;
    }

    public void setSourceDocTypeValue(String sourceDocTypeValue) {
        this.sourceDocTypeValue = sourceDocTypeValue;
    }

    public String getTargetDocTypeValue() {
        return targetDocTypeValue;
    }

    public void setTargetDocTypeValue(String targetDocTypeValue) {
        this.targetDocTypeValue = targetDocTypeValue;
    }

    public int getCloneAttachmentFlag() {
        return cloneAttachmentFlag;
    }

    public void setCloneAttachmentFlag(int cloneAttachmentFlag) {
        this.cloneAttachmentFlag = cloneAttachmentFlag;
    }

    public String getCloneAttachmentFlagValue() {
        return cloneAttachmentFlagValue;
    }

    public void setCloneAttachmentFlagValue(String cloneAttachmentFlagValue) {
        this.cloneAttachmentFlagValue = cloneAttachmentFlagValue;
    }

    public int getTriggerSourceActionCode() {
        return triggerSourceActionCode;
    }

    public void setTriggerSourceActionCode(int triggerSourceActionCode) {
        this.triggerSourceActionCode = triggerSourceActionCode;
    }

    public String getTriggerSourceActionCodeValue() {
        return triggerSourceActionCodeValue;
    }

    public void setTriggerSourceActionCodeValue(String triggerSourceActionCodeValue) {
        this.triggerSourceActionCodeValue = triggerSourceActionCodeValue;
    }

    public int getActiveCustomSwitch() {
        return activeCustomSwitch;
    }

    public void setActiveCustomSwitch(int activeCustomSwitch) {
        this.activeCustomSwitch = activeCustomSwitch;
    }

    public String getActiveCustomSwitchValue() {
        return activeCustomSwitchValue;
    }

    public void setActiveCustomSwitchValue(String activeCustomSwitchValue) {
        this.activeCustomSwitchValue = activeCustomSwitchValue;
    }
}

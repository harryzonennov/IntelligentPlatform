package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class CrossCopyDocConfigure extends ServiceEntityNode {

    public static final String NODENAME = IServiceModelConstants.CrossCopyDocConfigure;

    public static final String SENAME = ServiceDocumentSetting.SENAME;

    protected int targetDocType;

    protected int cloneAttachmentFlag;

    protected int triggerSourceActionCode = 0;

    protected int activeCustomSwitch = StandardSwitchProxy.SWITCH_ON;

    public CrossCopyDocConfigure() {
        super.nodeName = NODENAME;
        super.serviceEntityName = SENAME;
    }

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public int getCloneAttachmentFlag() {
        return cloneAttachmentFlag;
    }

    public void setCloneAttachmentFlag(int cloneAttachmentFlag) {
        this.cloneAttachmentFlag = cloneAttachmentFlag;
    }

    public int getTriggerSourceActionCode() {
        return triggerSourceActionCode;
    }

    public void setTriggerSourceActionCode(int triggerSourceActionCode) {
        this.triggerSourceActionCode = triggerSourceActionCode;
    }

    public int getActiveCustomSwitch() {
        return activeCustomSwitch;
    }

    public void setActiveCustomSwitch(int activeCustomSwitch) {
        this.activeCustomSwitch = activeCustomSwitch;
    }
}

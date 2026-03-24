package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class CrossCopyInvolvePartyUIModel extends SEUIComModel {

    protected int sourceDocType;

    protected String sourceDocTypeValue;

    protected int targetDocType;

    protected String targetDocTypeValue;

    protected int sourcePartyRole;

    protected int targetPartyRole;

    protected int logonPartyFlag;

    protected String sourcePartyRoleValue;

    protected String targetPartyRoleValue;

    protected String logonPartyFlagValue;

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

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public String getTargetDocTypeValue() {
        return targetDocTypeValue;
    }

    public void setTargetDocTypeValue(String targetDocTypeValue) {
        this.targetDocTypeValue = targetDocTypeValue;
    }

    public int getSourcePartyRole() {
        return sourcePartyRole;
    }

    public void setSourcePartyRole(int sourcePartyRole) {
        this.sourcePartyRole = sourcePartyRole;
    }

    public int getTargetPartyRole() {
        return targetPartyRole;
    }

    public void setTargetPartyRole(int targetPartyRole) {
        this.targetPartyRole = targetPartyRole;
    }

    public int getLogonPartyFlag() {
        return logonPartyFlag;
    }

    public void setLogonPartyFlag(int logonPartyFlag) {
        this.logonPartyFlag = logonPartyFlag;
    }

    public String getSourcePartyRoleValue() {
        return sourcePartyRoleValue;
    }

    public void setSourcePartyRoleValue(String sourcePartyRoleValue) {
        this.sourcePartyRoleValue = sourcePartyRoleValue;
    }

    public String getTargetPartyRoleValue() {
        return targetPartyRoleValue;
    }

    public void setTargetPartyRoleValue(String targetPartyRoleValue) {
        this.targetPartyRoleValue = targetPartyRoleValue;
    }

    public String getLogonPartyFlagValue() {
        return logonPartyFlagValue;
    }

    public void setLogonPartyFlagValue(String logonPartyFlagValue) {
        this.logonPartyFlagValue = logonPartyFlagValue;
    }
}

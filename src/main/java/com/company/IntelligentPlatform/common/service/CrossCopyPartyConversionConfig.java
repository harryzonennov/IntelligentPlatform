package com.company.IntelligentPlatform.common.service;

public class CrossCopyPartyConversionConfig {

    protected int sourcePartyRole;

    protected int targetPartyRole;

    protected int logonPartyFlag;

    public CrossCopyPartyConversionConfig() {
    }

    public CrossCopyPartyConversionConfig(int sourcePartyRole,
                                      int targetPartyRole) {
        this.sourcePartyRole = sourcePartyRole;
        this.targetPartyRole = targetPartyRole;
    }

    public CrossCopyPartyConversionConfig(int sourcePartyRole,
                                          int targetPartyRole, int logonPartyFlag) {
        this.sourcePartyRole = sourcePartyRole;
        this.targetPartyRole = targetPartyRole;
        this.logonPartyFlag = logonPartyFlag;
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
}

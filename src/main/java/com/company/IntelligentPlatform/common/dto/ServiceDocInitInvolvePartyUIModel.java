package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocInitInvolvePartyUIModel extends SEUIComModel {

    protected int partyRole;

    protected int logonPartyFlag;

    protected String partyRoleValue;

    protected String logonPartyFlagValue;

    public int getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(int partyRole) {
        this.partyRole = partyRole;
    }

    public int getLogonPartyFlag() {
        return logonPartyFlag;
    }

    public void setLogonPartyFlag(int logonPartyFlag) {
        this.logonPartyFlag = logonPartyFlag;
    }

    public String getPartyRoleValue() {
        return partyRoleValue;
    }

    public void setPartyRoleValue(String partyRoleValue) {
        this.partyRoleValue = partyRoleValue;
    }

    public String getLogonPartyFlagValue() {
        return logonPartyFlagValue;
    }

    public void setLogonPartyFlagValue(String logonPartyFlagValue) {
        this.logonPartyFlagValue = logonPartyFlagValue;
    }
}

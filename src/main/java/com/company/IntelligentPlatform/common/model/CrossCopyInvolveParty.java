package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class CrossCopyInvolveParty extends ServiceEntityNode {

    public static final String NODENAME = IServiceModelConstants.CrossCopyInvolveParty;

    public static final String SENAME = ServiceDocumentSetting.SENAME;

    protected int sourcePartyRole;

    protected int targetPartyRole;

    protected int logonPartyFlag;

    public CrossCopyInvolveParty() {
        super.nodeName = NODENAME;
        super.serviceEntityName = SENAME;
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

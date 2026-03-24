package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceDocInitInvolveParty extends ServiceEntityNode {

    public static final String NODENAME = IServiceModelConstants.ServiceDocInitInvolveParty;

    public static final String SENAME = ServiceDocumentSetting.SENAME;

    protected int partyRole;

    protected int logonPartyFlag;

    public ServiceDocInitInvolveParty() {
        super.nodeName = NODENAME;
        super.serviceEntityName = SENAME;
    }

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
}

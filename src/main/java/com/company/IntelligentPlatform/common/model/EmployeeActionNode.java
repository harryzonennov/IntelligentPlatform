package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class EmployeeActionNode extends DocActionNode {

    public static final String NODENAME = IServiceModelConstants.EmployeeActionNode;

    public static final String SENAME = IServiceModelConstants.Employee;

    public static final int DOC_ACTION_ACTIVE = SystemDefDocActionCodeProxy.DOC_ACTION_ACTIVE;

    public static final int DOC_ACTION_REINIT = SystemDefDocActionCodeProxy.DOC_ACTION_REINIT;

    public static final int DOC_ACTION_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE;

    public static final int DOC_ACTION_REJECT_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE;

    public static final int DOC_ACTION_SUBMIT = SystemDefDocActionCodeProxy.DOC_ACTION_SUBMIT;

    public static final int DOC_ACTION_REVOKE_SUBMIT = SystemDefDocActionCodeProxy.DOC_ACTION_REVOKE_SUBMIT;

    public static final int DOC_ACTION_ARCHIVE = SystemDefDocActionCodeProxy.DOC_ACTION_ARCHIVE;

    public static final String NODEINST_ACTION_ACTIVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_ACTIVE;

    public static final String NODEINST_ACTION_APPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE;

    public static final String NODEINST_ACTION_REJECT_APPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE;

    public static final String NODEINST_ACTION_SUBMIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_SUBMIT;

    public static final String NODEINST_ACTION_REVOKE_SUBMIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_REVOKE_SUBMIT;

    public static final String NODEINST_ACTION_ARCHIVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_ARCHIVE;

    public static final String NODEINST_ACTION_REINIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_REINIT;

    public EmployeeActionNode() {
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
    }

}

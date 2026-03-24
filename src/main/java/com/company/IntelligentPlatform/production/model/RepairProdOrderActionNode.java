package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class RepairProdOrderActionNode extends ProductionOrderActionNode{


    public final static String NODENAME = IServiceModelConstants.RepairProdOrderActionNode;

    public final static String SENAME = RepairProdOrder.SENAME;

    public static final int DOC_ACTION_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE;

    public static final int DOC_ACTION_REJECT_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE;

    public static final int DOC_ACTION_COUNTAPPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_COUNTAPPROVE;

    public static final int DOC_ACTION_INPRODUCTION = SystemDefDocActionCodeProxy.DOC_ACTION_INPROCESS;

    public static final int DOC_ACTION_FINISHED = ProductionOrder.STATUS_FINISHED;

    public static final int DOC_ACTION_PROCESS_DONE = SystemDefDocActionCodeProxy.DOC_ACTION_PROCESS_DONE;

    public static final int DOC_ACTION_SUBMIT = SystemDefDocActionCodeProxy.DOC_ACTION_SUBMIT;

    public static final int DOC_ACTION_REVOKE_SUBMIT = SystemDefDocActionCodeProxy.DOC_ACTION_REVOKE_SUBMIT;

    public static final int DOC_ACTION_ARCHIVE = SystemDefDocActionCodeProxy.DOC_ACTION_ARCHIVE;

    public static final int DOC_ACTION_CANCEL = SystemDefDocActionCodeProxy.DOC_ACTION_CANCEL;

    public static final String NODEINST_ACTION_APPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE;

    public static final String NODEINST_ACTION_COUNTAPPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE;

    public static final String NODEINST_ACTION_INPRODUCTION = "inProductionBy";

    public static final String NODEINST_ACTION_FINISHED = "finishedBy";

    public static final String NODEINST_ACTION_SUBMIT = SystemDefDocActionCodeProxy.NODEINST_ACTION_SUBMIT;

    public static final String NODEINST_ACTION_REVOKE_SUBMIT =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_REVOKE_SUBMIT;

    public static final String NODEINST_ACTION_REJECT_APPROVE =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE;

    public static final String NODEINST_ACTION_ARCHIVE =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_ARCHIVE;

    public static final String NODEINST_ACTION_CANCEL =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_CANCEL;

    public RepairProdOrderActionNode() {
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
    }
}

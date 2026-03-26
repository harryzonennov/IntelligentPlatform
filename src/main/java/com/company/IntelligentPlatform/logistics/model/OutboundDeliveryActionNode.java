package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.DocActionNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class OutboundDeliveryActionNode extends DocActionNode {

    public static final String NODENAME = IServiceModelConstants.OutboundDeliveryActionNode;

    public static final String SENAME = IServiceModelConstants.OutboundDelivery;

    public static final int DOC_ACTION_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE;

    public static final int DOC_ACTION_REJECT_APPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_REJECT_APPROVE;

    public static final int DOC_ACTION_COUNTAPPROVE = SystemDefDocActionCodeProxy.DOC_ACTION_COUNTAPPROVE;

    public static final int DOC_ACTION_RECORD_DONE = SystemDefDocActionCodeProxy.DOC_ACTION_DELIVERY_DONE;

    public static final int DOC_ACTION_ARCHIVE = SystemDefDocActionCodeProxy.DOC_ACTION_ARCHIVE;

    public static final int DOC_ACTION_CANCEL = SystemDefDocActionCodeProxy.DOC_ACTION_CANCEL;

    public static final String NODEINST_ACTION_APPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_APPROVE;

    public static final String NODEINST_ACTION_COUNTAPPROVE = SystemDefDocActionCodeProxy.NODEINST_ACTION_COUNTAPPROVE;

    public static final String NODEINST_ACTION_DELIVERY_DONE = SystemDefDocActionCodeProxy.NODEINST_ACTION_DELIVERY_DONE;

    public static final String NODEINST_ACTION_PROCESS_DONE = SystemDefDocActionCodeProxy.NODEINST_ACTION_PROCESS_DONE;

    public static final String NODEINST_ACTION_REJECT_APPROVE =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_REJECT_APPROVE;

    public static final String NODEINST_ACTION_ARCHIVE =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_ARCHIVE;

    public static final String NODEINST_ACTION_CANCEL =
            SystemDefDocActionCodeProxy.NODEINST_ACTION_CANCEL;

    public OutboundDeliveryActionNode() {
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
    }

}

package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.DocActionNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class QualityInsOrderActionNode extends DocActionNode {

    public static final String NODENAME = IServiceModelConstants.QualityInsOrderActionNode;

    public static final String SENAME = IServiceModelConstants.QualityInspectOrder;

    public static final int DOC_ACTION_PROCESS_DONE = SystemDefDocActionCodeProxy.DOC_ACTION_PROCESS_DONE;

    public static final int DOC_ACTION_DELIVERY_DONE = SystemDefDocActionCodeProxy.DOC_ACTION_DELIVERY_DONE;

    public static final int DOC_ACTION_START_TEST = SystemDefDocActionCodeProxy.DOC_ACTION_INPROCESS;

    public static final int DOC_ACTION_TESTDONE = QualityInspectOrder.STATUS_TESTDONE;

    public static final String NODEINST_ACTION_STRATTEST = "startTest";

    public static final String NODEINST_ACTION_TEST_DONE = "testDoneBy";

    public static final String NODEINST_ACTION_PROCESS_DONE = SystemDefDocActionCodeProxy.NODEINST_ACTION_PROCESS_DONE;

    public static final String NODEINST_ACTION_DELIVERY_DONE = SystemDefDocActionCodeProxy.NODEINST_ACTION_DELIVERY_DONE;

    public QualityInsOrderActionNode() {
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
    }

}

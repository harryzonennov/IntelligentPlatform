package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.service.SystemDefDocActionCodeProxy;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocActionNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "QualityInsOrderActionNode", catalog = "logistics")
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

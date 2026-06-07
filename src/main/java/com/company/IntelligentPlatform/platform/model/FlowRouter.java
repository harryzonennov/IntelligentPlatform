package com.company.IntelligentPlatform.platform.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.service.SystemSerialParallelProxy;
import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
@Entity
@Table(name = "FlowRouter", catalog = "platform")
public class FlowRouter extends ServiceEntityNode {

    public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

    public static final String SENAME = IServiceModelConstants.FlowRouter;

    protected int serialFlag;

    public FlowRouter() {
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
        this.serialFlag = SystemSerialParallelProxy.OP_SERIAL;
    }

    public int getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(int serialFlag) {
        this.serialFlag = serialFlag;
    }
}

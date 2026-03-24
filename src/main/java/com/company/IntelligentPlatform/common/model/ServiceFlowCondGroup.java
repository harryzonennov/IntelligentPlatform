package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceFlowCondGroup extends ServiceEntityNode{

    public static final String NODENAME = IServiceModelConstants.ServiceFlowCondGroup;

    public static final String SENAME = IServiceModelConstants.ServiceFlowModel;

    public static final String DEF_ID = "default";

    public ServiceFlowCondGroup(){
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
        this.innerLogicOperator = StandardLogicOperatorProxy.OPERATOR_AND;
        this.externalLogicOperator = StandardLogicOperatorProxy.OPERATOR_AND;
    }

    public int innerLogicOperator;

    public int externalLogicOperator;

    public int getInnerLogicOperator() {
        return innerLogicOperator;
    }

    public void setInnerLogicOperator(int innerLogicOperator) {
        this.innerLogicOperator = innerLogicOperator;
    }

    public int getExternalLogicOperator() {
        return externalLogicOperator;
    }

    public void setExternalLogicOperator(int externalLogicOperator) {
        this.externalLogicOperator = externalLogicOperator;
    }
}

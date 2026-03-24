package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceFlowModel extends ServiceEntityNode{

    public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

    public static final String SENAME = IServiceModelConstants.ServiceFlowModel;

    public ServiceFlowModel(){
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
        this.actionCode = SystemDefDocActionCodeProxy.DOC_ACTION_APPROVE;
    }

    protected int actionCode;

    protected String serviceUIModelId;

    protected int documentType;

    protected String refRouterUUID;

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getServiceUIModelId() {
        return serviceUIModelId;
    }

    public void setServiceUIModelId(String serviceUIModelId) {
        this.serviceUIModelId = serviceUIModelId;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getRefRouterUUID() {
        return refRouterUUID;
    }

    public void setRefRouterUUID(String refRouterUUID) {
        this.refRouterUUID = refRouterUUID;
    }
}

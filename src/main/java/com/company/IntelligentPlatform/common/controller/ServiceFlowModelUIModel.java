package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class ServiceFlowModelUIModel extends SEUIComModel {

    protected int actionCode;

    protected String actionCodeValue;

    protected int documentType;

    protected String documentTypeValue;

    protected String refRouterUUID;

    protected String refRouterId;

    protected String refRouterName;

    protected String serviceUIModelId;

    protected String resOrgUUID;

    protected String resEmployeeUUID;

    protected String lastUpdateBy;

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionCodeValue() {
        return actionCodeValue;
    }

    public void setActionCodeValue(String actionCodeValue) {
        this.actionCodeValue = actionCodeValue;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeValue() {
        return documentTypeValue;
    }

    public void setDocumentTypeValue(String documentTypeValue) {
        this.documentTypeValue = documentTypeValue;
    }

    public String getRefRouterUUID() {
        return refRouterUUID;
    }

    public void setRefRouterUUID(String refRouterUUID) {
        this.refRouterUUID = refRouterUUID;
    }

    public String getRefRouterId() {
        return refRouterId;
    }

    public void setRefRouterId(String refRouterId) {
        this.refRouterId = refRouterId;
    }

    public String getRefRouterName() {
        return refRouterName;
    }

    public void setRefRouterName(String refRouterName) {
        this.refRouterName = refRouterName;
    }

    public String getServiceUIModelId() {
        return serviceUIModelId;
    }

    public void setServiceUIModelId(String serviceUIModelId) {
        this.serviceUIModelId = serviceUIModelId;
    }

    public String getResOrgUUID() {
        return resOrgUUID;
    }

    public void setResOrgUUID(String resOrgUUID) {
        this.resOrgUUID = resOrgUUID;
    }

    public String getResEmployeeUUID() {
        return resEmployeeUUID;
    }

    public void setResEmployeeUUID(String resEmployeeUUID) {
        this.resEmployeeUUID = resEmployeeUUID;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}

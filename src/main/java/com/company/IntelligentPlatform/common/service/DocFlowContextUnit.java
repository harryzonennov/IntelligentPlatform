package com.company.IntelligentPlatform.common.service;

public class DocFlowContextUnit {

    protected String refUUID;

    protected int documentType;

    public DocFlowContextUnit() {
    }

    public DocFlowContextUnit(String refUUID, int documentType) {
        this.refUUID = refUUID;
        this.documentType = documentType;
    }

    public String getRefUUID() {
        return refUUID;
    }

    public void setRefUUID(String refUUID) {
        this.refUUID = refUUID;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }
}

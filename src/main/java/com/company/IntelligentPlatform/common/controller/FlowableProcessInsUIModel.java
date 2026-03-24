package com.company.IntelligentPlatform.common.controller;

public class FlowableProcessInsUIModel {

    protected String processInsId;

    protected String processInsName;

    protected String processDefId;

    protected String processDefName;

    protected String refDocumentUUID;

    protected int documentType;

    protected String documentTypeValue;

    protected String refDocumentId;

    protected String refDocumentName;

    public FlowableProcessInsUIModel() {
    }

    public FlowableProcessInsUIModel(String processInsId, String processInsName, String processDefId,
                                     String processDefName, String refDocumentUUID, int documentType,
                                     String documentTypeValue, String refDocumentId, String refDocumentName) {
        this.processInsId = processInsId;
        this.processInsName = processInsName;
        this.processDefId = processDefId;
        this.processDefName = processDefName;
        this.refDocumentUUID = refDocumentUUID;
        this.documentType = documentType;
        this.documentTypeValue = documentTypeValue;
        this.refDocumentId = refDocumentId;
        this.refDocumentName = refDocumentName;
    }

    public String getProcessInsId() {
        return processInsId;
    }

    public void setProcessInsId(String processInsId) {
        this.processInsId = processInsId;
    }

    public String getProcessInsName() {
        return processInsName;
    }

    public void setProcessInsName(String processInsName) {
        this.processInsName = processInsName;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public String getProcessDefName() {
        return processDefName;
    }

    public void setProcessDefName(String processDefName) {
        this.processDefName = processDefName;
    }

    public String getRefDocumentUUID() {
        return refDocumentUUID;
    }

    public void setRefDocumentUUID(String refDocumentUUID) {
        this.refDocumentUUID = refDocumentUUID;
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

    public String getRefDocumentId() {
        return refDocumentId;
    }

    public void setRefDocumentId(String refDocumentId) {
        this.refDocumentId = refDocumentId;
    }

    public String getRefDocumentName() {
        return refDocumentName;
    }

    public void setRefDocumentName(String refDocumentName) {
        this.refDocumentName = refDocumentName;
    }
}

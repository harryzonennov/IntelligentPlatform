package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.dto.DocFlowNodeSearchModel;

public class DocFlowNodeSearchModel extends SEUIComModel {

    protected int docFlowDirection;

    protected int targetDocType;

    @BSearchFieldConfig(fieldName = "uuid")
    protected String targetDocMatItemUUID;

    @BSearchFieldConfig(fieldName = "name")
    protected String targetDocName;

    @BSearchFieldConfig(fieldName = "id")
    protected String targetDocId;

    @BSearchFieldConfig(fieldName = "status")
    protected int targetDocStatus;

    public int getDocFlowDirection() {
        return docFlowDirection;
    }

    public void setDocFlowDirection(int docFlowDirection) {
        this.docFlowDirection = docFlowDirection;
    }

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public String getTargetDocMatItemUUID() {
        return targetDocMatItemUUID;
    }

    public void setTargetDocMatItemUUID(String targetDocMatItemUUID) {
        this.targetDocMatItemUUID = targetDocMatItemUUID;
    }

    public String getTargetDocName() {
        return targetDocName;
    }

    public void setTargetDocName(String targetDocName) {
        this.targetDocName = targetDocName;
    }

    public String getTargetDocId() {
        return targetDocId;
    }

    public void setTargetDocId(String targetDocId) {
        this.targetDocId = targetDocId;
    }

    public int getTargetDocStatus() {
        return targetDocStatus;
    }

    public void setTargetDocStatus(int targetDocStatus) {
        this.targetDocStatus = targetDocStatus;
    }
}

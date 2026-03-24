package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;

public class RepairProdOrderInitModel extends DocumentMatItemBatchGenRequest {

    protected String refMaterialTemplateUUID;

    protected String planStartTime;

    protected String planStartPrepareTime;

    protected String planEndTime;

    public String getRefMaterialTemplateUUID() {
        return refMaterialTemplateUUID;
    }

    public void setRefMaterialTemplateUUID(String refMaterialTemplateUUID) {
        this.refMaterialTemplateUUID = refMaterialTemplateUUID;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanStartPrepareTime() {
        return planStartPrepareTime;
    }

    public void setPlanStartPrepareTime(String planStartPrepareTime) {
        this.planStartPrepareTime = planStartPrepareTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }
}

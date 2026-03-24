package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;

public class WasteProcessOrderInitModel extends DocumentMatItemBatchGenRequest {

    protected int processType;

    public int getProcessType() {
        return processType;
    }

    public void setProcessType(int processType) {
        this.processType = processType;
    }
}

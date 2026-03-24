package com.company.IntelligentPlatform.logistics.service;

import java.util.List;

public class SerialIdInputModel {

    protected String refTemplateSKUUUID;

    protected List<String> serialId;

    public String getRefTemplateSKUUUID() {
        return refTemplateSKUUUID;
    }

    public void setRefTemplateSKUUUID(String refTemplateSKUUUID) {
        this.refTemplateSKUUUID = refTemplateSKUUUID;
    }

    public List<String> getSerialId() {
        return serialId;
    }

    public void setSerialId(List<String> serialId) {
        this.serialId = serialId;
    }
}

package com.company.IntelligentPlatform.common.service;

import java.util.List;

/**
 * This model is used for input serial id batch, for one document root node, which contains multiple template material SKU.
 */
public class SerialIdInputBatchModel {

    protected String baseUUID;

    protected List<SerialIdInputModel> serialIdInputModelList;

    public String getBaseUUID() {
        return baseUUID;
    }

    public void setBaseUUID(String baseUUID) {
        this.baseUUID = baseUUID;
    }

    public List<SerialIdInputModel> getSerialIdInputModelList() {
        return serialIdInputModelList;
    }

    public void setSerialIdInputModelList(List<SerialIdInputModel> serialIdInputModelList) {
        this.serialIdInputModelList = serialIdInputModelList;
    }
}

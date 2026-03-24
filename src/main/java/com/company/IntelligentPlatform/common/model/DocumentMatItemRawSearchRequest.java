package com.company.IntelligentPlatform.common.model;

import java.util.List;
import java.util.Map;

/**
 * JSON Request body for Document material item list batch generation.
 */
public class DocumentMatItemRawSearchRequest {

    protected String baseUUID;

    protected String parentNodeUUID;

    protected List<String> uuidList;

    protected Map<String, Object> requestMap;

    protected String client;

    protected DocumentMatItemBatchGenRequest genRequest;

    public String getBaseUUID() {
        return baseUUID;
    }

    public void setBaseUUID(String baseUUID) {
        this.baseUUID = baseUUID;
    }

    public String getParentNodeUUID() {
        return parentNodeUUID;
    }

    public void setParentNodeUUID(String parentNodeUUID) {
        this.parentNodeUUID = parentNodeUUID;
    }

    public List<String> getUuidList() {
        return uuidList;
    }

    public void setUuidList(List<String> uuidList) {
        this.uuidList = uuidList;
    }

    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap) {
        this.requestMap = requestMap;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public DocumentMatItemBatchGenRequest getGenRequest() {
        return genRequest;
    }

    public void setGenRequest(DocumentMatItemBatchGenRequest genRequest) {
        this.genRequest = genRequest;
    }
}

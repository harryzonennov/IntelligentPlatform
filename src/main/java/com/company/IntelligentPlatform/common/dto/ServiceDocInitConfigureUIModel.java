package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocInitConfigureUIModel extends SEUIComModel {

    protected String configMeta;

    protected String refServiceEntityName;

    protected String refNodeName;

    protected String nodeInstId;

    public String getConfigMeta() {
        return configMeta;
    }

    public void setConfigMeta(String configMeta) {
        this.configMeta = configMeta;
    }

    public String getRefServiceEntityName() {
        return refServiceEntityName;
    }

    public void setRefServiceEntityName(String refServiceEntityName) {
        this.refServiceEntityName = refServiceEntityName;
    }

    public String getRefNodeName() {
        return refNodeName;
    }

    public void setRefNodeName(String refNodeName) {
        this.refNodeName = refNodeName;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }
}

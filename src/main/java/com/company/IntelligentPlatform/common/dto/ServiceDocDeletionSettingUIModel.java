package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceDocDeletionSettingUIModel extends SEUIComModel {

    protected String refServiceEntityName;

    protected String refNodeName;

    protected String nodeInstId;

    @ISEDropDownResourceMapping(resouceMapping = "ServiceDocDeletionSetting_deletionStrategy", valueFieldName = "")
    protected int deletionStrategy;

    protected String deletionStrategyValue;

    protected String admDeleteStatus;

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

    public int getDeletionStrategy() {
        return deletionStrategy;
    }

    public void setDeletionStrategy(int deletionStrategy) {
        this.deletionStrategy = deletionStrategy;
    }

    public String getDeletionStrategyValue() {
        return deletionStrategyValue;
    }

    public void setDeletionStrategyValue(String deletionStrategyValue) {
        this.deletionStrategyValue = deletionStrategyValue;
    }

    public String getAdmDeleteStatus() {
        return admDeleteStatus;
    }

    public void setAdmDeleteStatus(String admDeleteStatus) {
        this.admDeleteStatus = admDeleteStatus;
    }
}

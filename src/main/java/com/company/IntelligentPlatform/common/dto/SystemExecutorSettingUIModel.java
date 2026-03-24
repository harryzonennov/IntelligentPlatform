package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class SystemExecutorSettingUIModel extends SEUIComModel {

    protected String refAOUUID;
    
    protected String refActionCode;
    
    protected int executeBatchNumber;
    
    @ISEDropDownResourceMapping(resouceMapping = "SystemExecutorSetting_executionType", valueFieldName =
            "executionTypeValue")
    protected int executionType;

    protected String executionTypeValue;
    
    protected String proxyName;

    protected String refPreExecuteSettingUUID;
    
    protected String authorizationObjectId;
    
    protected String authorizationObjectName;

    protected String refExecutorId;

    protected String refExecutorName;

    protected String refExecutorDescription;

    protected String requestContent;
    public String getRefAOUUID() {
        return this.refAOUUID;
    }

    public void setRefAOUUID(String refAOUUID) {
        this.refAOUUID = refAOUUID;
    }

    public String getRefActionCode() {
        return this.refActionCode;
    }

    public void setRefActionCode(String refActionCode) {
        this.refActionCode = refActionCode;
    }

    public int getExecuteBatchNumber() {
        return this.executeBatchNumber;
    }

    public void setExecuteBatchNumber(int executeBatchNumber) {
        this.executeBatchNumber = executeBatchNumber;
    }

    public int getExecutionType() {
        return this.executionType;
    }

    public void setExecutionType(int executionType) {
        this.executionType = executionType;
    }

    public String getExecutionTypeValue() {
        return executionTypeValue;
    }

    public void setExecutionTypeValue(String executionTypeValue) {
        this.executionTypeValue = executionTypeValue;
    }

    public String getProxyName() {
        return this.proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public String getRefPreExecuteSettingUUID() {
        return this.refPreExecuteSettingUUID;
    }

    public void setRefPreExecuteSettingUUID(String refPreExecuteSettingUUID) {
        this.refPreExecuteSettingUUID = refPreExecuteSettingUUID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAuthorizationObjectId() {
        return this.authorizationObjectId;
    }

    public void setAuthorizationObjectId(String authorizationObjectId) {
        this.authorizationObjectId = authorizationObjectId;
    }

    public String getAuthorizationObjectName() {
        return this.authorizationObjectName;
    }

    public void setAuthorizationObjectName(String authorizationObjectName) {
        this.authorizationObjectName = authorizationObjectName;
    }

    public String getRefExecutorId() {
        return refExecutorId;
    }

    public void setRefExecutorId(String refExecutorId) {
        this.refExecutorId = refExecutorId;
    }

    public String getRefExecutorName() {
        return refExecutorName;
    }

    public void setRefExecutorName(String refExecutorName) {
        this.refExecutorName = refExecutorName;
    }

    public String getRefExecutorDescription() {
        return refExecutorDescription;
    }

    public void setRefExecutorDescription(String refExecutorDescription) {
        this.refExecutorDescription = refExecutorDescription;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }
}

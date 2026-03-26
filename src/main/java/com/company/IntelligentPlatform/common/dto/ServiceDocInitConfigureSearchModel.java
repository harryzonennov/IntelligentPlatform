package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocInitConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

public class ServiceDocInitConfigureSearchModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "id", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME,
         nodeInstID = ServiceDocumentSetting.SENAME)
    protected String baseRefServiceEntityName;

    @BSearchFieldConfig(fieldName = "name", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME,
            nodeInstID = ServiceDocumentSetting.SENAME)
    protected String baseRefNodeName;

    @BSearchFieldConfig(fieldName = "refServiceEntityName", nodeName = ServiceDocInitConfigure.NODENAME, seName = ServiceDocInitConfigure.SENAME,
            nodeInstID = ServiceDocInitConfigure.SENAME)
    protected String refServiceEntityName;

    @BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceDocInitConfigure.NODENAME, seName = ServiceDocInitConfigure.SENAME,
            nodeInstID = ServiceDocInitConfigure.SENAME)
    protected String refNodeName;

    @BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceDocInitConfigure.NODENAME, seName = ServiceDocInitConfigure.SENAME,
            nodeInstID = ServiceDocInitConfigure.SENAME)
    protected String uuid;

    @BSearchFieldConfig(fieldName = "id", nodeName = ServiceDocInitConfigure.NODENAME, seName = ServiceDocInitConfigure.SENAME,
            nodeInstID = ServiceDocInitConfigure.SENAME)
    protected String initConfigureId;

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

    public String getBaseRefServiceEntityName() {
        return baseRefServiceEntityName;
    }

    public void setBaseRefServiceEntityName(String baseRefServiceEntityName) {
        this.baseRefServiceEntityName = baseRefServiceEntityName;
    }

    public String getBaseRefNodeName() {
        return baseRefNodeName;
    }

    public void setBaseRefNodeName(String baseRefNodeName) {
        this.baseRefNodeName = baseRefNodeName;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInitConfigureId() {
        return initConfigureId;
    }

    public void setInitConfigureId(String initConfigureId) {
        this.initConfigureId = initConfigureId;
    }
}

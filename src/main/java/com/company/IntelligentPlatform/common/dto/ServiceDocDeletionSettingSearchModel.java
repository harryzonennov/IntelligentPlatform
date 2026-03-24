package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocDeletionSetting;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

public class ServiceDocDeletionSettingSearchModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "refServiceEntityName", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME,
            nodeInstID = ServiceDocumentSetting.SENAME)
    protected String baseRefServiceEntityName;

    @BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME,
            nodeInstID = ServiceDocumentSetting.SENAME)
    protected String baseRefNodeName;


    @BSearchFieldConfig(fieldName = "refServiceEntityName", nodeName = ServiceDocDeletionSetting.NODENAME, seName = ServiceDocDeletionSetting.SENAME,
            nodeInstID = ServiceDocDeletionSetting.SENAME)
    protected String refServiceEntityName;

    @BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceDocDeletionSetting.NODENAME, seName = ServiceDocDeletionSetting.SENAME,
            nodeInstID = ServiceDocDeletionSetting.SENAME)
    protected String refNodeName;

    @BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceDocDeletionSetting.NODENAME, seName = ServiceDocDeletionSetting.SENAME,
            nodeInstID = ServiceDocDeletionSetting.SENAME)
    protected String uuid;

    @BSearchFieldConfig(fieldName = "id", nodeName = ServiceDocDeletionSetting.NODENAME, seName = ServiceDocDeletionSetting.SENAME,
            nodeInstID = ServiceDocDeletionSetting.SENAME)
    protected String deleteSettingId;



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

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeleteSettingId() {
        return deleteSettingId;
    }

    public void setDeleteSettingId(String deleteSettingId) {
        this.deleteSettingId = deleteSettingId;
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
}

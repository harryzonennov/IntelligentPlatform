package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

@Component
public class ServiceDocumentSettingSearchModel extends SEUIComModel {


    @BSearchFieldConfig(fieldName = "id", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME, nodeInstID = ServiceDocumentSetting.SENAME, showOnUI = true)
    protected String id;

    @BSearchFieldConfig(fieldName = "refServiceEntityName", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME, nodeInstID = ServiceDocumentSetting.SENAME, showOnUI = true)
    protected String refServiceEntityName;

    @BSearchFieldConfig(fieldName = "refNodeName", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME, nodeInstID = ServiceDocumentSetting.SENAME, showOnUI = true)
    protected String refNodeName;

    @BSearchFieldConfig(fieldName = "name", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME, nodeInstID = ServiceDocumentSetting.SENAME, showOnUI = true)
    protected String name;


    @BSearchFieldConfig(fieldName = "documentType", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME, nodeInstID = ServiceDocumentSetting.SENAME, showOnUI = true)
    protected int documentType;


    @BSearchFieldConfig(fieldName = "uuid", nodeName = ServiceDocumentSetting.NODENAME, seName = ServiceDocumentSetting.SENAME, nodeInstID = ServiceDocumentSetting.SENAME, showOnUI = true)
    protected String uuid;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}

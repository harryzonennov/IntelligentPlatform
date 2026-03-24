package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.dto.AccountSearchSubModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondField;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;


public class ServiceFlowModelSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = ServiceFlowModel.SENAME)
    protected ServiceDocSearchHeaderModel headerModel;

    @BSearchGroupConfig(groupInstId = ServiceFlowModel.SENAME)
    protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

    @BSearchFieldConfig(fieldName = "actionCode", nodeName = ServiceFlowModel.NODENAME, seName =
            ServiceFlowModel.SENAME, nodeInstID = ServiceFlowModel.SENAME)
    protected int actionCode;

    @BSearchFieldConfig(fieldName = "refRouterUUID", nodeName = ServiceFlowModel.NODENAME, seName =
            ServiceFlowModel.SENAME, nodeInstID = ServiceFlowModel.SENAME)
    protected String refRouterUUID;


    @BSearchFieldConfig(fieldName = "serviceUIModelId", nodeName = ServiceFlowModel.NODENAME, seName =
            ServiceFlowModel.SENAME, nodeInstID = ServiceFlowModel.SENAME)
    protected String serviceUIModelId;

    @BSearchFieldConfig(fieldName = "documentType", nodeName = ServiceFlowModel.NODENAME, seName =
            ServiceFlowModel.SENAME, nodeInstID = ServiceFlowModel.SENAME)
    protected int documentType;

    @BSearchFieldConfig(fieldName = "nodeInstId", nodeName = ServiceFlowCondField.NODENAME, seName =
            ServiceFlowCondField.SENAME, nodeInstID = ServiceFlowCondField.NODENAME)
    protected String nodeInstId;

    @BSearchFieldConfig(fieldName = "fieldName", nodeName = ServiceFlowCondField.NODENAME, seName =
            ServiceFlowCondField.SENAME, nodeInstID = ServiceFlowCondField.NODENAME)
    protected String fieldName;

    @BSearchFieldConfig(fieldName = "name", nodeName = FlowRouter.NODENAME, seName = FlowRouter.SENAME, nodeInstID =
            FlowRouter.SENAME)
    protected String refRouterName;


    @BSearchFieldConfig(fieldName = "id", nodeName = FlowRouter.NODENAME, seName = FlowRouter.SENAME, nodeInstID =
            FlowRouter.SENAME)
    protected String refRouterId;

    public int getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public String getRefRouterUUID() {
        return this.refRouterUUID;
    }

    public void setRefRouterUUID(String refRouterUUID) {
        this.refRouterUUID = refRouterUUID;
    }

    public String getServiceUIModelId() {
        return this.serviceUIModelId;
    }

    public void setServiceUIModelId(String serviceUIModelId) {
        this.serviceUIModelId = serviceUIModelId;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRefRouterName() {
        return refRouterName;
    }

    public void setRefRouterName(String refRouterName) {
        this.refRouterName = refRouterName;
    }

    public String getRefRouterId() {
        return refRouterId;
    }

    public void setRefRouterId(String refRouterId) {
        this.refRouterId = refRouterId;
    }

    public ServiceDocSearchHeaderModel getHeaderModel() {
        return headerModel;
    }

    public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
        this.headerModel = headerModel;
    }

    public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
        return createdUpdateModel;
    }

    public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
        this.createdUpdateModel = createdUpdateModel;
    }
}

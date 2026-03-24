package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.FlowRouter;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;


public class FlowRouterSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = ServiceFlowModel.SENAME)
    protected ServiceDocSearchHeaderModel headerModel;

    @BSearchGroupConfig(groupInstId = ServiceFlowModel.SENAME)
    protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

    @BSearchFieldConfig(fieldName = "serialFlag", nodeName = FlowRouter.NODENAME, seName = FlowRouter.SENAME, nodeInstID =
            FlowRouter.SENAME)
    protected int serialFlag;

    @BSearchFieldConfig(fieldName = "refDirectAssigneeUUID", nodeName = FlowRouter.NODENAME, seName =
            FlowRouter.SENAME, nodeInstID = FlowRouter.SENAME)
    protected String refDirectAssigneeUUID;

    @BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID =
            LogonUser.SENAME)
    protected String refDirectAssigneeName;


    @BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, nodeInstID =
            LogonUser.SENAME)
    protected String refDirectAssigneeId;


    public int getSerialFlag() {
        return serialFlag;
    }

    public void setSerialFlag(int serialFlag) {
        this.serialFlag = serialFlag;
    }

    public String getRefDirectAssigneeUUID() {
        return this.refDirectAssigneeUUID;
    }

    public void setRefDirectAssigneeUUID(String refDirectAssigneeUUID) {
        this.refDirectAssigneeUUID = refDirectAssigneeUUID;
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

    public String getRefDirectAssigneeName() {
        return refDirectAssigneeName;
    }

    public void setRefDirectAssigneeName(String refDirectAssigneeName) {
        this.refDirectAssigneeName = refDirectAssigneeName;
    }

    public String getRefDirectAssigneeId() {
        return refDirectAssigneeId;
    }

    public void setRefDirectAssigneeId(String refDirectAssigneeId) {
        this.refDirectAssigneeId = refDirectAssigneeId;
    }
}

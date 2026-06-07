package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.platform.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.platform.model.NavigationSystemSetting;
import com.company.IntelligentPlatform.platform.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.platform.dto.ServiceEntityCreateUpdateSearchModel;

public class NavigationSystemSettingSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = NavigationSystemSetting.SENAME)
    protected ServiceDocSearchHeaderModel headerModel;

    @BSearchGroupConfig(groupInstId = NavigationSystemSetting.SENAME)
    protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

    @BSearchFieldConfig(fieldName = "status", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
    protected int status;

    @BSearchFieldConfig(fieldName = "applicationLevel", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
    protected int applicationLevel;

    @BSearchFieldConfig(fieldName = "systemCategory", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
    protected int systemCategory;

    public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
        return createdUpdateModel;
    }

    public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
        this.createdUpdateModel = createdUpdateModel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getApplicationLevel() {
        return applicationLevel;
    }

    public void setApplicationLevel(int applicationLevel) {
        this.applicationLevel = applicationLevel;
    }

    public int getSystemCategory() {
        return systemCategory;
    }

    public void setSystemCategory(int systemCategory) {
        this.systemCategory = systemCategory;
    }

    public ServiceDocSearchHeaderModel getHeaderModel() {
        return headerModel;
    }

    public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
        this.headerModel = headerModel;
    }
}

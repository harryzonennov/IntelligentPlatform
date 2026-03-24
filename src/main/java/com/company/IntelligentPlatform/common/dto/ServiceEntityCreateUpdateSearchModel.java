package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.SearchDocConfigHelper;
import com.company.IntelligentPlatform.common.model.LogonUser;

import java.util.Date;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;

public class ServiceEntityCreateUpdateSearchModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, subNodeInstId = SearchDocConfigHelper.NODE_ID_CREATEDBY)
    protected String createdByName;

    @BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME,
            subNodeInstId = SearchDocConfigHelper.NODE_ID_CREATEDBY)
    protected String createdById;

    @BSearchFieldConfig(fieldName = "createdTime", fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
    protected Date createdTimeLow;

    @BSearchFieldConfig(fieldName = "createdTime", fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
    protected Date createdTimeHigh;

    @BSearchFieldConfig(fieldName = "name", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME, subNodeInstId = SearchDocConfigHelper.NODE_ID_LASTUPDATEDBY)
    protected String lastUpdateByName;

    @BSearchFieldConfig(fieldName = "id", nodeName = LogonUser.NODENAME, seName = LogonUser.SENAME,
            subNodeInstId = SearchDocConfigHelper.NODE_ID_LASTUPDATEDBY)
    protected String lastUpdateById;

    @BSearchFieldConfig(fieldName = "lastUpdateTime",  fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
    protected Date lastUpdateTimeLow;

    @BSearchFieldConfig(fieldName = "lastUpdateTime", fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
    protected Date lastUpdateTimeHigh;

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Date getCreatedTimeLow() {
        return createdTimeLow;
    }

    public void setCreatedTimeLow(Date createdTimeLow) {
        this.createdTimeLow = createdTimeLow;
    }

    public Date getCreatedTimeHigh() {
        return createdTimeHigh;
    }

    public void setCreatedTimeHigh(Date createdTimeHigh) {
        this.createdTimeHigh = createdTimeHigh;
    }

    public String getLastUpdateByName() {
        return lastUpdateByName;
    }

    public void setLastUpdateByName(String lastUpdateByName) {
        this.lastUpdateByName = lastUpdateByName;
    }

    public String getLastUpdateById() {
        return lastUpdateById;
    }

    public void setLastUpdateById(String lastUpdateById) {
        this.lastUpdateById = lastUpdateById;
    }

    public Date getLastUpdateTimeLow() {
        return lastUpdateTimeLow;
    }

    public void setLastUpdateTimeLow(Date lastUpdateTimeLow) {
        this.lastUpdateTimeLow = lastUpdateTimeLow;
    }

    public Date getLastUpdateTimeHigh() {
        return lastUpdateTimeHigh;
    }

    public void setLastUpdateTimeHigh(Date lastUpdateTimeHigh) {
        this.lastUpdateTimeHigh = lastUpdateTimeHigh;
    }

}

package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonUser;

import java.util.Date;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;

public class DocActionNodeSearchModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "docActionCode")
    protected int docActionCode;

    @BSearchFieldConfig(fieldName = "executionTime", fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
    protected Date executionTimeHigh;

    @BSearchFieldConfig(fieldName = "executionTime", fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
    protected Date executionTimeLow;

    protected String executionTimeStrHigh;

    protected String executionTimeStrLow;

    @BSearchFieldConfig(fieldName = "executedByUUID")
    protected String executedByUUID;

    @BSearchFieldConfig(fieldName = "name", subNodeInstId = LogonUser.SENAME)
    protected String executedByUserName;

    @BSearchFieldConfig(fieldName = "id", subNodeInstId = LogonUser.SENAME)
    protected String executedByUserId;

    public int getDocActionCode() {
        return docActionCode;
    }

    public void setDocActionCode(int docActionCode) {
        this.docActionCode = docActionCode;
    }

    public Date getExecutionTimeHigh() {
        return executionTimeHigh;
    }

    public void setExecutionTimeHigh(Date executionTimeHigh) {
        this.executionTimeHigh = executionTimeHigh;
    }

    public Date getExecutionTimeLow() {
        return executionTimeLow;
    }

    public void setExecutionTimeLow(Date executionTimeLow) {
        this.executionTimeLow = executionTimeLow;
    }

    public String getExecutionTimeStrHigh() {
        return executionTimeStrHigh;
    }

    public void setExecutionTimeStrHigh(String executionTimeStrHigh) {
        this.executionTimeStrHigh = executionTimeStrHigh;
    }

    public String getExecutionTimeStrLow() {
        return executionTimeStrLow;
    }

    public void setExecutionTimeStrLow(String executionTimeStrLow) {
        this.executionTimeStrLow = executionTimeStrLow;
    }

    public String getExecutedByUUID() {
        return executedByUUID;
    }

    public void setExecutedByUUID(String executedByUUID) {
        this.executedByUUID = executedByUUID;
    }

    public String getExecutedByUserName() {
        return executedByUserName;
    }

    public void setExecutedByUserName(String executedByUserName) {
        this.executedByUserName = executedByUserName;
    }

    public String getExecutedByUserId() {
        return executedByUserId;
    }

    public void setExecutedByUserId(String executedByUserId) {
        this.executedByUserId = executedByUserId;
    }
}

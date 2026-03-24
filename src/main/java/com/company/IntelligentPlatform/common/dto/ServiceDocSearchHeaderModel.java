package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;

public class ServiceDocSearchHeaderModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "uuid")
    protected String uuid;

    @BSearchFieldConfig(fieldName = "id")
    protected String id;

    @BSearchFieldConfig(fieldName = "name")
    protected String name;

    @BSearchFieldConfig(fieldName = "priorityCode")
    protected int priorityCode;

    @BSearchFieldConfig(fieldName = "status")
    protected int status;

    @BSearchFieldConfig(fieldName = "productionBatchNumber")
    protected String productionBatchNumber;

    @BSearchFieldConfig(fieldName = "purchaseBatchNumber")
    protected String purchaseBatchNumber;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(int priorityCode) {
        this.priorityCode = priorityCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProductionBatchNumber() {
        return productionBatchNumber;
    }

    public void setProductionBatchNumber(String productionBatchNumber) {
        this.productionBatchNumber = productionBatchNumber;
    }

    public String getPurchaseBatchNumber() {
        return purchaseBatchNumber;
    }

    public void setPurchaseBatchNumber(String purchaseBatchNumber) {
        this.purchaseBatchNumber = purchaseBatchNumber;
    }
}

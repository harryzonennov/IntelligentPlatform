package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonUser;

import java.util.Date;
import com.company.IntelligentPlatform.common.dto.DocEmbedMaterialSKUSearchModel;

public class DocEmbedMaterialSKUSearchModel extends SEUIComModel {

    @BSearchFieldConfig(fieldName = "uuid")
    protected String refMaterialSKUUUID;

    @BSearchFieldConfig(fieldName = "id")
    protected String refMaterialSKUId;

    @BSearchFieldConfig(fieldName = "packageStandard")
    protected String packageStandard;

    @BSearchFieldConfig(fieldName = "serialId", subNodeInstId = RegisteredProduct.SENAME)
    protected String serialId;

    @BSearchFieldConfig(fieldName = "supplyType", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int supplyType;

    @BSearchFieldConfig(fieldName = "traceMode", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int traceMode;

    @BSearchFieldConfig(fieldName = "traceStatus", nodeName = MaterialStockKeepUnit.NODENAME, seName =
            MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int traceStatus;

    @BSearchFieldConfig(fieldName = "name", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected String refMaterialSKUName;

    @BSearchFieldConfig(fieldName = "uuid", subNodeInstId = MaterialType.SENAME)
    protected String refMaterialTypeUUID;

    @BSearchFieldConfig(fieldName = "id", subNodeInstId = MaterialType.SENAME)
    protected String refMaterialTypeId;

    @BSearchFieldConfig(fieldName = "name", subNodeInstId = MaterialType.SENAME)
    protected String refMaterialTypeName;

    public DocEmbedMaterialSKUSearchModel() {
    }

    public DocEmbedMaterialSKUSearchModel(String refMaterialSKUUUID) {
        this.refMaterialSKUUUID = refMaterialSKUUUID;
    }

    public String getRefMaterialSKUUUID() {
        return refMaterialSKUUUID;
    }

    public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
        this.refMaterialSKUUUID = refMaterialSKUUUID;
    }

    public String getRefMaterialSKUId() {
        return refMaterialSKUId;
    }

    public void setRefMaterialSKUId(String refMaterialSKUId) {
        this.refMaterialSKUId = refMaterialSKUId;
    }

    public String getPackageStandard() {
        return packageStandard;
    }

    public void setPackageStandard(String packageStandard) {
        this.packageStandard = packageStandard;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public int getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(int supplyType) {
        this.supplyType = supplyType;
    }

    public int getTraceMode() {
        return traceMode;
    }

    public void setTraceMode(int traceMode) {
        this.traceMode = traceMode;
    }

    public int getTraceStatus() {
        return traceStatus;
    }

    public void setTraceStatus(int traceStatus) {
        this.traceStatus = traceStatus;
    }

    public String getRefMaterialSKUName() {
        return refMaterialSKUName;
    }

    public void setRefMaterialSKUName(String refMaterialSKUName) {
        this.refMaterialSKUName = refMaterialSKUName;
    }

    public String getRefMaterialTypeUUID() {
        return refMaterialTypeUUID;
    }

    public void setRefMaterialTypeUUID(String refMaterialTypeUUID) {
        this.refMaterialTypeUUID = refMaterialTypeUUID;
    }

    public String getRefMaterialTypeId() {
        return refMaterialTypeId;
    }

    public void setRefMaterialTypeId(String refMaterialTypeId) {
        this.refMaterialTypeId = refMaterialTypeId;
    }

    public String getRefMaterialTypeName() {
        return refMaterialTypeName;
    }

    public void setRefMaterialTypeName(String refMaterialTypeName) {
        this.refMaterialTypeName = refMaterialTypeName;
    }
}

package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialActionLog;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.dto.DocActionNodeSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceDocSearchHeaderModel;
import com.company.IntelligentPlatform.common.dto.ServiceEntityCreateUpdateSearchModel;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.service.BSearchGroupConfig;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * MaterialStockKeepUnit UI Model
 * *
 *
 * @author
 * @date Tue Sep 01 22:05:20 CST 2015
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */

@Component
public class MaterialStockKeepUnitSearchModel extends SEUIComModel {

    @BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
    protected ServiceDocSearchHeaderModel headerModel;

    @BSearchGroupConfig(groupInstId = MaterialType.SENAME)
    protected MaterialTypeSearchSubModel materialType;

    @BSearchGroupConfig(groupInstId = MaterialStockKeepUnit.SENAME)
    protected ServiceEntityCreateUpdateSearchModel createdUpdateModel;

    @BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_SUBMIT)
    protected DocActionNodeSearchModel submittedBy;

    @BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_APPROVE)
    protected DocActionNodeSearchModel approvedBy;

    @BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ACTIVE)
    protected DocActionNodeSearchModel activeBy;

    @BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_REINIT)
    protected DocActionNodeSearchModel reInitBy;

    @BSearchGroupConfig(groupInstId = MaterialActionLog.NODEINST_ACTION_ARCHIVE)
    protected DocActionNodeSearchModel archivedBy;

    @BSearchFieldConfig(fieldName = "serviceEntityName", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME)
    protected String serviceEntityName;

    @BSearchFieldConfig(fieldName = "refMaterialUUID", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME)
    protected String refMaterialUUID;

    @BSearchFieldConfig(fieldName = "materialCategory", nodeName = Material.NODENAME, seName = Material.SENAME, nodeInstID = Material.SENAME)
    protected int materialCategory;

    @BSearchFieldConfig(fieldName = "mainProductionPlace", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected String mainProductionPlace;

    @BSearchFieldConfig(fieldName = "supplyType", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int supplyType;

    @BSearchFieldConfig(fieldName = "switchFlag", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int switchFlag;

    @BSearchFieldConfig(fieldName = "supplierName", nodeName = CorporateCustomer.NODENAME, seName = CorporateCustomer.SENAME, nodeInstID = CorporateCustomer.SENAME)
    protected String supplierName;

    @BSearchFieldConfig(fieldName = "packageStandard", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected String packageStandard;

    @BSearchFieldConfig(fieldName = "productionBatchNumber", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected String productionBatchNumber;

    @BSearchFieldConfig(fieldName = "productionDate", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_HIGH)
    protected Date productionDateHigh;

    @BSearchFieldConfig(fieldName = "productionDate", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME, fieldType = BSearchFieldConfig.FIELDTYPE_LOW)
    protected Date productionDateLow;

    @BSearchFieldConfig(fieldName = "traceLevel", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int traceLevel;

    @BSearchFieldConfig(fieldName = "traceMode", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int traceMode;

    @BSearchFieldConfig(fieldName = "operationMode", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int operationMode;

    @BSearchFieldConfig(fieldName = "qualityInspectFlag", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME,
            nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int qualityInspectFlag;

    @BSearchFieldConfig(fieldName = "cargoType", nodeName = MaterialStockKeepUnit.NODENAME, seName = MaterialStockKeepUnit.SENAME, nodeInstID = MaterialStockKeepUnit.SENAME)
    protected int cargoType;

    public String getServiceEntityName() {
        return serviceEntityName;
    }

    public void setServiceEntityName(String serviceEntityName) {
        this.serviceEntityName = serviceEntityName;
    }

    public int getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(int materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getMainProductionPlace() {
        return mainProductionPlace;
    }

    public void setMainProductionPlace(String mainProductionPlace) {
        this.mainProductionPlace = mainProductionPlace;
    }

    public int getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(int switchFlag) {
        this.switchFlag = switchFlag;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPackageStandard() {
        return packageStandard;
    }

    public void setPackageStandard(String packageStandard) {
        this.packageStandard = packageStandard;
    }

    public String getProductionBatchNumber() {
        return productionBatchNumber;
    }

    public void setProductionBatchNumber(String productionBatchNumber) {
        this.productionBatchNumber = productionBatchNumber;
    }

    public Date getProductionDateHigh() {
        return productionDateHigh;
    }

    public void setProductionDateHigh(Date productionDateHigh) {
        this.productionDateHigh = productionDateHigh;
    }

    public Date getProductionDateLow() {
        return productionDateLow;
    }

    public void setProductionDateLow(Date productionDateLow) {
        this.productionDateLow = productionDateLow;
    }

    public String getRefMaterialUUID() {
        return refMaterialUUID;
    }

    public void setRefMaterialUUID(String refMaterialUUID) {
        this.refMaterialUUID = refMaterialUUID;
    }

    public int getTraceLevel() {
        return traceLevel;
    }

    public void setTraceLevel(int traceLevel) {
        this.traceLevel = traceLevel;
    }

    public int getTraceMode() {
        return traceMode;
    }

    public void setTraceMode(int traceMode) {
        this.traceMode = traceMode;
    }

    public int getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(int operationMode) {
        this.operationMode = operationMode;
    }

    public int getQualityInspectFlag() {
        return qualityInspectFlag;
    }

    public void setQualityInspectFlag(int qualityInspectFlag) {
        this.qualityInspectFlag = qualityInspectFlag;
    }

    public int getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(int supplyType) {
        this.supplyType = supplyType;
    }

    public ServiceDocSearchHeaderModel getHeaderModel() {
        return headerModel;
    }

    public void setHeaderModel(ServiceDocSearchHeaderModel headerModel) {
        this.headerModel = headerModel;
    }

    public MaterialTypeSearchSubModel getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialTypeSearchSubModel materialType) {
        this.materialType = materialType;
    }

    public ServiceEntityCreateUpdateSearchModel getCreatedUpdateModel() {
        return createdUpdateModel;
    }

    public void setCreatedUpdateModel(ServiceEntityCreateUpdateSearchModel createdUpdateModel) {
        this.createdUpdateModel = createdUpdateModel;
    }

    public DocActionNodeSearchModel getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(DocActionNodeSearchModel submittedBy) {
        this.submittedBy = submittedBy;
    }

    public DocActionNodeSearchModel getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(DocActionNodeSearchModel approvedBy) {
        this.approvedBy = approvedBy;
    }

    public DocActionNodeSearchModel getActiveBy() {
        return activeBy;
    }

    public void setActiveBy(DocActionNodeSearchModel activeBy) {
        this.activeBy = activeBy;
    }

    public DocActionNodeSearchModel getReInitBy() {
        return reInitBy;
    }

    public void setReInitBy(DocActionNodeSearchModel reInitBy) {
        this.reInitBy = reInitBy;
    }

    public DocActionNodeSearchModel getArchivedBy() {
        return archivedBy;
    }

    public void setArchivedBy(DocActionNodeSearchModel archivedBy) {
        this.archivedBy = archivedBy;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }
}

package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

public class MaterialStockKeepUnitUIModel extends SEUIComModel implements
        Comparable<MaterialStockKeepUnitUIModel> {

    /**
     * Section:[materialStockKeepUnit]
     **/

    protected String barcode;

    protected double length;

    protected double width;

    protected double height;

    protected double volume;

    protected double netWeight;

    protected double grossWeight;

    protected int switchFlag;

    protected int switchFlagShow;

    protected int status;

    protected String statusValue;

    @ISEDropDownResourceMapping(resouceMapping = "Material_supplyType", valueFieldName = "supplyTypeValue")
    protected int supplyType;

    protected String supplyTypeValue;

    protected String switchFlagValue;

    protected String productionPlace;

    protected String packageStandard;

    protected Date productionDate;

    protected String productionBatchNumber;

    protected String refMaterialUUID;

    protected String refMaterialId;

    protected String refMaterialName;

    protected String mainMaterialUnit;

    protected String mainMaterialUnitName;


    @ISEUIModelMapping(fieldName = "inboundDeliveryPrice", seName = MaterialStockKeepUnit.SENAME, nodeName =
            MaterialStockKeepUnit.NODENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnList = false, secId =
            MaterialStockKeepUnit.SENAME, tabId = TABID_BASIC)
    protected double inboundDeliveryPrice;

    @ISEUIModelMapping(fieldName = "outboundDeliveryPrice", seName = MaterialStockKeepUnit.SENAME, nodeName =
            MaterialStockKeepUnit.NODENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnList = false, secId =
            MaterialStockKeepUnit.SENAME, tabId = TABID_BASIC)
    protected double outboundDeliveryPrice;

    @ISEUIModelMapping(fieldName = "refSupplierUUID", seName = MaterialStockKeepUnit.SENAME, nodeName =
            MaterialStockKeepUnit.NODENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnList = false, secId =
            MaterialStockKeepUnit.SENAME, tabId = TABID_BASIC)
    protected String refSupplierUUID;

    @ISEUIModelMapping(fieldName = "name", seName = CorporateCustomer.SENAME, nodeName = CorporateCustomer.NODENAME,
            nodeInstID = CorporateCustomer.SENAME, readOnlyFlag = true, secId = CorporateCustomer.SENAME, tabId =
            TABID_BASIC)
    protected String supplierName;

    /**
     * Section:[material]
     **/

    @ISEUIModelMapping(fieldName = "materialCategory", seName = Material.SENAME, nodeName = Material.NODENAME,
            nodeInstID = Material.SENAME, showOnList = false, readOnlyFlag = true, secId = Material.SENAME, tabId =
            TABID_BASIC)
    @ISEDropDownResourceMapping(resouceMapping = "Material_materialCategory", valueFieldName = "materialCategoryValue")
    protected int materialCategory;

    @ISEUIModelMapping(seName = Material.SENAME, nodeName = Material.NODENAME, nodeInstID = Material.SENAME,
            showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    protected String materialCategoryValue;

    protected String refMaterialType;

    @ISEUIModelMapping(fieldName = "retailPrice", seName = Material.SENAME, nodeName = Material.NODENAME, nodeInstID
            = Material.SENAME, showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    protected double retailPrice;

    @ISEUIModelMapping(fieldName = "purchasePrice", seName = Material.SENAME, nodeName = Material.NODENAME,
            nodeInstID = Material.SENAME, showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    protected double purchasePrice;

    protected double retailPriceDisplay;

    protected double purchasePriceDisplay;

    protected double unitCostDisplay;

    @ISEUIModelMapping(fieldName = "wholeSalePrice", seName = Material.SENAME, nodeName = Material.NODENAME,
            nodeInstID = Material.SENAME, showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    protected double wholeSalePrice;

    @ISEUIModelMapping(fieldName = "memberSalePrice", seName = Material.SENAME, nodeName = Material.NODENAME,
            nodeInstID = Material.SENAME, showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    protected double memberSalePrice;

    @ISEUIModelMapping(fieldName = "packageMaterialType", seName = Material.SENAME, nodeName = Material.NODENAME,
            nodeInstID = Material.SENAME, showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    @ISEDropDownResourceMapping(resouceMapping = "Material_packageMaterialType", valueFieldName = "")
    protected int packageMaterialType;

    protected String packageMaterialTypeValue;

    @ISEUIModelMapping(fieldName = "minStoreNumber", seName = Material.SENAME, nodeName = Material.NODENAME,
            nodeInstID = Material.SENAME, showOnEditor = false, secId = Material.SENAME, tabId = TABID_BASIC)
    protected int minStoreNumber;

    protected String systemMatTypeID;

    protected String materialTypeName;

    protected String materialTypeId;

    protected Date lastUpdateTime;

    protected int cargoType;

    protected String cargoTypeValue;

    /**
     * [Section] Production and purchase setting
     */
    @ISEUIModelMapping(fieldName = "fixLeadTime", seName = MaterialStockKeepUnit.SENAME, nodeName =
            MaterialStockKeepUnit.NODENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnList = false, secId =
            MaterialStockKeepUnit.SENAME, tabId = TABID_BASIC)
    protected double fixLeadTime;

    @ISEUIModelMapping(fieldName = "variableLeadTime", seName = MaterialStockKeepUnit.SENAME, nodeName =
            MaterialStockKeepUnit.NODENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnList = false, secId =
            MaterialStockKeepUnit.SENAME, tabId = TABID_BASIC)
    protected double variableLeadTime;

    @ISEUIModelMapping(fieldName = "amountForVarLeadTime", seName = MaterialStockKeepUnit.SENAME, nodeName =
            MaterialStockKeepUnit.NODENAME, nodeInstID = MaterialStockKeepUnit.SENAME, showOnList = false, secId =
            MaterialStockKeepUnit.SENAME, tabId = TABID_BASIC)
    protected double amountForVarLeadTime;

    protected double unitCost;

    protected String refLengthUnit;

    protected String refVolumeUnit;

    protected String refWeightUnit;

    /**
     * Weather this material using 1-simple (default) operation mode, or
     * 2-compound operation mode.
     */
    @ISEDropDownResourceMapping(resouceMapping = "Material_operationMode", valueFieldName = "")
    protected int operationMode;

    @ISEDropDownResourceMapping(resouceMapping = "MaterialStockKeepUnit_traceMode", valueFieldName = "")
    protected int traceMode;

    protected String traceModeValue;

    @ISEDropDownResourceMapping(resouceMapping = "MaterialStockKeepUnit_traceLevel", valueFieldName = "")
    protected int traceLevel;

    protected String traceLevelValue;

    /**
     * Only work if trace level is batch or single, not working for template
     */
    @ISEDropDownResourceMapping(resouceMapping = "MaterialStockKeepUnit_traceStatus", valueFieldName = "")
    protected int traceStatus;

    protected String traceStatusValue;

    protected String refTemplateUUID;

    protected int qualityInspectFlag;

    protected String qualityInspectValue;

    public MaterialStockKeepUnitUIModel() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(int supplyType) {
        this.supplyType = supplyType;
    }

    public String getSupplyTypeValue() {
        return supplyTypeValue;
    }

    public void setSupplyTypeValue(String supplyTypeValue) {
        this.supplyTypeValue = supplyTypeValue;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public int getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(int switchFlag) {
        this.switchFlag = switchFlag;
    }

    public int getSwitchFlagShow() {
        return switchFlagShow;
    }

    public void setSwitchFlagShow(int switchFlagShow) {
        this.switchFlagShow = switchFlagShow;
    }

    public String getSwitchFlagValue() {
        return switchFlagValue;
    }

    public void setSwitchFlagValue(String switchFlagValue) {
        this.switchFlagValue = switchFlagValue;
    }

    public String getPackageStandard() {
        return packageStandard;
    }

    public void setPackageStandard(String packageStandard) {
        this.packageStandard = packageStandard;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getProductionBatchNumber() {
        return productionBatchNumber;
    }

    public void setProductionBatchNumber(String productionBatchNumber) {
        this.productionBatchNumber = productionBatchNumber;
    }

    public String getProductionPlace() {
        return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
    }

    public String getRefMaterialUUID() {
        return refMaterialUUID;
    }

    public void setRefMaterialUUID(String refMaterialUUID) {
        this.refMaterialUUID = refMaterialUUID;
    }

    public double getInboundDeliveryPrice() {
        return inboundDeliveryPrice;
    }

    public void setInboundDeliveryPrice(double inboundDeliveryPrice) {
        this.inboundDeliveryPrice = inboundDeliveryPrice;
    }

    public double getOutboundDeliveryPrice() {
        return outboundDeliveryPrice;
    }

    public void setOutboundDeliveryPrice(double outboundDeliveryPrice) {
        this.outboundDeliveryPrice = outboundDeliveryPrice;
    }

    public String getRefSupplierUUID() {
        return refSupplierUUID;
    }

    public void setRefSupplierUUID(String refSupplierUUID) {
        this.refSupplierUUID = refSupplierUUID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(int materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getMaterialCategoryValue() {
        return materialCategoryValue;
    }

    public void setMaterialCategoryValue(String materialCategoryValue) {
        this.materialCategoryValue = materialCategoryValue;
    }

    public String getRefMaterialType() {
        return refMaterialType;
    }

    public void setRefMaterialType(String refMaterialType) {
        this.refMaterialType = refMaterialType;
    }

    public String getRefMaterialId() {
        return refMaterialId;
    }

    public void setRefMaterialId(String refMaterialId) {
        this.refMaterialId = refMaterialId;
    }

    public String getRefMaterialName() {
        return refMaterialName;
    }

    public void setRefMaterialName(String refMaterialName) {
        this.refMaterialName = refMaterialName;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getRetailPriceDisplay() {
        return retailPriceDisplay;
    }

    public void setRetailPriceDisplay(double retailPriceDisplay) {
        this.retailPriceDisplay = retailPriceDisplay;
    }

    public double getPurchasePriceDisplay() {
        return purchasePriceDisplay;
    }

    public void setPurchasePriceDisplay(double purchasePriceDisplay) {
        this.purchasePriceDisplay = purchasePriceDisplay;
    }

    public double getUnitCostDisplay() {
        return unitCostDisplay;
    }

    public void setUnitCostDisplay(double unitCostDisplay) {
        this.unitCostDisplay = unitCostDisplay;
    }

    public double getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(double wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    public double getMemberSalePrice() {
        return memberSalePrice;
    }

    public void setMemberSalePrice(double memberSalePrice) {
        this.memberSalePrice = memberSalePrice;
    }

    public int getPackageMaterialType() {
        return packageMaterialType;
    }

    public void setPackageMaterialType(int packageMaterialType) {
        this.packageMaterialType = packageMaterialType;
    }

    public String getPackageMaterialTypeValue() {
        return packageMaterialTypeValue;
    }

    public void setPackageMaterialTypeValue(String packageMaterialTypeValue) {
        this.packageMaterialTypeValue = packageMaterialTypeValue;
    }

    public int getMinStoreNumber() {
        return minStoreNumber;
    }

    public void setMinStoreNumber(int minStoreNumber) {
        this.minStoreNumber = minStoreNumber;
    }

    public String getMainMaterialUnit() {
        return mainMaterialUnit;
    }

    public void setMainMaterialUnit(String mainMaterialUnit) {
        this.mainMaterialUnit = mainMaterialUnit;
    }

    public String getSystemMatTypeID() {
        return systemMatTypeID;
    }

    public void setSystemMatTypeID(String systemMatTypeID) {
        this.systemMatTypeID = systemMatTypeID;
    }

    public String getMaterialTypeName() {
        return materialTypeName;
    }

    public void setMaterialTypeName(String materialTypeName) {
        this.materialTypeName = materialTypeName;
    }

    public String getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(String materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }

    public String getCargoTypeValue() {
        return cargoTypeValue;
    }

    public void setCargoTypeValue(String cargoTypeValue) {
        this.cargoTypeValue = cargoTypeValue;
    }

    public double getFixLeadTime() {
        return fixLeadTime;
    }

    public void setFixLeadTime(double fixLeadTime) {
        this.fixLeadTime = fixLeadTime;
    }

    public double getVariableLeadTime() {
        return variableLeadTime;
    }

    public void setVariableLeadTime(double variableLeadTime) {
        this.variableLeadTime = variableLeadTime;
    }

    public double getAmountForVarLeadTime() {
        return amountForVarLeadTime;
    }

    public void setAmountForVarLeadTime(double amountForVarLeadTime) {
        this.amountForVarLeadTime = amountForVarLeadTime;
    }

    public String getMainMaterialUnitName() {
        return mainMaterialUnitName;
    }

    public void setMainMaterialUnitName(String mainMaterialUnitName) {
        this.mainMaterialUnitName = mainMaterialUnitName;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public String getRefLengthUnit() {
        return refLengthUnit;
    }

    public void setRefLengthUnit(String refLengthUnit) {
        this.refLengthUnit = refLengthUnit;
    }

    public String getRefVolumeUnit() {
        return refVolumeUnit;
    }

    public void setRefVolumeUnit(String refVolumeUnit) {
        this.refVolumeUnit = refVolumeUnit;
    }

    public String getRefWeightUnit() {
        return refWeightUnit;
    }

    public void setRefWeightUnit(String refWeightUnit) {
        this.refWeightUnit = refWeightUnit;
    }

    public int getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(int operationMode) {
        this.operationMode = operationMode;
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

    public int getTraceStatus() {
        return traceStatus;
    }

    public void setTraceStatus(int traceStatus) {
        this.traceStatus = traceStatus;
    }

    public String getRefTemplateUUID() {
        return refTemplateUUID;
    }

    public void setRefTemplateUUID(String refTemplateUUID) {
        this.refTemplateUUID = refTemplateUUID;
    }

    public String getTraceModeValue() {
        return traceModeValue;
    }

    public void setTraceModeValue(String traceModeValue) {
        this.traceModeValue = traceModeValue;
    }

    public String getTraceLevelValue() {
        return traceLevelValue;
    }

    public void setTraceLevelValue(String traceLevelValue) {
        this.traceLevelValue = traceLevelValue;
    }

    public String getTraceStatusValue() {
        return traceStatusValue;
    }

    public void setTraceStatusValue(String traceStatusValue) {
        this.traceStatusValue = traceStatusValue;
    }

    public int getQualityInspectFlag() {
        return qualityInspectFlag;
    }

    public void setQualityInspectFlag(int qualityInspectFlag) {
        this.qualityInspectFlag = qualityInspectFlag;
    }

    public String getQualityInspectValue() {
        return qualityInspectValue;
    }

    public void setQualityInspectValue(String qualityInspectValue) {
        this.qualityInspectValue = qualityInspectValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    @Override
    public int compareTo(MaterialStockKeepUnitUIModel other) {
        Date homeUpdateTime = this.getLastUpdateTime();
        Date otherUpdateTime = other.getLastUpdateTime();
        if (otherUpdateTime != null) {
            return otherUpdateTime.compareTo(homeUpdateTime);
        } else {
            return -1;
        }
    }

}

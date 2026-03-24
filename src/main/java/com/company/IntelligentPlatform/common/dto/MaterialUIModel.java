package com.company.IntelligentPlatform.common.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;

public class MaterialUIModel extends SEUIComModel implements Comparable<MaterialUIModel> {

	/**
	 * Section:[material]
	 **/

	protected String refMaterialType;
	
	protected String materialTypeId;
	
	protected String materialTypeName;
	
	protected String systemMatTypeID;
	
	@ISEDropDownResourceMapping(resouceMapping = "Material_materialCategory", valueFieldName = "materialCategoryValue")
	protected int materialCategory;
	
	protected String materialCategoryValue;
	
	protected String mainProductionPlace;
	
	@ISEDropDownResourceMapping(resouceMapping = "Material_switchFlag", valueFieldName = "switchFlagValue")
	protected int switchFlag;

	protected int switchFlagShow;

	@ISEDropDownResourceMapping(resouceMapping = "Material_status", valueFieldName = "statusValue")
	protected int status;

	protected String statusValue;
	
	protected String switchFlagValue;
	
	protected String mainMaterialUnit;
	
	protected String mainMaterialUnitName;

	protected double length;

	protected double width;

	protected double height;

	protected double netWeight;

	protected double grossWeight;

	protected double volume;

	protected double inboundDeliveryPrice;

	protected double outboundDeliveryPrice;

	protected String refMainSupplierUUID;

	protected String mainSupplierName;

	protected String barcode;

	protected String packageStandard;

	protected double retailPrice;
	
	protected double purchasePrice;
	
	protected double wholeSalePrice;

	protected double retailPriceDisplay;

	protected double purchasePriceDisplay;

	protected double unitCostDisplay;
	
	protected double memberSalePrice;
	
	@ISEDropDownResourceMapping(resouceMapping = "Material_packageMaterialType", valueFieldName = "")
	protected int packageMaterialType;
	
	protected String packageMaterialTypeValue;
	
	protected int minStoreNumber;
	
	protected Date lastUpdateTime;
	
	protected int cargoType;
	
	protected String cargoTypeValue;
	
	/**
	 * [Section] Production and purchase setting
	 */
	
	protected double fixLeadTime;
	
	protected double variableLeadTime;
	
	protected double amountForVarLeadTime;
	
	@ISEDropDownResourceMapping(resouceMapping = "Material_supplyType", valueFieldName = "supplyTypeValue")
	protected int supplyType;
	
	protected String supplyTypeValue;	

	protected double unitCost;
	
	protected String refLengthUnit;
	
	protected String refVolumeUnit;
	
	protected String refWeightUnit;
	
	protected String refLengthUnitValue;
	
	protected String refVolumeUnitValue;
	
	protected String refWeightUnitValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "Material_operationMode", valueFieldName = "")
	protected int operationMode;
	
	protected String operationModeValue;
	
	protected int qualityInspectFlag;
	
	protected String qualityInspectFlagValue;

	public String getRefMaterialType() {
		return refMaterialType;
	}

	public void setRefMaterialType(String refMaterialType) {
		this.refMaterialType = refMaterialType;
	}

	public String getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(String materialTypeId) {
		this.materialTypeId = materialTypeId;
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

	public String getMainMaterialUnit() {
		return mainMaterialUnit;
	}

	public void setMainMaterialUnit(String mainMaterialUnit) {
		this.mainMaterialUnit = mainMaterialUnit;
	}

	public String getMainMaterialUnitName() {
		return mainMaterialUnitName;
	}

	public void setMainMaterialUnitName(String mainMaterialUnitName) {
		this.mainMaterialUnitName = mainMaterialUnitName;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
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

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
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

	public String getRefMainSupplierUUID() {
		return refMainSupplierUUID;
	}

	public void setRefMainSupplierUUID(String refMainSupplierUUID) {
		this.refMainSupplierUUID = refMainSupplierUUID;
	}

	public String getMainSupplierName() {
		return mainSupplierName;
	}

	public void setMainSupplierName(String mainSupplierName) {
		this.mainSupplierName = mainSupplierName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
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

	public String getOperationModeValue() {
		return operationModeValue;
	}

	public void setOperationModeValue(String operationModeValue) {
		this.operationModeValue = operationModeValue;
	}

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

	public String getQualityInspectFlagValue() {
		return qualityInspectFlagValue;
	}

	public void setQualityInspectFlagValue(String qualityInspectFlagValue) {
		this.qualityInspectFlagValue = qualityInspectFlagValue;
	}

	public String getRefLengthUnitValue() {
		return refLengthUnitValue;
	}

	public void setRefLengthUnitValue(String refLengthUnitValue) {
		this.refLengthUnitValue = refLengthUnitValue;
	}

	public String getRefVolumeUnitValue() {
		return refVolumeUnitValue;
	}

	public void setRefVolumeUnitValue(String refVolumeUnitValue) {
		this.refVolumeUnitValue = refVolumeUnitValue;
	}

	public String getRefWeightUnitValue() {
		return refWeightUnitValue;
	}

	public void setRefWeightUnitValue(String refWeightUnitValue) {
		this.refWeightUnitValue = refWeightUnitValue;
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
	public int compareTo(MaterialUIModel other) {
		Date homeUpdateTime = this.getLastUpdateTime();
		Date otherUpdateTime = other.getLastUpdateTime();
		if (otherUpdateTime != null) {
			return otherUpdateTime.compareTo(homeUpdateTime);
		} else {
			return -1;
		}
	}

}

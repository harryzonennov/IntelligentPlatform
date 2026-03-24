package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class Material extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.Material;

	public static final int MATECATE_RETAIL_GOODS = 1;

	public static final int MATECATE_FINISH_PRODUCTS = 2;

	public static final int MATECATE_RAW_MATERIAL = 3;

	public static final int MATECATE_SEMI_FINISH_PRODUCTS = 4;

	public static final int MATECATE_ACCESSORIES = 5;

	public static final int SUPPLYTYPE_SELF_PROD = 1;

	public static final int SUPPLYTYPE_PURCHASE = 2;

	public static final int SUPPLYTYPE_SUBCONTRACT = 3;

	public static final int SUPPLYTYPE_MIXED = 4;

	public static final int OPERATIONMODE_SIMPLE = 1;

	public static final int OPERATIONMODE_COMPOUND = 2;

	public static final int STATUS_INIT = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ACTIVE = DocumentContent.STATUS_ACTIVE;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;

	protected String refMainSupplierUUID;

	protected String mainProductionPlace;

	/**
	 * Size length: standard unit: mm
	 */
	protected double length;

	/**
	 * Size width: standard unit: mm
	 */
	protected double width;

	/**
	 * Size height: standard unit: mm
	 */
	protected double height;

	protected double netWeight;

	protected double grossWeight;

	/**
	 * standard unit:m3
	 */
	protected double volume;

	protected double inboundDeliveryPrice;

	protected double outboundDeliveryPrice;

	protected double retailPrice;

	protected double purchasePrice;

	protected double retailPriceDisplay;

	protected double purchasePriceDisplay;

	protected double unitCost;

	protected double unitCostDisplay;

	protected double wholeSalePrice;

	protected double memberSalePrice;

	protected int materialCategory;

	protected String refMaterialType;

	protected int switchFlag;

	protected int status;

	protected String mainMaterialUnit;

	protected String barcode;

	protected String packageStandard;

	protected int packageMaterialType;

	protected int minStoreNumber;

	protected int cargoType;

	protected double fixLeadTime;

	protected double variableLeadTime;

	protected double amountForVarLeadTime;

	protected int supplyType;

	protected String refLengthUnit;

	protected String refVolumeUnit;

	protected String refWeightUnit;

	/**
	 * Weather this material using 1-simple (default) operation mode, or
	 * 2-compound operation mode.
	 */
	protected int operationMode;

	protected int qualityInspectFlag;

	public Material() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.cargoType = Cargo.CARGOTYPE_HEAVY;
		this.supplyType = SUPPLYTYPE_PURCHASE;
		this.status = STATUS_INIT;
		this.switchFlag = StandardSwitchProxy.SWITCH_INITIAL;
		this.operationMode = OPERATIONMODE_SIMPLE;
		this.qualityInspectFlag = StandardSwitchProxy.SWITCH_OFF;
	}

	public String getRefMainSupplierUUID() {
		return refMainSupplierUUID;
	}

	public void setRefMainSupplierUUID(String refMainSupplierUUID) {
		this.refMainSupplierUUID = refMainSupplierUUID;
	}

	public String getMainProductionPlace() {
		return mainProductionPlace;
	}

	public void setMainProductionPlace(String mainProductionPlace) {
		this.mainProductionPlace = mainProductionPlace;
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

	public int getMaterialCategory() {
		return materialCategory;
	}

	public void setMaterialCategory(int materialCategory) {
		this.materialCategory = materialCategory;
	}

	public String getRefMaterialType() {
		return refMaterialType;
	}

	public void setRefMaterialType(String refMaterialType) {
		this.refMaterialType = refMaterialType;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMainMaterialUnit() {
		return mainMaterialUnit;
	}

	public void setMainMaterialUnit(String mainMaterialUnit) {
		this.mainMaterialUnit = mainMaterialUnit;
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

	public int getPackageMaterialType() {
		return packageMaterialType;
	}

	public void setPackageMaterialType(int packageMaterialType) {
		this.packageMaterialType = packageMaterialType;
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

	public int getMinStoreNumber() {
		return minStoreNumber;
	}

	public void setMinStoreNumber(int minStoreNumber) {
		this.minStoreNumber = minStoreNumber;
	}

	public int getCargoType() {
		return cargoType;
	}

	public void setCargoType(int cargoType) {
		this.cargoType = cargoType;
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

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
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

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

}

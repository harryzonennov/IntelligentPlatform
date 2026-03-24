package com.company.IntelligentPlatform.common.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class MaterialStockKeepUnit extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.MaterialStockKeepUnit;

	public static final String FIELD_REF_MATERIALUUID = "refMaterialUUID";

	public static final int TRACELEVEL_TEMPLATE = 1;

	public static final int TRACELEVEL_INSTANCE = 2;

	public static final int TRACEMODE_NONE = 1;

	public static final int TRACEMODE_SINGLE = 2;

	public static final int TRACEMODE_BATCH = 3;

	public static final int TRACESTATUS_INIT = 1;

	public static final int TRACESTATUS_ACTIVE = 2;

	public static final int TRACESTATUS_INSERVICE = 3;

	public static final int TRACESTATUS_ARCHIVE = 4;

	public static final int TRACESTATUS_WASTE = 5;

	public static final int TRACESTATUS_DELETE = 6;

	public static final int STATUS_INIT = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ACTIVE = DocumentContent.STATUS_ACTIVE;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;

	protected String barcode;

	protected int switchFlag;

	protected String packageStandard;

	protected Date productionDate;

	protected String refMaterialUUID;

	protected int status;

	protected String refSupplierUUID;

	protected String productionPlace;

	protected String productionBatchNumber;

	protected double inboundDeliveryPrice;

	protected double outboundDeliveryPrice;

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

	protected String mainMaterialUnit;

	protected int packageMaterialType;

	protected double retailPrice;

	protected double memberSalePrice;

	protected double wholeSalePrice;

	protected double purchasePrice;

	protected double retailPriceDisplay;

	protected double purchasePriceDisplay;

	protected double unitCostDisplay;

	protected int minStoreNumber;

	protected int cargoType;

	protected double fixLeadTime;

	protected double variableLeadTime;

	protected double amountForVarLeadTime;

	protected int supplyType;

    protected double unitCost;

	protected String refLengthUnit;

	protected String refVolumeUnit;

	protected String refWeightUnit;

	/**
	 * Weather this material using 1-simple (default) operation mode, or
	 * 2-compound operation mode.
	 */
	protected int operationMode;


	/**
	 * Indicate how to trace the instance of SKU
	 */
	protected int traceMode;

	/**
	 * Only work if trace mode is batch or single, indicate the current trace level of SKU instance.
	 */
	protected int traceLevel;

	/**
	 * Only work if trace mode is batch or single, not working for template
	 */
	protected int traceStatus;

	protected int qualityInspectFlag;

	protected String refTemplateUUID;

	public MaterialStockKeepUnit() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.cargoType = Cargo.CARGOTYPE_HEAVY;
		this.switchFlag = StandardSwitchProxy.SWITCH_INITIAL;
		this.traceStatus = TRACESTATUS_INIT;
		this.traceLevel = TRACELEVEL_TEMPLATE;
		this.traceMode = TRACEMODE_NONE;
		this.status = Material.STATUS_INIT;
		this.qualityInspectFlag = StandardSwitchProxy.SWITCH_OFF;
		this.operationMode = Material.OPERATIONMODE_SIMPLE;
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

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getRefMaterialUUID() {
		return refMaterialUUID;
	}

	public void setRefMaterialUUID(String refMaterialUUID) {
		this.refMaterialUUID = refMaterialUUID;
	}

	public String getRefSupplierUUID() {
		return refSupplierUUID;
	}

	public void setRefSupplierUUID(String refSupplierUUID) {
		this.refSupplierUUID = refSupplierUUID;
	}

	public String getProductionPlace() {
		return productionPlace;
	}

	public void setProductionPlace(String productionPlace) {
		this.productionPlace = productionPlace;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
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

	public String getMainMaterialUnit() {
		return mainMaterialUnit;
	}

	public void setMainMaterialUnit(String mainMaterialUnit) {
		this.mainMaterialUnit = mainMaterialUnit;
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

	public double getMemberSalePrice() {
		return memberSalePrice;
	}

	public void setMemberSalePrice(double memberSalePrice) {
		this.memberSalePrice = memberSalePrice;
	}

	public double getWholeSalePrice() {
		return wholeSalePrice;
	}

	public void setWholeSalePrice(double wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}

	public double getPurchasePrice() {
		return purchasePrice;
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

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
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

	public int getQualityInspectFlag() {
		return qualityInspectFlag;
	}

	public void setQualityInspectFlag(int qualityInspectFlag) {
		this.qualityInspectFlag = qualityInspectFlag;
	}

}

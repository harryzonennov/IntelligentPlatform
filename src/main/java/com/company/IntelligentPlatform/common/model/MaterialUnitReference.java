package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;

public class MaterialUnitReference extends ReferenceNode{

	public static final String NODENAME = IServiceModelConstants.MaterialUnitReference;

	public static final String SENAME = Material.SENAME;

    public static final int UNITTYPE_REF_TOSTANDARD = 1;

	public static final int UNITTYPE_SKU_SELF = 2;

	protected double ratioToStandard;

	protected String barcode;

	protected double length;

	protected double width;

	protected double height;

	protected double volume;

	protected double grossWeight;

	protected double netWeight;

	protected int unitType;

	protected int outPackageMaterialType;

	protected double inboundDeliveryPrice;

	protected double outboundDeliveryPrice;

	protected double retailPrice;

	protected double purchasePrice;

	protected double wholeSalePrice;

	protected double memberSalePrice;

    protected double unitCost;

	protected String refLengthUnit;

	protected String refVolumeUnit;

	protected String refWeightUnit;

	public MaterialUnitReference() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.ratioToStandard = 1;
	}

	public double getRatioToStandard() {
		return ratioToStandard;
	}

	public void setRatioToStandard(double ratioToStandard) {
		this.ratioToStandard = ratioToStandard;
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

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

	public int getOutPackageMaterialType() {
		return outPackageMaterialType;
	}

	public void setOutPackageMaterialType(int outPackageMaterialType) {
		this.outPackageMaterialType = outPackageMaterialType;
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

}

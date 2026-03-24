package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * Inbound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class InboundDeliveryUIModel extends DocumentUIModel {

	protected String productionBatchNumber;
	
	protected String purchaseBatchNumber;

	protected String amountLabel;

	protected double amount;
	
	protected double weight;
	
	protected double volume;

	protected double grossDeclaredValue;
	
	protected String refWarehouseUUID;
	
	protected String refWarehouseAreaUUID;
	
	protected String refWarehouseId;

	protected String refWarehouseName;
	
	protected String refWarehouseAreaId;

	protected String refWarehouseAreaName;
	
	protected double grossInboundFee;
	
	protected double grossPrice;
	
	protected String shippingTime;
	
	protected String shippingPoint;
	
	protected String carrier;
	
	protected String carrierVehicle;
	
	@ISEDropDownResourceMapping(resouceMapping = "Delivery_freightChargeType", valueFieldName = "freightChargeTypeValue")
	protected int freightChargeType;
	
	protected String freightChargeTypeValue;
	
	protected double freightCharge;
	
	@ISEDropDownResourceMapping(resouceMapping = "Delivery_planCategory", valueFieldName = "planCategoryValue")
	protected int planCategory;
	
	protected String planCategoryValue;
	
	protected String planExecuteDate;

	public InboundDeliveryUIModel() {

	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefWarehouseAreaName() {
		return refWarehouseAreaName;
	}

	public void setRefWarehouseAreaName(String refWarehouseAreaName) {
		this.refWarehouseAreaName = refWarehouseAreaName;
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

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefWarehouseAreaId() {
		return refWarehouseAreaId;
	}

	public void setRefWarehouseAreaId(String refWarehouseAreaId) {
		this.refWarehouseAreaId = refWarehouseAreaId;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getGrossDeclaredValue() {
		return grossDeclaredValue;
	}

	public void setGrossDeclaredValue(double grossDeclaredValue) {
		this.grossDeclaredValue = grossDeclaredValue;
	}

	public double getGrossInboundFee() {
		return grossInboundFee;
	}

	public void setGrossInboundFee(double grossInboundFee) {
		this.grossInboundFee = grossInboundFee;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingPoint() {
		return shippingPoint;
	}

	public void setShippingPoint(String shippingPoint) {
		this.shippingPoint = shippingPoint;
	}

	public int getFreightChargeType() {
		return freightChargeType;
	}

	public void setFreightChargeType(int freightChargeType) {
		this.freightChargeType = freightChargeType;
	}

	public double getFreightCharge() {
		return freightCharge;
	}

	public void setFreightCharge(double freightCharge) {
		this.freightCharge = freightCharge;
	}

	public String getFreightChargeTypeValue() {
		return freightChargeTypeValue;
	}

	public void setFreightChargeTypeValue(String freightChargeTypeValue) {
		this.freightChargeTypeValue = freightChargeTypeValue;
	}

	public int getPlanCategory() {
		return planCategory;
	}

	public void setPlanCategory(int planCategory) {
		this.planCategory = planCategory;
	}

	public String getPlanCategoryValue() {
		return planCategoryValue;
	}

	public void setPlanCategoryValue(String planCategoryValue) {
		this.planCategoryValue = planCategoryValue;
	}

	public String getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(String planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}

}

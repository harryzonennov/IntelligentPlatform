package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * Inventory transfer order UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class InventoryTransferOrderUIModel extends DocumentUIModel {

	protected String productionBatchNumber;
	
	protected String purchaseBatchNumber;

	protected double grossDeclaredValue;
	
	protected String refOutboundWarehouseUUID;
	
	protected String refOutboundWarehouseAreaUUID;
	
	protected String refOutboundWarehouseId;
	
	protected String refOutboundWarehouseName;
	
	protected String refOutboundWarehouseAreaId;
	
	protected String refOutboundWarehouseAreaName;

	protected String refInboundWarehouseUUID;
	
	protected String refInboundWarehouseAreaUUID;
	
	protected String refInboundWarehouseId;
	
	protected String refInboundWarehouseName;

	protected String refInboundWarehouseAreaId;
	
	protected String refInboundWarehouseAreaName;
	
	protected String shippingTime;

	protected double grossOutboundFee;

	protected double grossStorageFee;
	
	protected String refOutboundDeliveryUUID;
	
	protected String refOutboundDeliveryId;
	
	protected int refOutboundDeliveryStatus;
	
	protected String refInboundDeliveryUUID;
	
	protected String refInboundDeliveryId;
	
	protected int refInboundDeliveryStatus;
	
	@ISEDropDownResourceMapping(resouceMapping = "Delivery_planCategory", valueFieldName = "planCategoryValue")
	protected int planCategory;
	
	protected String planCategoryValue;
	
	protected String planExecuteDate;
	
	protected String shippingPoint;
	
	protected double grossPrice;

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

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getRefInboundDeliveryUUID() {
		return refInboundDeliveryUUID;
	}

	public void setRefInboundDeliveryUUID(String refInboundDeliveryUUID) {
		this.refInboundDeliveryUUID = refInboundDeliveryUUID;
	}

	public String getRefOutboundWarehouseAreaUUID() {
		return refOutboundWarehouseAreaUUID;
	}

	public void setRefOutboundWarehouseAreaUUID(String refOutboundWarehouseAreaUUID) {
		this.refOutboundWarehouseAreaUUID = refOutboundWarehouseAreaUUID;
	}

	public String getRefOutboundWarehouseId() {
		return refOutboundWarehouseId;
	}

	public void setRefOutboundWarehouseId(String refOutboundWarehouseId) {
		this.refOutboundWarehouseId = refOutboundWarehouseId;
	}

	public String getRefInboundWarehouseAreaUUID() {
		return refInboundWarehouseAreaUUID;
	}

	public void setRefInboundWarehouseAreaUUID(String refInboundWarehouseAreaUUID) {
		this.refInboundWarehouseAreaUUID = refInboundWarehouseAreaUUID;
	}

	public String getRefOutboundWarehouseAreaId() {
		return refOutboundWarehouseAreaId;
	}

	public void setRefOutboundWarehouseAreaId(String refOutboundWarehouseAreaId) {
		this.refOutboundWarehouseAreaId = refOutboundWarehouseAreaId;
	}

	public String getRefOutboundWarehouseAreaName() {
		return refOutboundWarehouseAreaName;
	}

	public void setRefOutboundWarehouseAreaName(String refOutboundWarehouseAreaName) {
		this.refOutboundWarehouseAreaName = refOutboundWarehouseAreaName;
	}

	public String getRefInboundWarehouseAreaId() {
		return refInboundWarehouseAreaId;
	}

	public void setRefInboundWarehouseAreaId(String refInboundWarehouseAreaId) {
		this.refInboundWarehouseAreaId = refInboundWarehouseAreaId;
	}

	public String getRefInboundWarehouseAreaName() {
		return refInboundWarehouseAreaName;
	}

	public void setRefInboundWarehouseAreaName(String refInboundWarehouseAreaName) {
		this.refInboundWarehouseAreaName = refInboundWarehouseAreaName;
	}

	public String getRefInboundWarehouseId() {
		return refInboundWarehouseId;
	}

	public void setRefInboundWarehouseId(String refInboundWarehouseId) {
		this.refInboundWarehouseId = refInboundWarehouseId;
	}

	public String getShippingPoint() {
		return shippingPoint;
	}

	public void setShippingPoint(String shippingPoint) {
		this.shippingPoint = shippingPoint;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public double getGrossDeclaredValue() {
		return grossDeclaredValue;
	}

	public void setGrossDeclaredValue(double grossDeclaredValue) {
		this.grossDeclaredValue = grossDeclaredValue;
	}
	
	public double getGrossOutboundFee() {
		return grossOutboundFee;
	}

	public void setGrossOutboundFee(double grossOutboundFee) {
		this.grossOutboundFee = grossOutboundFee;
	}

	public double getGrossStorageFee() {
		return grossStorageFee;
	}

	public void setGrossStorageFee(double grossStorageFee) {
		this.grossStorageFee = grossStorageFee;
	}

	public String getRefOutboundWarehouseUUID() {
		return refOutboundWarehouseUUID;
	}

	public void setRefOutboundWarehouseUUID(String refOutboundWarehouseUUID) {
		this.refOutboundWarehouseUUID = refOutboundWarehouseUUID;
	}

	public String getRefOutboundWarehouseName() {
		return refOutboundWarehouseName;
	}

	public void setRefOutboundWarehouseName(String refOutboundWarehouseName) {
		this.refOutboundWarehouseName = refOutboundWarehouseName;
	}
	
	public String getRefInboundWarehouseUUID() {
		return refInboundWarehouseUUID;
	}

	public void setRefInboundWarehouseUUID(String refInboundWarehouseUUID) {
		this.refInboundWarehouseUUID = refInboundWarehouseUUID;
	}

	public String getRefInboundWarehouseName() {
		return refInboundWarehouseName;
	}

	public void setRefInboundWarehouseName(String refInboundWarehouseName) {
		this.refInboundWarehouseName = refInboundWarehouseName;
	}

	public String getRefOutboundDeliveryId() {
		return refOutboundDeliveryId;
	}

	public String getRefOutboundDeliveryUUID() {
		return refOutboundDeliveryUUID;
	}

	public void setRefOutboundDeliveryUUID(String refOutboundDeliveryUUID) {
		this.refOutboundDeliveryUUID = refOutboundDeliveryUUID;
	}

	public void setRefOutboundDeliveryId(String refOutboundDeliveryId) {
		this.refOutboundDeliveryId = refOutboundDeliveryId;
	}

	public String getRefInboundDeliveryId() {
		return refInboundDeliveryId;
	}

	public void setRefInboundDeliveryId(String refInboundDeliveryId) {
		this.refInboundDeliveryId = refInboundDeliveryId;
	}

	public int getRefOutboundDeliveryStatus() {
		return refOutboundDeliveryStatus;
	}

	public void setRefOutboundDeliveryStatus(int refOutboundDeliveryStatus) {
		this.refOutboundDeliveryStatus = refOutboundDeliveryStatus;
	}

	public int getRefInboundDeliveryStatus() {
		return refInboundDeliveryStatus;
	}

	public void setRefInboundDeliveryStatus(int refInboundDeliveryStatus) {
		this.refInboundDeliveryStatus = refInboundDeliveryStatus;
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

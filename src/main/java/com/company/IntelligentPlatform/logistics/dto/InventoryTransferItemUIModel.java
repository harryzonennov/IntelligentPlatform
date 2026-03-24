package com.company.IntelligentPlatform.logistics.dto;


import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

/**
 * Inventory Transfer Item UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class InventoryTransferItemUIModel extends DocMatItemUIModel {

	protected String baseUUID;

	protected String refStoreItemUUID;

	protected double volume;

	protected double weight;
	
	protected double taxRate;

	protected double declaredValue;
	
	protected double itemPriceNoTax;
	
	protected double unitPriceNoTax;

	protected double unitValue;

	protected String productionDate;

	protected String refInboundWarehouseAreaUUID;

	protected String refInboundWarehouseAreaId;

	protected String refInboundWarehouseAreaName;

    protected String refOutboundWarehouseAreaUUID;
    
    protected String refOutboundWarehouseAreaId;
    
    protected String refOutboundWarehouseAreaName;

	protected String producerName;

	protected double outboundFee;

	protected double storageFee;

	protected String refOutboundWarehouseId;
	
	protected String refOutboundWarehouseName;
	
	protected String refOutboundWarehouseUUID;
	
	protected String refInboundWarehouseUUID;
	
	protected String refInboundWarehouseId;
	
	protected String refInboundWarehouseName;
	
	protected String refInboundDeliveryId;
	
	protected int refInboundDeliveryStatus;
	
	protected String refOutboundDeliveryId;
	
	protected int refOutboundDeliveryStatus;

	protected int storeDay;
	
	protected String refInboundItemUUID;
	
	protected String refOutboundItemUUID;
	
	protected String processDate;
	
	protected String approvedDate;

	protected double availableAmount;

	protected String availableAmountLabel;

	protected String availableRefUnitUUID;

	public int getRefInboundDeliveryStatus() {
		return refInboundDeliveryStatus;
	}

	public void setRefInboundDeliveryStatus(int refInboundDeliveryStatus) {
		this.refInboundDeliveryStatus = refInboundDeliveryStatus;
	}

	public int getRefOutboundDeliveryStatus() {
		return refOutboundDeliveryStatus;
	}

	public void setRefOutboundDeliveryStatus(int refOutboundDeliveryStatus) {
		this.refOutboundDeliveryStatus = refOutboundDeliveryStatus;
	}

	public String getRefInboundItemUUID() {
		return refInboundItemUUID;
	}

	public void setRefInboundItemUUID(String refInboundItemUUID) {
		this.refInboundItemUUID = refInboundItemUUID;
	}

	public String getRefOutboundItemUUID() {
		return refOutboundItemUUID;
	}

	public void setRefOutboundItemUUID(String refOutboundItemUUID) {
		this.refOutboundItemUUID = refOutboundItemUUID;
	}

	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getItemPriceNoTax() {
		return itemPriceNoTax;
	}

	public void setItemPriceNoTax(double itemPriceNoTax) {
		this.itemPriceNoTax = itemPriceNoTax;
	}
	
	public double getUnitPriceNoTax() {
		return unitPriceNoTax;
	}

	public void setUnitPriceNoTax(double unitPriceNoTax) {
		this.unitPriceNoTax = unitPriceNoTax;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public double getOutboundFee() {
		return outboundFee;
	}

	public void setOutboundFee(double outboundFee) {
		this.outboundFee = outboundFee;
	}

	public double getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(double storageFee) {
		this.storageFee = storageFee;
	}

	public int getStoreDay() {
		return storeDay;
	}

	public void setStoreDay(int storeDay) {
		this.storeDay = storeDay;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}

	public String getRefInboundWarehouseAreaUUID() {
		return refInboundWarehouseAreaUUID;
	}

	public void setRefInboundWarehouseAreaUUID(String refInboundWarehouseAreaUUID) {
		this.refInboundWarehouseAreaUUID = refInboundWarehouseAreaUUID;
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

	public String getRefOutboundWarehouseAreaUUID() {
		return refOutboundWarehouseAreaUUID;
	}

	public void setRefOutboundWarehouseAreaUUID(String refOutboundWarehouseAreaUUID) {
		this.refOutboundWarehouseAreaUUID = refOutboundWarehouseAreaUUID;
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

	public String getRefOutboundWarehouseId() {
		return refOutboundWarehouseId;
	}

	public void setRefOutboundWarehouseId(String refOutboundWarehouseId) {
		this.refOutboundWarehouseId = refOutboundWarehouseId;
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

	public String getRefInboundWarehouseId() {
		return refInboundWarehouseId;
	}

	public void setRefInboundWarehouseId(String refInboundWarehouseId) {
		this.refInboundWarehouseId = refInboundWarehouseId;
	}

	public String getRefInboundWarehouseName() {
		return refInboundWarehouseName;
	}

	public void setRefInboundWarehouseName(String refInboundWarehouseName) {
		this.refInboundWarehouseName = refInboundWarehouseName;
	}

	public String getRefInboundDeliveryId() {
		return refInboundDeliveryId;
	}

	public void setRefInboundDeliveryId(String refInboundDeliveryId) {
		this.refInboundDeliveryId = refInboundDeliveryId;
	}

	public String getRefOutboundDeliveryId() {
		return refOutboundDeliveryId;
	}

	public void setRefOutboundDeliveryId(String refOutboundDeliveryId) {
		this.refOutboundDeliveryId = refOutboundDeliveryId;
	}

	public String getRefOutboundWarehouseUUID() {
		return refOutboundWarehouseUUID;
	}

	public void setRefOutboundWarehouseUUID(String refOutboundWarehouseUUID) {
		this.refOutboundWarehouseUUID = refOutboundWarehouseUUID;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(final double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getAvailableAmountLabel() {
		return availableAmountLabel;
	}

	public void setAvailableAmountLabel(final String availableAmountLabel) {
		this.availableAmountLabel = availableAmountLabel;
	}

	public String getAvailableRefUnitUUID() {
		return availableRefUnitUUID;
	}

	public void setAvailableRefUnitUUID(final String availableRefUnitUUID) {
		this.availableRefUnitUUID = availableRefUnitUUID;
	}
}

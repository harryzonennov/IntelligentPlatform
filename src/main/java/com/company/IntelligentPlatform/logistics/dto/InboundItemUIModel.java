package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

/**
 * Inbound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class InboundItemUIModel extends DocMatItemUIModel {
	
	protected int itemIndex;
	
	protected double volume;
	
	protected double weight;
	
	protected double declaredValue;
	
	protected double unitValue;
	
	protected String productionDate;

	protected String refUnitNodeInstID;

	protected String refWarehouseUUID;
	
	protected String refWarehouseId;

	protected String refWarehouseName;

	protected String refWarehouseAreaUUID;
	
	protected String refWarehouseAreaId;
	
	protected String refWarehouseAreaName;

	protected String refShelfNumberId;

	protected String producerName;
	
	protected double inboundFee;
	
    protected double itemPriceNoTax;
	
	protected double unitPriceNoTax;
	
	protected String currencyCode;
	
	protected double taxRate;	
	
	protected double unitOutboundPrice;
		
	protected double itemOutboundPrice;
	    
	protected double itemOutboundPriceNoTax;
	    
	protected double unitOutboundPriceNoTax;
	
	protected String refStoreItemUUID;

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
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

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}
	
	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getRefUnitNodeInstID() {
		return refUnitNodeInstID;
	}

	public void setRefUnitNodeInstID(String refUnitNodeInstID) {
		this.refUnitNodeInstID = refUnitNodeInstID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getRefWarehouseAreaId() {
		return refWarehouseAreaId;
	}

	public void setRefWarehouseAreaId(String refWarehouseAreaId) {
		this.refWarehouseAreaId = refWarehouseAreaId;
	}

	public String getRefWarehouseAreaName() {
		return refWarehouseAreaName;
	}

	public void setRefWarehouseAreaName(String refWarehouseAreaName) {
		this.refWarehouseAreaName = refWarehouseAreaName;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefShelfNumberId() {
		return refShelfNumberId;
	}

	public void setRefShelfNumberId(String refShelfNumberId) {
		this.refShelfNumberId = refShelfNumberId;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public double getInboundFee() {
		return inboundFee;
	}

	public void setInboundFee(double inboundFee) {
		this.inboundFee = inboundFee;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getUnitOutboundPrice() {
		return unitOutboundPrice;
	}

	public void setUnitOutboundPrice(double unitOutboundPrice) {
		this.unitOutboundPrice = unitOutboundPrice;
	}

	public double getItemOutboundPrice() {
		return itemOutboundPrice;
	}

	public void setItemOutboundPrice(double itemOutboundPrice) {
		this.itemOutboundPrice = itemOutboundPrice;
	}

	public double getItemOutboundPriceNoTax() {
		return itemOutboundPriceNoTax;
	}

	public void setItemOutboundPriceNoTax(double itemOutboundPriceNoTax) {
		this.itemOutboundPriceNoTax = itemOutboundPriceNoTax;
	}

	public double getUnitOutboundPriceNoTax() {
		return unitOutboundPriceNoTax;
	}

	public void setUnitOutboundPriceNoTax(double unitOutboundPriceNoTax) {
		this.unitOutboundPriceNoTax = unitOutboundPriceNoTax;
	}
}

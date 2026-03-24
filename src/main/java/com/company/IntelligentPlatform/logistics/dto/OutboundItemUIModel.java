package com.company.IntelligentPlatform.logistics.dto;

import java.util.Date;

// TODO-LEGACY: import org.springframework.beans.factory.annotation.Required; // removed in Spring 6

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

/**
 * Inbound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class OutboundItemUIModel extends DocMatItemUIModel {

	protected int itemIndex;

	protected double volume;

	protected double weight;

	protected double declaredValue;

	protected double netValue;

	protected double unitValue;

	protected Date productionDate;

	protected String refUnitNodeInstID;

	protected String refWarehouseId;

	protected String refWarehouseName;

	protected String refWarehouseUUID;

	protected String refWarehouseAreaUUID;

	protected String refWarehouseAreaId;

	protected String refWarehouseAreaName;

	protected String refShelfNumberId;

	protected String producerName;

	protected double outboundFee;

	protected double storageFee;

	protected Date inboundDate;

	protected String refStoreItemUUID;

	protected int storeDay;

	protected double itemPriceNoTax;

	protected double unitPriceNoTax;

	protected double unitInboundPrice;

	protected double itemInboundPrice;

	protected double itemInboundPriceNoTax;

	protected String currencyCode;

	protected double unitInboundPriceNoTax;

	protected double taxRate;

	protected double availableAmount;

	protected String availableAmountLabel;

	protected String availableRefUnitUUID;
	
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

	public double getNetValue() {
		return netValue;
	}

	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
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

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
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

	public Date getInboundDate() {
		return inboundDate;
	}

	public void setInboundDate(Date inboundDate) {
		this.inboundDate = inboundDate;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public int getStoreDay() {
		return storeDay;
	}

	public void setStoreDay(int storeDay) {
		this.storeDay = storeDay;
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

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getUnitInboundPrice() {
		return unitInboundPrice;
	}

	public void setUnitInboundPrice(double unitInboundPrice) {
		this.unitInboundPrice = unitInboundPrice;
	}

	public double getItemInboundPrice() {
		return itemInboundPrice;
	}

	public void setItemInboundPrice(double itemInboundPrice) {
		this.itemInboundPrice = itemInboundPrice;
	}

	public double getItemInboundPriceNoTax() {
		return itemInboundPriceNoTax;
	}

	public void setItemInboundPriceNoTax(double itemInboundPriceNoTax) {
		this.itemInboundPriceNoTax = itemInboundPriceNoTax;
	}

	public double getUnitInboundPriceNoTax() {
		return unitInboundPriceNoTax;
	}

	public void setUnitInboundPriceNoTax(double unitInboundPriceNoTax) {
		this.unitInboundPriceNoTax = unitInboundPriceNoTax;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	// @Required
	public void setAvailableAmount(final double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getAvailableAmountLabel() {
		return availableAmountLabel;
	}

	// @Required
	public void setAvailableAmountLabel(final String availableAmountLabel) {
		this.availableAmountLabel = availableAmountLabel;
	}

	public String getAvailableRefUnitUUID() {
		return availableRefUnitUUID;
	}

	// @Required
	public void setAvailableRefUnitUUID(final String availableRefUnitUUID) {
		this.availableRefUnitUUID = availableRefUnitUUID;
	}

}

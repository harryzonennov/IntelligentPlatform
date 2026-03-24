package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

/**
 * Out-bound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class WarehouseStoreUIModel extends DocumentUIModel {

	protected String refWarehouseUUID;

	protected String refWarehouseId;

	protected String refWarehouseName;

	protected String refWarehouseAreaUUID;

	protected String refWarehouseAreaId;

	protected String refInboundDeliveryId;

	protected double grossPrice;

	protected double grossPriceDisplay;

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
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

	public String getRefInboundDeliveryId() {
		return refInboundDeliveryId;
	}

	public void setRefInboundDeliveryId(String refInboundDeliveryId) {
		this.refInboundDeliveryId = refInboundDeliveryId;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public double getGrossPriceDisplay() {
		return grossPriceDisplay;
	}

	public void setGrossPriceDisplay(double grossPriceDisplay) {
		this.grossPriceDisplay = grossPriceDisplay;
	}
}

package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;
import java.time.LocalDateTime;

/**
 * DTO for OutboundDelivery create/update requests.
 */
public class OutboundDeliveryDto {

	protected String name;

	protected String client;

	protected String refWarehouseUUID;

	protected String refWarehouseAreaUUID;

	protected double grossPrice;

	protected LocalDateTime shippingTime;

	protected String shippingPoint;

	protected int freightChargeType;

	protected double freightCharge;

	protected int planCategory;

	protected LocalDateTime planExecuteDate;

	protected String productionBatchNumber;

	protected String purchaseBatchNumber;

	protected double grossOutboundFee;

	protected double grossStorageFee;

	protected String note;

	public OutboundDelivery toEntity() {
		OutboundDelivery delivery = new OutboundDelivery();
		applyTo(delivery);
		return delivery;
	}

	public void applyTo(OutboundDelivery delivery) {
		if (name != null)                   delivery.setName(name);
		if (client != null)                 delivery.setClient(client);
		if (refWarehouseUUID != null)        delivery.setRefWarehouseUUID(refWarehouseUUID);
		if (refWarehouseAreaUUID != null)    delivery.setRefWarehouseAreaUUID(refWarehouseAreaUUID);
		if (shippingTime != null)            delivery.setShippingTime(shippingTime);
		if (shippingPoint != null)           delivery.setShippingPoint(shippingPoint);
		if (productionBatchNumber != null)   delivery.setProductionBatchNumber(productionBatchNumber);
		if (purchaseBatchNumber != null)     delivery.setPurchaseBatchNumber(purchaseBatchNumber);
		if (planExecuteDate != null)         delivery.setPlanExecuteDate(planExecuteDate);
		if (note != null)                    delivery.setNote(note);
		delivery.setGrossPrice(grossPrice);
		delivery.setFreightChargeType(freightChargeType);
		delivery.setFreightCharge(freightCharge);
		delivery.setPlanCategory(planCategory);
		delivery.setGrossOutboundFee(grossOutboundFee);
		delivery.setGrossStorageFee(grossStorageFee);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public LocalDateTime getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(LocalDateTime shippingTime) {
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

	public int getPlanCategory() {
		return planCategory;
	}

	public void setPlanCategory(int planCategory) {
		this.planCategory = planCategory;
	}

	public LocalDateTime getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(LocalDateTime planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}

package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.PurchaseReturnOrder;

/**
 * DTO for PurchaseReturnOrder create/update requests.
 */
public class PurchaseReturnOrderDto {

	protected String name;

	protected String client;

	protected double grossPrice;

	protected double grossPriceDisplay;

	protected double taxRate;

	protected String productionBatchNumber;

	protected String note;

	public PurchaseReturnOrder toEntity() {
		PurchaseReturnOrder order = new PurchaseReturnOrder();
		applyTo(order);
		return order;
	}

	public void applyTo(PurchaseReturnOrder order) {
		if (name != null)                   order.setName(name);
		if (client != null)                 order.setClient(client);
		if (productionBatchNumber != null)  order.setProductionBatchNumber(productionBatchNumber);
		if (note != null)                   order.setNote(note);
		order.setGrossPrice(grossPrice);
		order.setGrossPriceDisplay(grossPriceDisplay);
		order.setTaxRate(taxRate);
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

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}

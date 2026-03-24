package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

/**
 * Sales Order Item UI Model
 ** 
 * @author Zhang,Hang
 * @date Wed Oct 16 19:00:12 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class SalesReturnOrderUIModel extends DocumentUIModel {

	protected String barcode;

	protected double taxRate;

	protected double grossPrice;
	
	protected double grossPriceDisplay;
	
	protected double grossAmount;
	
	protected double grossTypeAmount;
	
	protected String productionBatchNumber;

	protected String signDate;
	
	public SalesReturnOrderUIModel(){
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
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

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public double getGrossTypeAmount() {
		return grossTypeAmount;
	}

	public void setGrossTypeAmount(double grossTypeAmount) {
		this.grossTypeAmount = grossTypeAmount;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
}
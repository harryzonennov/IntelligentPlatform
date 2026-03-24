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
public class SalesForcastUIModel extends DocumentUIModel {

	protected String productionBatchNumber;

	protected double taxRate;

	protected double grossPrice;

	protected double grossPriceDisplay;

	protected String planExecutionDate;

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

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public String getPlanExecutionDate() {
		return planExecutionDate;
	}

	public void setPlanExecutionDate(String planExecutionDate) {
		this.planExecutionDate = planExecutionDate;
	}
}
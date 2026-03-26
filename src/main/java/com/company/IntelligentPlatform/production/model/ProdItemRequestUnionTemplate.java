package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;

/**
 * Internal module, not persist into DB
 * For manage production order item and prod order item request proposal
 */
public class ProdItemRequestUnionTemplate extends DocMatItemNode{

	protected double inStockAmount;

	protected double inProcessAmount;

	protected double toPickAmount;

	protected double availableAmount;

	protected double lackInPlanAmount;

	protected double pickedAmount;

	protected double suppliedAmount;

	protected int pickStatus;

	protected double itemCostLossRate;

	protected double itemCostActual;

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(final double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(final double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
	}

	public double getToPickAmount() {
		return toPickAmount;
	}

	public void setToPickAmount(final double toPickAmount) {
		this.toPickAmount = toPickAmount;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(final double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getLackInPlanAmount() {
		return lackInPlanAmount;
	}

	public void setLackInPlanAmount(final double lackInPlanAmount) {
		this.lackInPlanAmount = lackInPlanAmount;
	}

	public double getPickedAmount() {
		return pickedAmount;
	}

	public void setPickedAmount(final double pickedAmount) {
		this.pickedAmount = pickedAmount;
	}

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(final double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
	}

	public int getPickStatus() {
		return pickStatus;
	}

	public void setPickStatus(final int pickStatus) {
		this.pickStatus = pickStatus;
	}

	public double getItemCostLossRate() {
		return itemCostLossRate;
	}

	public void setItemCostLossRate(final double itemCostLossRate) {
		this.itemCostLossRate = itemCostLossRate;
	}

	public double getItemCostActual() {
		return itemCostActual;
	}

	public void setItemCostActual(final double itemCostActual) {
		this.itemCostActual = itemCostActual;
	}

}

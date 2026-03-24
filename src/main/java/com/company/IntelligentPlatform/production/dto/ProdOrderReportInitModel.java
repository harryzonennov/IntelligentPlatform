package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdOrderReportInitModel extends SEUIComModel {

	protected String refMaterialSKUUUID;

	protected String refMaterialSKUId;

	protected String refMaterialSKUName;
	
	protected int traceMode;
	
	protected double planAmount;
	
	protected String planUnitUUID;
	
	protected double leftAmount;
	
	protected String leftUnitUUID;
	
	protected double amount;
	
	protected String refUnitUUID;
	
	protected String startProdBatchNumber;
	
	protected int step;

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public int getTraceMode() {
		return traceMode;
	}

	public void setTraceMode(int traceMode) {
		this.traceMode = traceMode;
	}

	public double getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(double planAmount) {
		this.planAmount = planAmount;
	}

	public String getPlanUnitUUID() {
		return planUnitUUID;
	}

	public void setPlanUnitUUID(String planUnitUUID) {
		this.planUnitUUID = planUnitUUID;
	}

	public double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(double leftAmount) {
		this.leftAmount = leftAmount;
	}

	public String getLeftUnitUUID() {
		return leftUnitUUID;
	}

	public void setLeftUnitUUID(String leftUnitUUID) {
		this.leftUnitUUID = leftUnitUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getStartProdBatchNumber() {
		return startProdBatchNumber;
	}

	public void setStartProdBatchNumber(String startProdBatchNumber) {
		this.startProdBatchNumber = startProdBatchNumber;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
}

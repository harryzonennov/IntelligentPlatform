package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProductionPlanInitModel extends SEUIComModel {

	protected String refMaterialSKUUUID;

	protected String refMaterialSKUId;

	protected String refMaterialSKUName;

	protected String refBillOfMaterialUUID;

	protected String refBillOfMaterialId;

	protected String planStartPrepareDate;

	protected String planStartTime;

	protected String planEndTime;

	protected double amount;

	protected String refUnitUUID;
	
	protected String productionBatchNumber;

	protected int prevDocType;

	protected String prevDocMatItemUUID;

	public ProductionPlanInitModel() {

	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getRefBillOfMaterialUUID() {
		return refBillOfMaterialUUID;
	}

	public void setRefBillOfMaterialUUID(String refBillOfMaterialUUID) {
		this.refBillOfMaterialUUID = refBillOfMaterialUUID;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getRefBillOfMaterialId() {
		return refBillOfMaterialId;
	}

	public void setRefBillOfMaterialId(String refBillOfMaterialId) {
		this.refBillOfMaterialId = refBillOfMaterialId;
	}

	public String getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(String planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
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

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public int getPrevDocType() {
		return prevDocType;
	}

	public void setPrevDocType(int prevDocType) {
		this.prevDocType = prevDocType;
	}

	public String getPrevDocMatItemUUID() {
		return prevDocMatItemUUID;
	}

	public void setPrevDocMatItemUUID(String prevDocMatItemUUID) {
		this.prevDocMatItemUUID = prevDocMatItemUUID;
	}
}

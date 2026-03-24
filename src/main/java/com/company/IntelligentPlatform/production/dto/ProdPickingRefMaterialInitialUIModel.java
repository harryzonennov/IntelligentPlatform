package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdPickingRefMaterialInitialUIModel extends SEUIComModel{
	
    protected String baseUUID;
	
	protected String refMaterialSKUUUID;
	
	protected String refMaterialSKUId;
	
	protected String refMaterialSKUName;
	
	protected String packageStandard;
	
	protected String prodOrderId;
	
	protected String prodOrderName;
	
	protected String refOrderItemUUID;
	
	protected String refProdOrderUUID;
	
	protected double amount;

	protected String refUnitUUID;
	
    protected double inPlanAmount;	

	protected String inPlanUnitUUID;
	
	public String getBaseUUID() {
		return baseUUID;
	}

	public void setBaseUUID(String baseUUID) {
		this.baseUUID = baseUUID;
	}

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

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public String getProdOrderId() {
		return prodOrderId;
	}

	public void setProdOrderId(String prodOrderId) {
		this.prodOrderId = prodOrderId;
	}

	public String getRefProdOrderUUID() {
		return refProdOrderUUID;
	}

	public void setRefProdOrderUUID(String refProdOrderUUID) {
		this.refProdOrderUUID = refProdOrderUUID;
	}

	public String getRefOrderItemUUID() {
		return refOrderItemUUID;
	}

	public void setRefOrderItemUUID(String refOrderItemUUID) {
		this.refOrderItemUUID = refOrderItemUUID;
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

	public String getProdOrderName() {
		return prodOrderName;
	}

	public void setProdOrderName(String prodOrderName) {
		this.prodOrderName = prodOrderName;
	}

	public double getInPlanAmount() {
		return inPlanAmount;
	}

	public void setInPlanAmount(double inPlanAmount) {
		this.inPlanAmount = inPlanAmount;
	}

	public String getInPlanUnitUUID() {
		return inPlanUnitUUID;
	}

	public void setInPlanUnitUUID(String inPlanUnitUUID) {
		this.inPlanUnitUUID = inPlanUnitUUID;
	}

}

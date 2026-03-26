package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class ProductionPlanUIModel extends DocumentUIModel {

	@ISEDropDownResourceMapping(resouceMapping = "ProductionPlan_category", valueFieldName = "categoryValue")
	protected int category;

	protected String categoryValue;

	protected String planStartPrepareDate;

	protected double selfLeadTime;

	protected double comLeadTime;

	protected String planStartTime;

	protected String actualStartTime;

	protected String planEndTime;

	protected String actualEndTime;
	
	protected String refMaterialSKUUUID;

	protected String refMaterialSKUId;

	protected String refMaterialSKUName;

	protected String packageStandard;
	
	protected int supplyType;
	
	protected String supplyTypeValue;

	protected String refBillOfMaterialUUID;

	protected String refBillOfMaterialId;

	protected double amount;
	
	protected String amountLabel;

	protected String refUnitUUID;
	
	protected String refUnitName;	
	
	protected String approveBy;
	
	protected String approveById;
	
	protected String approveByName;
	
	protected String approveTime;
	
	protected String countApproveBy;
	
	protected String countApproveById;
	
	protected String countApproveByName;
	
	protected String countApproveTime;
	
	protected String refMainProdOrderUUID;
	
	protected String refMainProdOrderId;
	
	protected boolean refreshPlanFlag;
	
	protected String productionBatchNumber;
	
	public ProductionPlanUIModel(){
		
	} 

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
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

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}

	public String getSupplyTypeValue() {
		return supplyTypeValue;
	}

	public void setSupplyTypeValue(String supplyTypeValue) {
		this.supplyTypeValue = supplyTypeValue;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
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

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public String getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getRefBillOfMaterialUUID() {
		return refBillOfMaterialUUID;
	}

	public void setRefBillOfMaterialUUID(String refBillOfMaterialUUID) {
		this.refBillOfMaterialUUID = refBillOfMaterialUUID;
	}

	public String getRefBillOfMaterialId() {
		return refBillOfMaterialId;
	}

	public void setRefBillOfMaterialId(String refBillOfMaterialId) {
		this.refBillOfMaterialId = refBillOfMaterialId;
	}

	public double getSelfLeadTime() {
		return selfLeadTime;
	}

	public void setSelfLeadTime(double selfLeadTime) {
		this.selfLeadTime = selfLeadTime;
	}

	public double getComLeadTime() {
		return comLeadTime;
	}

	public void setComLeadTime(double comLeadTime) {
		this.comLeadTime = comLeadTime;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getCountApproveBy() {
		return countApproveBy;
	}

	public void setCountApproveBy(String countApproveBy) {
		this.countApproveBy = countApproveBy;
	}

	public String getCountApproveTime() {
		return countApproveTime;
	}

	public void setCountApproveTime(String countApproveTime) {
		this.countApproveTime = countApproveTime;
	}

	public String getRefMainProdOrderUUID() {
		return refMainProdOrderUUID;
	}

	public void setRefMainProdOrderUUID(String refMainProdOrderUUID) {
		this.refMainProdOrderUUID = refMainProdOrderUUID;
	}

	public boolean getRefreshPlanFlag() {
		return refreshPlanFlag;
	}

	public void setRefreshPlanFlag(boolean refreshPlanFlag) {
		this.refreshPlanFlag = refreshPlanFlag;
	}

	public String getApproveById() {
		return approveById;
	}

	public void setApproveById(String approveById) {
		this.approveById = approveById;
	}

	public String getApproveByName() {
		return approveByName;
	}

	public void setApproveByName(String approveByName) {
		this.approveByName = approveByName;
	}

	public String getCountApproveById() {
		return countApproveById;
	}

	public void setCountApproveById(String countApproveById) {
		this.countApproveById = countApproveById;
	}

	public String getCountApproveByName() {
		return countApproveByName;
	}

	public void setCountApproveByName(String countApproveByName) {
		this.countApproveByName = countApproveByName;
	}

	public String getRefMainProdOrderId() {
		return refMainProdOrderId;
	}

	public void setRefMainProdOrderId(String refMainProdOrderId) {
		this.refMainProdOrderId = refMainProdOrderId;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}
	
}

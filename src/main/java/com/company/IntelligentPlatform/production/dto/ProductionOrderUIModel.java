package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class ProductionOrderUIModel extends DocumentUIModel {

	@ISEDropDownResourceMapping(resouceMapping = "ProductionOrder_category", valueFieldName = "categoryValue")
	protected int category;

	protected String categoryValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "ProductionOrder_orderType", valueFieldName = "orderTypeValue")
	protected int orderType;
	
	protected String orderTypeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "ProductionOrder_doneStatus", valueFieldName = "doneStatusValue")
	protected int doneStatus;
	
	protected String doneStatusValue;

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
	
	protected String refBillOfMaterialNodeName;	

	protected double amount;
	
	protected String amountLabel;

	protected String refUnitUUID;
	
	protected String refUnitName;
	
	protected double actualAmount;
	
	protected String actualAmountLabel;
	
	protected String actualUnitUUID;
	
	protected String actualUnitName;
	
	protected String refWocUUID;
	
	protected String refWocId;
	
	protected String refWocName;
	
	protected String refPlanUUID;
	
	protected String refPlanId;
	
	protected String refPlanName;
	
	protected String reservedMatItemUUID;
	
	protected int reservedDocType;
	
	protected String reservedDocId;
	
	protected String reservedDocName;
	
	protected double grossCost;
	
	protected double grossCostLossRate;
	
	protected double grossCostActual;
	
    protected String approveBy;
	
	protected String approveTime;
	
	protected String approveById;
	
	protected String approveByName;
	
	protected String countApproveBy;
	
	protected String countApproveTime;

	protected String countApproveById;
	
	protected String countApproveByName;
	
	protected String productionBatchNumber;

	protected int genOrderItemMode;

	protected String genOrderItemModeValue;
	
	public ProductionOrderUIModel(){

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

	public int getDoneStatus() {
		return doneStatus;
	}

	public void setDoneStatus(int doneStatus) {
		this.doneStatus = doneStatus;
	}

	public String getDoneStatusValue() {
		return doneStatusValue;
	}

	public void setDoneStatusValue(String doneStatusValue) {
		this.doneStatusValue = doneStatusValue;
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

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getActualAmountLabel() {
		return actualAmountLabel;
	}

	public void setActualAmountLabel(String actualAmountLabel) {
		this.actualAmountLabel = actualAmountLabel;
	}

	public String getActualUnitUUID() {
		return actualUnitUUID;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeValue() {
		return orderTypeValue;
	}

	public void setOrderTypeValue(String orderTypeValue) {
		this.orderTypeValue = orderTypeValue;
	}

	public void setActualUnitUUID(String actualUnitUUID) {
		this.actualUnitUUID = actualUnitUUID;
	}

	public String getActualUnitName() {
		return actualUnitName;
	}

	public void setActualUnitName(String actualUnitName) {
		this.actualUnitName = actualUnitName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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

	public String getRefBillOfMaterialNodeName() {
		return refBillOfMaterialNodeName;
	}

	public void setRefBillOfMaterialNodeName(String refBillOfMaterialNodeName) {
		this.refBillOfMaterialNodeName = refBillOfMaterialNodeName;
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

	public String getRefWocUUID() {
		return refWocUUID;
	}

	public void setRefWocUUID(String refWocUUID) {
		this.refWocUUID = refWocUUID;
	}

	public String getRefWocId() {
		return refWocId;
	}

	public void setRefWocId(String refWocId) {
		this.refWocId = refWocId;
	}

	public String getRefWocName() {
		return refWocName;
	}

	public void setRefWocName(String refWocName) {
		this.refWocName = refWocName;
	}

	public String getRefPlanUUID() {
		return refPlanUUID;
	}

	public void setRefPlanUUID(String refPlanUUID) {
		this.refPlanUUID = refPlanUUID;
	}

	public String getRefPlanId() {
		return refPlanId;
	}

	public void setRefPlanId(String refPlanId) {
		this.refPlanId = refPlanId;
	}

	public String getRefPlanName() {
		return refPlanName;
	}

	public void setRefPlanName(String refPlanName) {
		this.refPlanName = refPlanName;
	}

	public String getReservedMatItemUUID() {
		return reservedMatItemUUID;
	}

	public void setReservedMatItemUUID(String reservedMatItemUUID) {
		this.reservedMatItemUUID = reservedMatItemUUID;
	}

	public int getReservedDocType() {
		return reservedDocType;
	}

	public void setReservedDocType(int reservedDocType) {
		this.reservedDocType = reservedDocType;
	}

	public String getReservedDocId() {
		return reservedDocId;
	}

	public void setReservedDocId(String reservedDocId) {
		this.reservedDocId = reservedDocId;
	}

	public String getReservedDocName() {
		return reservedDocName;
	}

	public void setReservedDocName(String reservedDocName) {
		this.reservedDocName = reservedDocName;
	}

	public double getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(double grossCost) {
		this.grossCost = grossCost;
	}

	public double getGrossCostLossRate() {
		return grossCostLossRate;
	}

	public void setGrossCostLossRate(double grossCostLossRate) {
		this.grossCostLossRate = grossCostLossRate;
	}

	public double getGrossCostActual() {
		return grossCostActual;
	}

	public void setGrossCostActual(double grossCostActual) {
		this.grossCostActual = grossCostActual;
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

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public int getGenOrderItemMode() {
		return genOrderItemMode;
	}

	public void setGenOrderItemMode(int genOrderItemMode) {
		this.genOrderItemMode = genOrderItemMode;
	}

	public String getGenOrderItemModeValue() {
		return genOrderItemModeValue;
	}

	public void setGenOrderItemModeValue(String genOrderItemModeValue) {
		this.genOrderItemModeValue = genOrderItemModeValue;
	}
}

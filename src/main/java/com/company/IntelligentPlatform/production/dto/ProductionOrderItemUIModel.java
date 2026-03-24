package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.production.model.ProductionOrderItem;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class ProductionOrderItemUIModel extends DocMatItemUIModel {

	@ISEUIModelMapping(fieldName = "planStartTime", seName = ProductionOrder.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, hiddenFlag = true, searchFlag = true, secId = ProductionOrder.SENAME, tabId = TABID_BASIC)
	protected String parentDocPlanStartTime;

	@ISEUIModelMapping(fieldName = "planEndTime", seName = ProductionOrder.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, hiddenFlag = true, searchFlag = true, secId = ProductionOrder.SENAME, tabId = TABID_BASIC)
	protected String parentDocPlanEndTime;

	protected String parentDocAmountLabel;
	
	protected String parentDocMaterialSKUUUID;

	protected String parentDocMaterialSKUId;

	protected String parentDocMaterialSKUName;

	protected String packageStandard;

	protected String supplyTypeValue;

	protected int supplyType;

	@ISEUIModelMapping(fieldName = "amountWithLossRate", seName = ProductionOrderItem.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, searchFlag = false, secId = ProductionOrderItem.NODENAME, tabId = TABID_BASIC)
	protected double amountWithLossRate;

	protected String amountWithLossRateLabel;

	@ISEUIModelMapping(fieldName = "actualAmount", seName = ProductionOrderItem.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, searchFlag = false, secId = ProductionOrderItem.NODENAME, tabId = TABID_BASIC, importParaFlag = true)
	protected double actualAmount;

	protected String actualAmountLabel;

	@ISEUIModelMapping(fieldName = "refUnitUUID", seName = ProductionOrderItem.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, searchFlag = false, secId = ProductionOrderItem.NODENAME, tabId = TABID_BASIC)
	protected String refUnitName;

	@ISEUIModelMapping(fieldName = "status", seName = ProductionOrderItem.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, searchFlag = true, secId = ProductionOrderItem.NODENAME, tabId = TABID_BASIC, exportParaFlag = true)
	@ISEDropDownResourceMapping(resouceMapping = "ProductionOrderItem_itemStatus", valueFieldName = "itemStatusValue")
	protected int itemStatus;

	@ISEUIModelMapping(fieldName = "refBOMItemUUID", seName = ProductionOrderItem.SENAME, nodeName = ProductionOrderItem.NODENAME, nodeInstID = ProductionOrderItem.NODENAME, searchFlag = false, secId = ProductionOrderItem.SENAME, tabId = TABID_BASIC, exportParaFlag = true)
	protected String refBOMItemUUID;

	protected String planStartPrepareDate;

	protected String planEndDate;

	protected String planStartDate;

	protected String actualEndDate;

	protected String actualStartDate;

	protected double selfLeadTime;

	protected double comLeadTime;

	protected double itemCostNoTax;

	protected double unitCostNoTax;

	protected double taxRate;

	protected double itemCostLossRate;

	protected double itemCostActual;
	
	protected double inStockAmount;
	
	protected double toPickAmount;
	
	protected double inProcessAmount;
	
	protected double availableAmount;
	
	protected double lackInPlanAmount;
	
	protected double pickedAmount;
	
	protected double suppliedAmount;
	
	protected String inStockAmountLabel;
	
	protected String toPickAmountLabel;
	
	protected String inProcessAmountLabel;
	
	protected String availableAmountLabel;
	
	protected String lackInPlanAmountLabel;
	
	protected String pickedAmountLabel;
	
	protected String suppliedAmountLabel;

	protected int pickStatus;

	protected String pickStatusValue;

	public ProductionOrderItemUIModel() {

	}

	public double getAmountWithLossRate() {
		return amountWithLossRate;
	}

	public void setAmountWithLossRate(double amountWithLossRate) {
		this.amountWithLossRate = amountWithLossRate;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getParentDocPlanStartTime() {
		return parentDocPlanStartTime;
	}

	public void setParentDocPlanStartTime(String parentDocPlanStartTime) {
		this.parentDocPlanStartTime = parentDocPlanStartTime;
	}

	public String getParentDocPlanEndTime() {
		return parentDocPlanEndTime;
	}

	public void setParentDocPlanEndTime(String parentDocPlanEndTime) {
		this.parentDocPlanEndTime = parentDocPlanEndTime;
	}

	public String getParentDocAmountLabel() {
		return parentDocAmountLabel;
	}

	public void setParentDocAmountLabel(String parentDocAmountLabel) {
		this.parentDocAmountLabel = parentDocAmountLabel;
	}

	public String getParentDocMaterialSKUUUID() {
		return parentDocMaterialSKUUUID;
	}

	public void setParentDocMaterialSKUUUID(String parentDocMaterialSKUUUID) {
		this.parentDocMaterialSKUUUID = parentDocMaterialSKUUUID;
	}

	public String getParentDocMaterialSKUId() {
		return parentDocMaterialSKUId;
	}

	public void setParentDocMaterialSKUId(String parentDocMaterialSKUId) {
		this.parentDocMaterialSKUId = parentDocMaterialSKUId;
	}

	public String getParentDocMaterialSKUName() {
		return parentDocMaterialSKUName;
	}

	public void setParentDocMaterialSKUName(String parentDocMaterialSKUName) {
		this.parentDocMaterialSKUName = parentDocMaterialSKUName;
	}

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public String getSupplyTypeValue() {
		return supplyTypeValue;
	}

	public void setSupplyTypeValue(String supplyTypeValue) {
		this.supplyTypeValue = supplyTypeValue;
	}

	public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemStatusValue() {
		return itemStatusValue;
	}

	public void setItemStatusValue(String itemStatusValue) {
		this.itemStatusValue = itemStatusValue;
	}

	public String getAmountWithLossRateLabel() {
		return amountWithLossRateLabel;
	}

	public void setAmountWithLossRateLabel(String amountWithLossRateLabel) {
		this.amountWithLossRateLabel = amountWithLossRateLabel;
	}

	public String getActualAmountLabel() {
		return actualAmountLabel;
	}

	public void setActualAmountLabel(String actualAmountLabel) {
		this.actualAmountLabel = actualAmountLabel;
	}

	public String getRefBOMItemUUID() {
		return refBOMItemUUID;
	}

	public void setRefBOMItemUUID(String refBOMItemUUID) {
		this.refBOMItemUUID = refBOMItemUUID;
	}

	public String getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(String planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public double getComLeadTime() {
		return comLeadTime;
	}

	public void setComLeadTime(double comLeadTime) {
		this.comLeadTime = comLeadTime;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public double getSelfLeadTime() {
		return selfLeadTime;
	}

	public void setSelfLeadTime(double selfLeadTime) {
		this.selfLeadTime = selfLeadTime;
	}

	public double getItemCostNoTax() {
		return itemCostNoTax;
	}

	public void setItemCostNoTax(double itemCostNoTax) {
		this.itemCostNoTax = itemCostNoTax;
	}

	public double getUnitCostNoTax() {
		return unitCostNoTax;
	}

	public void setUnitCostNoTax(double unitCostNoTax) {
		this.unitCostNoTax = unitCostNoTax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getItemCostLossRate() {
		return itemCostLossRate;
	}

	public void setItemCostLossRate(double itemCostLossRate) {
		this.itemCostLossRate = itemCostLossRate;
	}

	public double getItemCostActual() {
		return itemCostActual;
	}

	public void setItemCostActual(double itemCostActual) {
		this.itemCostActual = itemCostActual;
	}

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getLackInPlanAmount() {
		return lackInPlanAmount;
	}

	public void setLackInPlanAmount(double lackInPlanAmount) {
		this.lackInPlanAmount = lackInPlanAmount;
	}

	public double getPickedAmount() {
		return pickedAmount;
	}

	public void setPickedAmount(double pickedAmount) {
		this.pickedAmount = pickedAmount;
	}

	public double getToPickAmount() {
		return toPickAmount;
	}

	public void setToPickAmount(double toPickAmount) {
		this.toPickAmount = toPickAmount;
	}

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
	}

	public String getInStockAmountLabel() {
		return inStockAmountLabel;
	}

	public void setInStockAmountLabel(String inStockAmountLabel) {
		this.inStockAmountLabel = inStockAmountLabel;
	}

	public String getToPickAmountLabel() {
		return toPickAmountLabel;
	}

	public void setToPickAmountLabel(String toPickAmountLabel) {
		this.toPickAmountLabel = toPickAmountLabel;
	}

	public String getInProcessAmountLabel() {
		return inProcessAmountLabel;
	}

	public void setInProcessAmountLabel(String inProcessAmountLabel) {
		this.inProcessAmountLabel = inProcessAmountLabel;
	}

	public String getAvailableAmountLabel() {
		return availableAmountLabel;
	}

	public void setAvailableAmountLabel(String availableAmountLabel) {
		this.availableAmountLabel = availableAmountLabel;
	}

	public String getLackInPlanAmountLabel() {
		return lackInPlanAmountLabel;
	}

	public void setLackInPlanAmountLabel(String lackInPlanAmountLabel) {
		this.lackInPlanAmountLabel = lackInPlanAmountLabel;
	}

	public String getPickedAmountLabel() {
		return pickedAmountLabel;
	}

	public void setPickedAmountLabel(String pickedAmountLabel) {
		this.pickedAmountLabel = pickedAmountLabel;
	}

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
	}

	public String getSuppliedAmountLabel() {
		return suppliedAmountLabel;
	}

	public void setSuppliedAmountLabel(String suppliedAmountLabel) {
		this.suppliedAmountLabel = suppliedAmountLabel;
	}

	public int getPickStatus()
	{
		return pickStatus;
	}

	public void setPickStatus(final int pickStatus)
	{
		this.pickStatus = pickStatus;
	}

	public String getPickStatusValue()
	{
		return pickStatusValue;
	}

	public void setPickStatusValue(final String pickStatusValue)
	{
		this.pickStatusValue = pickStatusValue;
	}
}

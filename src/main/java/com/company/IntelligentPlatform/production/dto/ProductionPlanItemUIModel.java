package com.company.IntelligentPlatform.production.dto;

import java.util.Date;

import com.company.IntelligentPlatform.production.model.ProductionPlanItem;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelHeader;

@ISEUIModelHeader(i18nFileName = ProductionPlanItem.NODENAME, modelName = ProductionPlanItem.NODENAME)
public class ProductionPlanItemUIModel extends DocMatItemUIModel {

	protected String planId;

	protected String planPlanStartTime;

	protected String planPlanEndTime;

	protected String planAmountLabel;

	protected String planMaterialSKUId;

	protected String planMaterialSKUName;
	
	protected String packageStandard;
	
	protected String supplyTypeValue;
	
	protected int supplyType;

	protected double amountWithLossRate;
	
	protected String amountWithLossRateLabel;

	protected double actualAmount;
	
	protected String actualAmountLabel;
	
	protected String refBOMItemUUID;
	
	protected String planStartPrepareDate;
	
	protected String planEndDate;
	
	protected String planStartDate;
	
    protected String actualEndDate;
	
	protected String actualStartDate;
	
	protected double selfLeadTime;
	
	protected double comLeadTime;
	
	protected double inStockAmount;
	
	protected double inProcessAmount;
	
	protected double toPickAmount;
	
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

	public ProductionPlanItemUIModel() {

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
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanPlanStartTime() {
		return planPlanStartTime;
	}

	public void setPlanPlanStartTime(String planPlanStartTime) {
		this.planPlanStartTime = planPlanStartTime;
	}

	public String getPlanPlanEndTime() {
		return planPlanEndTime;
	}

	public void setPlanPlanEndTime(String planPlanEndTime) {
		this.planPlanEndTime = planPlanEndTime;
	}

	public String getPlanAmountLabel() {
		return planAmountLabel;
	}

	public void setPlanAmountLabel(String planAmountLabel) {
		this.planAmountLabel = planAmountLabel;
	}

	public String getPlanMaterialSKUId() {
		return planMaterialSKUId;
	}

	public void setPlanMaterialSKUId(String planMaterialSKUId) {
		this.planMaterialSKUId = planMaterialSKUId;
	}

	public String getPlanMaterialSKUName() {
		return planMaterialSKUName;
	}

	public void setPlanMaterialSKUName(String planMaterialSKUName) {
		this.planMaterialSKUName = planMaterialSKUName;
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

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public double getToPickAmount() {
		return toPickAmount;
	}

	public void setToPickAmount(double toPickAmount) {
		this.toPickAmount = toPickAmount;
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

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
	}

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
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

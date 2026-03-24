package com.company.IntelligentPlatform.production.model;

import java.time.LocalDateTime;



import com.company.IntelligentPlatform.common.model.DocMatItemNode;

public class ProdItemReqProposalTemplate extends DocMatItemNode implements
		Comparable<ProdItemReqProposalTemplate> {

	protected int itemIndex;

	protected String refWarehouseUUID;

	protected String refBOMItemUUID;

	protected int documentType;

	protected double storeAmount;

	protected String storeUnitUUID;

	protected String refStoreItemUUID;

	protected LocalDateTime planStartDate;

	protected LocalDateTime actualStartDate;

	protected LocalDateTime planStartPrepareDate;

	protected LocalDateTime actualStartPrepareDate;

	protected LocalDateTime planEndDate;

	protected LocalDateTime actualEndDate;

	protected double selfLeadTime;

	protected double comLeadTime;

    protected double inStockAmount;

	protected double inProcessAmount;

	protected double toPickAmount;

	protected double availableAmount;

	protected double lackInPlanAmount;

	protected double pickedAmount;

	protected double suppliedAmount;

	protected int pickStatus;

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefBOMItemUUID() {
		return refBOMItemUUID;
	}

	public void setRefBOMItemUUID(String refBOMItemUUID) {
		this.refBOMItemUUID = refBOMItemUUID;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public double getStoreAmount() {
		return storeAmount;
	}

	public void setStoreAmount(double storeAmount) {
		this.storeAmount = storeAmount;
	}

	public String getStoreUnitUUID() {
		return storeUnitUUID;
	}

	public void setStoreUnitUUID(String storeUnitUUID) {
		this.storeUnitUUID = storeUnitUUID;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(final String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public LocalDateTime getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(LocalDateTime planStartDate) {
		this.planStartDate = planStartDate;
	}

	public LocalDateTime getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(LocalDateTime actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public LocalDateTime getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(LocalDateTime planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public LocalDateTime getActualStartPrepareDate() {
		return actualStartPrepareDate;
	}

	public void setActualStartPrepareDate(LocalDateTime actualStartPrepareDate) {
		this.actualStartPrepareDate = actualStartPrepareDate;
	}

	public LocalDateTime getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(LocalDateTime planEndDate) {
		this.planEndDate = planEndDate;
	}

	public LocalDateTime getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(LocalDateTime actualEndDate) {
		this.actualEndDate = actualEndDate;
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

	public double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
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

	public double getSuppliedAmount() {
		return suppliedAmount;
	}

	public void setSuppliedAmount(double suppliedAmount) {
		this.suppliedAmount = suppliedAmount;
	}

	public int getPickStatus()
	{
		return pickStatus;
	}

	public void setPickStatus(final int pickStatus)
	{
		this.pickStatus = pickStatus;
	}

	@Override
	public int compareTo(ProdItemReqProposalTemplate other) {
		return this.getDocumentType() - other.getDocumentType();
	}

}

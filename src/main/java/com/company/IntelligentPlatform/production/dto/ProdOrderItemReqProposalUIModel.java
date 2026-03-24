package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class ProdOrderItemReqProposalUIModel extends DocMatItemUIModel {

	protected int itemIndex;

	protected String refUnitName;

	protected int documentType;
	
	protected String documentTypeValue;

	protected String refDocumentId;

	protected String itemStatusValue;
	
	protected String parentItemId;
	
	protected String orderId;
	
	protected int orderStatus;
	
	protected String orderStatusValue;
	
	protected String refUUID;
	
	protected String planStartPrepareDate;
	
	protected String actualStartPrepareDate;
	
	protected String planStartDate;
	
	protected String planEndDate;
	
	protected String actualStartDate;
	
	protected String actualEndDate;

	protected double selfLeadTime;

	protected double comLeadTime;

	protected String refBOMItemUUID;
	
	protected double storeAmount;
	
	protected String storeUnitUUID;
	
	protected String refWarehouseUUID;
	
	protected String refWarehouseId;
	
	protected String refWarehouseName;
	
    protected double inStockAmount;
	
	protected double toPickAmount;
	
	protected double inProcessAmount;
	
	protected double availableAmount;
	
	protected double pickedAmount;
	
	protected double suppliedAmount;
	
    protected String inStockAmountLabel;
	
	protected String toPickAmountLabel;
	
	protected String inProcessAmountLabel;
	
	protected String availableAmountLabel;
	
	protected String pickedAmountLabel;
	
	protected String suppliedAmountLabel;

	protected int pickStatus;

	protected String pickStatusValue;

	public ProdOrderItemReqProposalUIModel() {

	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public String getRefDocumentId() {
		return refDocumentId;
	}

	public void setRefDocumentId(String refDocumentId) {
		this.refDocumentId = refDocumentId;
	}
	
	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}
	
	public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
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

	public String getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(String planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public String getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getActualStartPrepareDate() {
		return actualStartPrepareDate;
	}

	public void setActualStartPrepareDate(String actualStartPrepareDate) {
		this.actualStartPrepareDate = actualStartPrepareDate;
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

	public String getRefBOMItemUUID() {
		return refBOMItemUUID;
	}

	public void setRefBOMItemUUID(String refBOMItemUUID) {
		this.refBOMItemUUID = refBOMItemUUID;
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

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
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

	public double getInProcessAmount() {
		return inProcessAmount;
	}

	public void setInProcessAmount(double inProcessAmount) {
		this.inProcessAmount = inProcessAmount;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getPickedAmount() {
		return pickedAmount;
	}

	public void setPickedAmount(double pickedAmount) {
		this.pickedAmount = pickedAmount;
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

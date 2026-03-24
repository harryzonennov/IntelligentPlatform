package com.company.IntelligentPlatform.production.dto;

import java.time.LocalDateTime;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdOrderItemExcelProposal extends SEUIComModel{
	
	public static final int ITEM_CATE_PRODITEM = 1;
	
	public static final int ITEM_CATE_PRODITEMREQ = 2;	
	
	public static final int ITEM_CATE_OUTBOUND = 3;
	
	public static final int ITEM_CATE_PURCHASE = 4;
	
	public static final int ITEM_CATE_SUBPROD = 5;
	
	protected int itemCategory;
	
	protected int refDocumentType;
	
	protected String prefix;
	
	protected String outMaterialSKUID;
	
	protected String outMaterialSKULabel;
	
	protected String index;
	
	protected String itemTitleLabel;
	
	protected String warehouseID;
	
	protected String warehouseTitle;
	
	protected String warehouseStoreAmountLabel;
	
	protected String warehouseStoreAmount;
	
	protected int itemStatus;
	
	protected String itemStatusValue;
	
	protected String itemStatusLabel;
	
	protected String amount;
	
	protected String amountLabel;
	
	protected String actualAmount;
	
	protected String actualAmountLabel;
	
	protected LocalDateTime planStartPrepareDate;
	
	protected String planStartPrepareDateStr;
	
	protected LocalDateTime planStartDate;
	
	protected String planStartDateStr;
	
    protected LocalDateTime planEndDate;
	
	protected String planEndDateStr;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getRefDocumentType() {
		return refDocumentType;
	}

	public void setRefDocumentType(int refDocumentType) {
		this.refDocumentType = refDocumentType;
	}

	public String getOutMaterialSKUID() {
		return outMaterialSKUID;
	}

	public void setOutMaterialSKUID(String outMaterialSKUID) {
		this.outMaterialSKUID = outMaterialSKUID;
	}

	public String getOutMaterialSKULabel() {
		return outMaterialSKULabel;
	}

	public void setOutMaterialSKULabel(String outMaterialSKULabel) {
		this.outMaterialSKULabel = outMaterialSKULabel;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getItemTitleLabel() {
		return itemTitleLabel;
	}

	public void setItemTitleLabel(String itemTitleLabel) {
		this.itemTitleLabel = itemTitleLabel;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseTitle() {
		return warehouseTitle;
	}

	public void setWarehouseTitle(String warehouseTitle) {
		this.warehouseTitle = warehouseTitle;
	}

	public String getWarehouseStoreAmountLabel() {
		return warehouseStoreAmountLabel;
	}

	public void setWarehouseStoreAmountLabel(String warehouseStoreAmountLabel) {
		this.warehouseStoreAmountLabel = warehouseStoreAmountLabel;
	}

	public String getWarehouseStoreAmount() {
		return warehouseStoreAmount;
	}

	public void setWarehouseStoreAmount(String warehouseStoreAmount) {
		this.warehouseStoreAmount = warehouseStoreAmount;
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

	public String getItemStatusLabel() {
		return itemStatusLabel;
	}

	public void setItemStatusLabel(String itemStatusLabel) {
		this.itemStatusLabel = itemStatusLabel;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountLabel() {
		return amountLabel;
	}

	public void setAmountLabel(String amountLabel) {
		this.amountLabel = amountLabel;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getActualAmountLabel() {
		return actualAmountLabel;
	}

	public void setActualAmountLabel(String actualAmountLabel) {
		this.actualAmountLabel = actualAmountLabel;
	}

	public LocalDateTime getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(LocalDateTime planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public String getPlanStartPrepareDateStr() {
		return planStartPrepareDateStr;
	}

	public void setPlanStartPrepareDateStr(String planStartPrepareDateStr) {
		this.planStartPrepareDateStr = planStartPrepareDateStr;
	}

	public LocalDateTime getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(LocalDateTime planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getPlanStartDateStr() {
		return planStartDateStr;
	}

	public void setPlanStartDateStr(String planStartDateStr) {
		this.planStartDateStr = planStartDateStr;
	}

	public LocalDateTime getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(LocalDateTime planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getPlanEndDateStr() {
		return planEndDateStr;
	}

	public void setPlanEndDateStr(String planEndDateStr) {
		this.planEndDateStr = planEndDateStr;
	}

}

package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdOrderHeaderExcelProposal extends SEUIComModel{
	
	public static final int ITEM_CATE_PRODITEM = 1;
	
	public static final int ITEM_CATE_PRODITEMREQ = 2;	
	
	protected int itemCategory;
	
	protected int refDocumentType;
	
	protected String orderID;
	
	protected String orderIDLabel;
	
	protected String outMaterialSKUID;
	
	protected String outMaterialSKULabel;
	
	protected String indexLabel;
	
	protected String orderTitleLabel;
	
	protected String warehouseID;
	
	protected String warehouseTitle;
	
	protected String warehouseStoreAmountLabel;
	
	protected String warehouseStoreAmount;
	
	protected String itemStatus;
	
	protected String itemStatusLabel;
	
	protected String amount;
	
	protected String amountLabel;
	
	protected String actualAmount;
	
	protected String actualAmountLabel;

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getRefDocumentType() {
		return refDocumentType;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderIDLabel() {
		return orderIDLabel;
	}

	public void setOrderIDLabel(String orderIDLabel) {
		this.orderIDLabel = orderIDLabel;
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

	public String getIndexLabel() {
		return indexLabel;
	}

	public void setIndexLabel(String indexLabel) {
		this.indexLabel = indexLabel;
	}

	public String getOrderTitleLabel() {
		return orderTitleLabel;
	}

	public void setOrderTitleLabel(String orderTitleLabel) {
		this.orderTitleLabel = orderTitleLabel;
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

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
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
	

}

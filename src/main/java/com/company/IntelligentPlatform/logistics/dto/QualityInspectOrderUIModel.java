package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class QualityInspectOrderUIModel extends DocumentUIModel {

	@ISEDropDownResourceMapping(resouceMapping = "QualityInspectOrder_checkStatus", valueFieldName = "null")
	protected int checkStatus;
	
	protected String checkStatusValue;

	protected String checkResult;

	@ISEDropDownResourceMapping(resouceMapping = "QualityInspectOrder_reservedDocType", valueFieldName = "null")
	protected int reservedDocType;

	protected String reservedDocTypeValue;
	
	protected String reservedDocUUID;
	
	protected String reservedDocId;
	
	protected String reservedDocName;

	protected String checkDate;

	@ISEDropDownResourceMapping(resouceMapping = "QualityInspectOrder_category", valueFieldName = "null")
	protected int category;

	protected String categoryValue;

	@ISEDropDownResourceMapping(resouceMapping = "QualityInspectOrder_inspectType", valueFieldName = "null")
	protected int inspectType;

	protected String inspectTypeValue;

	protected double grossPrice;
	
	protected String refWarehouseAreaUUID;
	
	protected String refWarehouseAreaId;
	
	protected String refWarehouseUUID;
	
	protected String refWarehouseName;
	
	protected String refWarehouseId;

	protected String purchaseBatchNumber;
	
	protected String productionBatchNumber;

	public int getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckResult() {
		return this.checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public int getReservedDocType() {
		return this.reservedDocType;
	}

	public void setReservedDocType(int reservedDocType) {
		this.reservedDocType = reservedDocType;
	}

	public String getReservedDocUUID() {
		return reservedDocUUID;
	}

	public void setReservedDocUUID(String reservedDocUUID) {
		this.reservedDocUUID = reservedDocUUID;
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

	public String getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getInspectType() {
		return this.inspectType;
	}

	public void setInspectType(int inspectType) {
		this.inspectType = inspectType;
	}

	public String getCheckStatusValue() {
		return checkStatusValue;
	}

	public void setCheckStatusValue(String checkStatusValue) {
		this.checkStatusValue = checkStatusValue;
	}

	public String getReservedDocTypeValue() {
		return reservedDocTypeValue;
	}

	public void setReservedDocTypeValue(String reservedDocTypeValue) {
		this.reservedDocTypeValue = reservedDocTypeValue;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getInspectTypeValue() {
		return inspectTypeValue;
	}

	public void setInspectTypeValue(String inspectTypeValue) {
		this.inspectTypeValue = inspectTypeValue;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseName() {
		return refWarehouseName;
	}

	public void setRefWarehouseName(String refWarehouseName) {
		this.refWarehouseName = refWarehouseName;
	}

	public String getRefWarehouseId() {
		return refWarehouseId;
	}

	public void setRefWarehouseId(String refWarehouseId) {
		this.refWarehouseId = refWarehouseId;
	}

	public String getRefWarehouseAreaId() {
		return refWarehouseAreaId;
	}

	public void setRefWarehouseAreaId(String refWarehouseAreaId) {
		this.refWarehouseAreaId = refWarehouseAreaId;
	}

	public String getPurchaseBatchNumber() {
		return purchaseBatchNumber;
	}

	public void setPurchaseBatchNumber(String purchaseBatchNumber) {
		this.purchaseBatchNumber = purchaseBatchNumber;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

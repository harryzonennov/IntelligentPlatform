package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class ProdOrderReportUIModel extends DocumentUIModel {

	@ISEDropDownResourceMapping(resouceMapping = "ProdOrderReport_reportStatus", valueFieldName = "")
	protected int reportStatus;
	
	protected String reportStatusValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "ProdOrderReport_reportCategory", valueFieldName = "")
	protected int reportCategory;
	
	protected String reportCategoryValue;

	protected String reportTime;

	protected String reportedBy;

	protected String reportByName;

	protected String reportById;

	protected String orderId;
	
	protected String refMaterialSKUUUID;
	
	protected String refMaterialSKUId;
	
	protected String refMaterialSKUName;
	
	protected double grossAmount;
	
	protected String grossAmountValue;
	
	protected double grossPrice;

	protected String orderName;
	
	protected String refUnitUUID;
	
	protected String refUnitName;
	
	protected String productionBatchNumber;

	public int getReportStatus() {
		return this.reportStatus;
	}

	public String getReportStatusValue() {
		return reportStatusValue;
	}

	public void setReportStatusValue(String reportStatusValue) {
		this.reportStatusValue = reportStatusValue;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public int getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(int reportCategory) {
		this.reportCategory = reportCategory;
	}

	public String getReportCategoryValue() {
		return reportCategoryValue;
	}

	public void setReportCategoryValue(String reportCategoryValue) {
		this.reportCategoryValue = reportCategoryValue;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReportTime() {
		return this.reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getReportedBy() {
		return this.reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getReportByName() {
		return this.reportByName;
	}

	public void setReportByName(String reportByName) {
		this.reportByName = reportByName;
	}

	public String getReportById() {
		return this.reportById;
	}

	public void setReportById(String reportById) {
		this.reportById = reportById;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getGrossAmountValue() {
		return grossAmountValue;
	}

	public void setGrossAmountValue(String grossAmountValue) {
		this.grossAmountValue = grossAmountValue;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
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

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

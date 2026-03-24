package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdOrderReportItemUIModel extends SEUIComModel {

	protected double amount;

	protected String refTemplateSKUUUID;

	protected String refInboundUUID;

	protected int processIndex;

	protected String refUnitUUID;
	
	protected String refUnitName;
	
	protected double itemPrice;
	
	protected double unitPrice;

	protected String productionBatchNumber;

	protected String refSerialId;

	protected String refMaterialSKUUUID;

	protected String refMaterialSKUId;

	protected String refMaterialSKUName;
	
	protected String packageStandard;
	
	protected String reservedMatItemUUID;
	
	protected int reservedDocType;
	
	protected String reservedDocId;
	
	protected String reservedDocName;
	
    protected int prevDocType;
	
	protected String prevDocTypeValue;

	protected int nextDocType;
	
	protected String nextDocTypeValue;

	protected String nextDocMatItemUUID;

	protected String prevDocMatItemUUID;
	
	protected String prevDocId;
	
	protected String prevDocName;
	
	protected String nextDocId;
	
	protected String nextDocName;

	protected String reportId;
	
	protected String reportName;
	
	protected String reportTime;
	
	protected int reportStatus;
	
	protected String reportStatusValue;

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefTemplateSKUUUID() {
		return this.refTemplateSKUUUID;
	}

	public void setRefTemplateSKUUUID(String refTemplateSKUUUID) {
		this.refTemplateSKUUUID = refTemplateSKUUUID;
	}

	public String getRefInboundUUID() {
		return this.refInboundUUID;
	}

	public void setRefInboundUUID(String refInboundUUID) {
		this.refInboundUUID = refInboundUUID;
	}

	public int getProcessIndex() {
		return this.processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefUnitUUID() {
		return this.refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getProductionBatchNumber() {
		return this.productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(String refSerialId) {
		this.refSerialId = refSerialId;
	}

	public String getRefMaterialSKUUUID() {
		return this.refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
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

	public String getPackageStandard() {
		return packageStandard;
	}

	public void setPackageStandard(String packageStandard) {
		this.packageStandard = packageStandard;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
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

	public int getPrevDocType() {
		return prevDocType;
	}

	public void setPrevDocType(int prevDocType) {
		this.prevDocType = prevDocType;
	}

	public String getPrevDocTypeValue() {
		return prevDocTypeValue;
	}

	public void setPrevDocTypeValue(String prevDocTypeValue) {
		this.prevDocTypeValue = prevDocTypeValue;
	}

	public int getNextDocType() {
		return nextDocType;
	}

	public void setNextDocType(int nextDocType) {
		this.nextDocType = nextDocType;
	}

	public String getNextDocTypeValue() {
		return nextDocTypeValue;
	}

	public void setNextDocTypeValue(String nextDocTypeValue) {
		this.nextDocTypeValue = nextDocTypeValue;
	}

	public String getNextDocMatItemUUID() {
		return nextDocMatItemUUID;
	}

	public void setNextDocMatItemUUID(String nextDocMatItemUUID) {
		this.nextDocMatItemUUID = nextDocMatItemUUID;
	}

	public String getPrevDocMatItemUUID() {
		return prevDocMatItemUUID;
	}

	public void setPrevDocMatItemUUID(String prevDocMatItemUUID) {
		this.prevDocMatItemUUID = prevDocMatItemUUID;
	}

	public String getPrevDocId() {
		return prevDocId;
	}

	public void setPrevDocId(String prevDocId) {
		this.prevDocId = prevDocId;
	}

	public String getPrevDocName() {
		return prevDocName;
	}

	public void setPrevDocName(String prevDocName) {
		this.prevDocName = prevDocName;
	}

	public String getNextDocId() {
		return nextDocId;
	}

	public void setNextDocId(String nextDocId) {
		this.nextDocId = nextDocId;
	}

	public String getNextDocName() {
		return nextDocName;
	}

	public void setNextDocName(String nextDocName) {
		this.nextDocName = nextDocName;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}	

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public int getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getReportStatusValue() {
		return reportStatusValue;
	}

	public void setReportStatusValue(String reportStatusValue) {
		this.reportStatusValue = reportStatusValue;
	}

}

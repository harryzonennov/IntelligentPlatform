package com.company.IntelligentPlatform.common.controller;

public class ServiceDocumentExtendUIModel extends SEUIComModel{

	protected int status;
	
	protected String statusValue;
	
	protected int processIndex;
	
	protected int priorityCode;
	
	protected int documentType;
	
	protected String documentTypeValue;
	
	protected String priorityCodeValue;
	
	protected String refMaterialSKUUUID;
	
	protected String refMaterialSKUName;
	
	protected String refMaterialSKUId;
	
	protected String serialId;
	
	protected double amount;
	
	protected String amountLabel;
	
	protected String productionBatchNumber;
	
	protected String referenceDate;
	
	protected int materialStatus;
	
	protected String materialStatusValue;
	
	protected SEUIComModel refUIModel;

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

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public int getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
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

	public String getPriorityCodeValue() {
		return priorityCodeValue;
	}

	public void setPriorityCodeValue(String priorityCodeValue) {
		this.priorityCodeValue = priorityCodeValue;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public String getRefMaterialSKUName() {
		return refMaterialSKUName;
	}

	public void setRefMaterialSKUName(String refMaterialSKUName) {
		this.refMaterialSKUName = refMaterialSKUName;
	}

	public String getRefMaterialSKUId() {
		return refMaterialSKUId;
	}

	public void setRefMaterialSKUId(String refMaterialSKUId) {
		this.refMaterialSKUId = refMaterialSKUId;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public String getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}

	public SEUIComModel getRefUIModel() {
		return refUIModel;
	}

	public void setRefUIModel(SEUIComModel refUIModel) {
		this.refUIModel = refUIModel;
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

	public int getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(int materialStatus) {
		this.materialStatus = materialStatus;
	}

	public String getMaterialStatusValue() {
		return materialStatusValue;
	}

	public void setMaterialStatusValue(String materialStatusValue) {
		this.materialStatusValue = materialStatusValue;
	}	

}

package com.company.IntelligentPlatform.common.controller;


public class DocActionNodeUIModel extends SEUIComModel {

	public static final String FIELD_executedByUserName = "executedByUserName";

	public static final String FIELD_executedByUserId = "executedByUserId";

	protected int processIndex;

	protected int docActionCode;

	protected int flatNodeSwitch;

	protected String docActionCodeLabel;

	protected String executionTime;

	protected String executedByUUID;

	protected String executedByUserName;

	protected String executedByUserId;

	protected int documentType;

	protected String documentTypeValue;

	protected String refDocMatItemUUID;

	protected String refDocumentUUID;

	protected String documentId;

	protected String documentName;

	protected int documentStatus;

	protected String documentStatusValue;

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public int getFlatNodeSwitch() {
		return flatNodeSwitch;
	}

	public void setFlatNodeSwitch(int flatNodeSwitch) {
		this.flatNodeSwitch = flatNodeSwitch;
	}

	public int getDocActionCode() {
		return docActionCode;
	}

	public void setDocActionCode(int docActionCode) {
		this.docActionCode = docActionCode;
	}

	public String getDocActionCodeLabel() {
		return docActionCodeLabel;
	}

	public void setDocActionCodeLabel(String docActionCodeLabel) {
		this.docActionCodeLabel = docActionCodeLabel;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public String getExecutedByUUID() {
		return executedByUUID;
	}

	public void setExecutedByUUID(String executedByUUID) {
		this.executedByUUID = executedByUUID;
	}

	public String getExecutedByUserName() {
		return executedByUserName;
	}

	public void setExecutedByUserName(String executedByUserName) {
		this.executedByUserName = executedByUserName;
	}

	public String getExecutedByUserId() {
		return executedByUserId;
	}

	public void setExecutedByUserId(String executedByUserId) {
		this.executedByUserId = executedByUserId;
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

	public String getRefDocMatItemUUID() {
		return refDocMatItemUUID;
	}

	public void setRefDocMatItemUUID(String refDocMatItemUUID) {
		this.refDocMatItemUUID = refDocMatItemUUID;
	}

	public String getRefDocumentUUID() {
		return refDocumentUUID;
	}

	public void setRefDocumentUUID(String refDocumentUUID) {
		this.refDocumentUUID = refDocumentUUID;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public int getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(int documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getDocumentStatusValue() {
		return documentStatusValue;
	}

	public void setDocumentStatusValue(String documentStatusValue) {
		this.documentStatusValue = documentStatusValue;
	}
}

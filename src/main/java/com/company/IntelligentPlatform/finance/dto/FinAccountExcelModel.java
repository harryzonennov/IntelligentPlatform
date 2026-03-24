package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class FinAccountExcelModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "id", seName = "FinAccount", nodeName = "ROOT")
	protected String id;

	@ISEUIModelMapping(fieldName = "name;", seName = "Account", nodeName = "ROOT")
	protected String accountObjectName;

	@ISEUIModelMapping(showOnEditor = false)
	protected String paymentTypeValue;

	@ISEUIModelMapping(fieldName = "name", seName = "FinAccountTitle", nodeName = "ROOT")
	protected String accountTitleName;
	
	@ISEUIModelMapping(showOnEditor = false)
	protected String documentTypeValue;

	@ISEUIModelMapping(fieldName = "documentId", seName = "FinAccount", nodeName = "ROOT")
	protected String documentId;

	protected String dateStr;

	@ISEUIModelMapping(fieldName = "finAccountType", seName = "FinAccountTitle", nodeName = "ROOT", showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_accountType", valueFieldName = "accountTypeValue")
	protected int finAccountType;

	@ISEUIModelMapping(showOnEditor = false)
	protected String finAccountTypeValue;

	protected String auditStatusValue;

	protected String verifyStatusValue;

	protected String recordStatusValue;

	protected double amount;
	
	@ISEUIModelMapping(fieldName = "recordedAmount", seName = "FinAccount", nodeName = "ROOT")
	protected double recordedAmount;
	
	@ISEUIModelMapping(fieldName = "verifyTime", seName = "FinAccount", nodeName = "ROOT")
	protected double toRecordAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountObjectName() {
		return accountObjectName;
	}

	public void setAccountObjectName(String accountObjectName) {
		this.accountObjectName = accountObjectName;
	}

	public String getPaymentTypeValue() {
		return paymentTypeValue;
	}

	public void setPaymentTypeValue(String paymentTypeValue) {
		this.paymentTypeValue = paymentTypeValue;
	}

	public String getAccountTitleName() {
		return accountTitleName;
	}

	public void setAccountTitleName(String accountTitleName) {
		this.accountTitleName = accountTitleName;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public int getFinAccountType() {
		return finAccountType;
	}

	public void setFinAccountType(int finAccountType) {
		this.finAccountType = finAccountType;
	}

	public String getFinAccountTypeValue() {
		return finAccountTypeValue;
	}

	public void setFinAccountTypeValue(String finAccountTypeValue) {
		this.finAccountTypeValue = finAccountTypeValue;
	}

	public String getAuditStatusValue() {
		return auditStatusValue;
	}

	public void setAuditStatusValue(String auditStatusValue) {
		this.auditStatusValue = auditStatusValue;
	}

	public String getVerifyStatusValue() {
		return verifyStatusValue;
	}

	public void setVerifyStatusValue(String verifyStatusValue) {
		this.verifyStatusValue = verifyStatusValue;
	}

	public String getRecordStatusValue() {
		return recordStatusValue;
	}

	public void setRecordStatusValue(String recordStatusValue) {
		this.recordStatusValue = recordStatusValue;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRecordedAmount() {
		return recordedAmount;
	}

	public void setRecordedAmount(double recordedAmount) {
		this.recordedAmount = recordedAmount;
	}

	public double getToRecordAmount() {
		return toRecordAmount;
	}

	public void setToRecordAmount(double toRecordAmount) {
		this.toRecordAmount = toRecordAmount;
	}

}

package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class FinAccountUIModel extends DocumentUIModel {
	
	@ISEUIModelMapping(fieldName = "documentType;", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountSearch_documentType", valueFieldName = "documentTypeValue")
	protected int documentType;

	@ISEUIModelMapping(fieldName = "name;", seName = "Account", nodeName = FinAccount.NODENAME)
	protected String accountObjectName;
	
	@ISEUIModelMapping(fieldName = "name;", seName = "Account", nodeName = FinAccount.NODENAME)
	protected String accountObjectId;

	@ISEUIModelMapping(fieldName = "paymentType;", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccount_paymentType", valueFieldName = "paymentTypeValue")
	protected int paymentType;

	@ISEUIModelMapping(showOnEditor = false)
	protected String paymentTypeValue;

	@ISEUIModelMapping(fieldName = "accountTitleUUID", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String accountTitleUUID;

	@ISEUIModelMapping(fieldName = "name", seName = "FinAccountTitle", nodeName = FinAccount.NODENAME)
	protected String accountTitleName;

	@ISEUIModelMapping(fieldName = "documentID", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected String documentId;

	@ISEUIModelMapping(fieldName = "note", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, textAreaFlag = true, showOnList = false)
	protected String note;

	protected String dateStr;

	@ISEUIModelMapping(fieldName = "finAccountType", seName = FinAccountTitle.SENAME, nodeName = FinAccountTitle.NODENAME, showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccountTitle_finAccountType", valueFieldName = "finAccountTypeValue")
	protected int finAccountType;

	@ISEUIModelMapping(showOnEditor = false)
	protected String finAccountTypeValue;

	@ISEUIModelMapping(showOnEditor = false)
	protected String documentTypeValue;

	@ISEUIModelMapping(fieldName = "name", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected String cashierName;

	@ISEUIModelMapping(fieldName = "name", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected String accountantName;

	@ISEUIModelMapping(fieldName = "refUUID", seName = FinAccount.SENAME, nodeName = "FinAccountObjectRef")
	protected String refAccountObjectUUID;

	protected int accountObjectType;

	protected String accountObjectTypeValue;
	
	protected int accountObjectInitFlag;

	@ISEUIModelMapping(fieldName = "auditStatus", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccount_auditStatus", valueFieldName = "accountStatusValue")
	protected int auditStatus;

	protected String auditStatusValue;

	@ISEUIModelMapping(fieldName = "verifyStatus", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccount_verifyStatus", valueFieldName = "verifyStatusValue")
	protected int verifyStatus;

	protected String verifyStatusValue;

	@ISEUIModelMapping(fieldName = "recordStatus", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccount_recordStatus", valueFieldName = "recordStatusValue")
	protected int recordStatus;

	protected String recordStatusValue;

	@ISEUIModelMapping(fieldName = "recordNote", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String recordNote;

	@ISEUIModelMapping(fieldName = "auditNote", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String auditNote;

	@ISEUIModelMapping(fieldName = "verifyNote", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String verifyNote;

	@ISEUIModelMapping(fieldName = "verifyBy", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected String verifyBy;

	protected String verifyByName;
	
	protected String AccountantUUID;
	
	protected int status;
	
	protected String statusValue;	

	@ISEUIModelMapping(fieldName = "auditBy", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected String auditBy;

	protected String auditByName;

	@ISEUIModelMapping(fieldName = "recordBy", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected String recordBy;

	protected String recordByName;

	protected boolean recordEnableFlag;

	protected boolean verifyEnableFlag;

	protected boolean auditEnableFlag;

	@ISEUIModelMapping(fieldName = "auditTime", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String auditTime;

	@ISEUIModelMapping(fieldName = "recordTime", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String recordTime;
	
	@ISEUIModelMapping(fieldName = "verifyTime", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME, showOnList = false)
	protected String verifyTime;
	
	protected String financeTime;
	
	@ISEUIModelMapping(fieldName = "recordedAmount", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected double recordedAmount;
	
	@ISEUIModelMapping(fieldName = "verifyTime", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected double toRecordAmount;
	
	@ISEUIModelMapping(fieldName = "adjustAmount", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	protected double adjustAmount;
	
	@ISEUIModelMapping(fieldName = "adjustDirection", seName = FinAccount.SENAME, nodeName = FinAccount.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "FinAccount_adjustDirection", valueFieldName = "")
	protected int adjustDirection;

	// put from UI form
	protected double amount;
	
	protected double amountShow;

	protected String recordLockMSG;

	protected String auditLockMSG;

	protected String verifyLockMSG;
	
	protected boolean recordLock;
	
	protected boolean auditLock;
	
	protected boolean verifyLock;
	
	protected String cashierUUID;
	
	protected int priorityCode;
	
	protected String finAccountTitleName;
	
	protected String finAccountTitleId;
	
    protected String currencyCode;
	
	protected String refDocumentUUID;
	
    protected double amountInSetCurrency;
	
	protected double exchangeRate;
	
	protected double recordedAmountInSetCurrency;
	
	protected double toRecordAmountInSetCurrency;

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getAccountObjectName() {
		return accountObjectName;
	}

	public void setAccountObjectName(String accountObjectName) {
		this.accountObjectName = accountObjectName;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getAccountTitleUUID() {
		return accountTitleUUID;
	}

	public void setAccountTitleUUID(String accountTitleUUID) {
		this.accountTitleUUID = accountTitleUUID;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getAccountTitleName() {
		return accountTitleName;
	}

	public void setAccountTitleName(String accountTitleName) {
		this.accountTitleName = accountTitleName;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getAccountantName() {
		return accountantName;
	}

	public void setAccountantName(String accountantName) {
		this.accountantName = accountantName;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getPaymentTypeValue() {
		return paymentTypeValue;
	}

	public void setPaymentTypeValue(String paymentTypeValue) {
		this.paymentTypeValue = paymentTypeValue;
	}

	public String getRefAccountObjectUUID() {
		return refAccountObjectUUID;
	}

	public void setRefAccountObjectUUID(String refAccountObjectUUID) {
		this.refAccountObjectUUID = refAccountObjectUUID;
	}

	public int getAccountObjectType() {
		return accountObjectType;
	}

	public void setAccountObjectType(int accountObjectType) {
		this.accountObjectType = accountObjectType;
	}

	public String getAccountObjectTypeValue() {
		return accountObjectTypeValue;
	}

	public void setAccountObjectTypeValue(String accountObjectTypeValue) {
		this.accountObjectTypeValue = accountObjectTypeValue;
	}

	public String getAccountObjectId() {
		return accountObjectId;
	}

	public void setAccountObjectId(String accountObjectId) {
		this.accountObjectId = accountObjectId;
	}

	public int getAccountObjectInitFlag() {
		return accountObjectInitFlag;
	}

	public void setAccountObjectInitFlag(int accountObjectInitFlag) {
		this.accountObjectInitFlag = accountObjectInitFlag;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditStatusValue() {
		return auditStatusValue;
	}

	public void setAuditStatusValue(String auditStatusValue) {
		this.auditStatusValue = auditStatusValue;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyStatusValue() {
		return verifyStatusValue;
	}

	public void setVerifyStatusValue(String verifyStatusValue) {
		this.verifyStatusValue = verifyStatusValue;
	}

	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getRecordStatusValue() {
		return recordStatusValue;
	}

	public void setRecordStatusValue(String recordStatusValue) {
		this.recordStatusValue = recordStatusValue;
	}

	public String getRecordNote() {
		return recordNote;
	}

	public void setRecordNote(String recordNote) {
		this.recordNote = recordNote;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public String getVerifyNote() {
		return verifyNote;
	}

	public void setVerifyNote(String verifyNote) {
		this.verifyNote = verifyNote;
	}

	public String getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public String getRecordBy() {
		return recordBy;
	}

	public void setRecordBy(String recordBy) {
		this.recordBy = recordBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyByName() {
		return verifyByName;
	}

	public void setVerifyByName(String verifyByName) {
		this.verifyByName = verifyByName;
	}

	public String getAuditByName() {
		return auditByName;
	}

	public void setAuditByName(String auditByName) {
		this.auditByName = auditByName;
	}

	public String getRecordByName() {
		return recordByName;
	}

	public void setRecordByName(String recordByName) {
		this.recordByName = recordByName;
	}

	public boolean isRecordEnableFlag() {
		return recordEnableFlag;
	}

	public void setRecordEnableFlag(boolean recordEnableFlag) {
		this.recordEnableFlag = recordEnableFlag;
	}

	public boolean isVerifyEnableFlag() {
		return verifyEnableFlag;
	}

	public void setVerifyEnableFlag(boolean verifyEnableFlag) {
		this.verifyEnableFlag = verifyEnableFlag;
	}

	public boolean isAuditEnableFlag() {
		return auditEnableFlag;
	}

	public void setAuditEnableFlag(boolean auditEnableFlag) {
		this.auditEnableFlag = auditEnableFlag;
	}

	public String getRecordLockMSG() {
		return recordLockMSG;
	}

	public void setRecordLockMSG(String recordLockMSG) {
		this.recordLockMSG = recordLockMSG;
	}

	public String getAuditLockMSG() {
		return auditLockMSG;
	}

	public void setAuditLockMSG(String auditLockMSG) {
		this.auditLockMSG = auditLockMSG;
	}

	public String getVerifyLockMSG() {
		return verifyLockMSG;
	}

	public void setVerifyLockMSG(String verifyLockMSG) {
		this.verifyLockMSG = verifyLockMSG;
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

	public double getAmountShow() {
		return amountShow;
	}

	public void setAmountShow(double amountShow) {
		this.amountShow = amountShow;
	}

	public double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public int getAdjustDirection() {
		return adjustDirection;
	}

	public void setAdjustDirection(int adjustDirection) {
		this.adjustDirection = adjustDirection;
	}

	public String getCashierUUID() {
		return cashierUUID;
	}

	public void setCashierUUID(String cashierUUID) {
		this.cashierUUID = cashierUUID;
	}

	public int getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getFinanceTime() {
		return financeTime;
	}

	public void setFinanceTime(String financeTime) {
		this.financeTime = financeTime;
	}

	public boolean getRecordLock() {
		return recordLock;
	}

	public void setRecordLock(boolean recordLock) {
		this.recordLock = recordLock;
	}

	public boolean getAuditLock() {
		return auditLock;
	}

	public void setAuditLock(boolean auditLock) {
		this.auditLock = auditLock;
	}

	public boolean getVerifyLock() {
		return verifyLock;
	}

	public void setVerifyLock(boolean verifyLock) {
		this.verifyLock = verifyLock;
	}

	public String getAccountantUUID() {
		return AccountantUUID;
	}

	public void setAccountantUUID(String accountantUUID) {
		AccountantUUID = accountantUUID;
	}

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

	public String getFinAccountTitleName() {
		return finAccountTitleName;
	}

	public void setFinAccountTitleName(String finAccountTitleName) {
		this.finAccountTitleName = finAccountTitleName;
	}

	public String getFinAccountTitleId() {
		return finAccountTitleId;
	}

	public void setFinAccountTitleId(String finAccountTitleId) {
		this.finAccountTitleId = finAccountTitleId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRefDocumentUUID() {
		return refDocumentUUID;
	}

	public void setRefDocumentUUID(String refDocumentUUID) {
		this.refDocumentUUID = refDocumentUUID;
	}

	public double getAmountInSetCurrency() {
		return amountInSetCurrency;
	}

	public void setAmountInSetCurrency(double amountInSetCurrency) {
		this.amountInSetCurrency = amountInSetCurrency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getRecordedAmountInSetCurrency() {
		return recordedAmountInSetCurrency;
	}

	public void setRecordedAmountInSetCurrency(double recordedAmountInSetCurrency) {
		this.recordedAmountInSetCurrency = recordedAmountInSetCurrency;
	}

	public double getToRecordAmountInSetCurrency() {
		return toRecordAmountInSetCurrency;
	}

	public void setToRecordAmountInSetCurrency(double toRecordAmountInSetCurrency) {
		this.toRecordAmountInSetCurrency = toRecordAmountInSetCurrency;
	}

}

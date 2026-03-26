package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinFinance - FinAccount.java / FinAccount.hbm.xml
 * New table: FinAccount (schema: finance)
 * Hierarchy: ServiceEntityNode → DocumentContent → FinAccount
 *
 * FinAccount has a tri-status approval workflow (audit → record → verify)
 * separate from the base document status field.
 */
@Entity
@Table(name = "FinAccount", catalog = "finance")
public class FinAccount extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.FinAccount;

	public static final String FIELD_ACCTITLE_UUID = "accountTitleUUID";

	// --- tri-step approval status constants ---
	public static final int AUDIT_UNDONE    = 1;

	public static final int AUDIT_DONE      = 2;

	public static final int AUDIT_REJECT    = 3;

	public static final int VERIFIED_UNDONE = 1;

	public static final int VERIFIED_DONE   = 2;

	public static final int RECORDED_UNDONE = 1;

	public static final int RECORDED_DONE   = 2;

	// --- payment type ---
	public static final int PAYMENT_CASH = 1;

	public static final int PAYMENT_BANK = 2;

	// --- adjust direction ---
	public static final int ADJUST_DISCOUNT = 1;

	public static final int ADJUST_INCREASE = 2;

	@Column(name = "documentType")
	protected int documentType;

	@Column(name = "amount")
	protected double amount;

	@Column(name = "accountTitleUUID")
	protected String accountTitleUUID;

	@Column(name = "accountObject")
	protected String accountObject;

	// Cross-module refs — UUID only, no FK
	@Column(name = "accountantUUID")
	protected String accountantUUID;

	@Column(name = "cashierUUID")
	protected String cashierUUID;

	@Column(name = "refDocumentUUID")
	protected String refDocumentUUID;

	@Column(name = "refAccountObjectUUID")
	protected String refAccountObjectUUID;

	@Column(name = "execOrgUUID")
	protected String execOrgUUID;

	@Column(name = "paymentType")
	protected int paymentType;

	@Column(name = "financeTime")
	protected LocalDateTime financeTime;

	// --- audit step ---
	@Column(name = "auditStatus")
	protected int auditStatus;

	@Column(name = "auditLock")
	protected boolean auditLock;

	@Column(name = "auditBy")
	protected String auditBy;

	@Column(name = "auditTime")
	protected LocalDateTime auditTime;

	@Column(name = "auditNote")
	protected String auditNote;

	@Column(name = "auditLockMSG")
	protected String auditLockMSG;

	// --- record step ---
	@Column(name = "recordStatus")
	protected int recordStatus;

	@Column(name = "recordLock")
	protected boolean recordLock;

	@Column(name = "recordBy")
	protected String recordBy;

	@Column(name = "recordTime")
	protected LocalDateTime recordTime;

	@Column(name = "recordNote")
	protected String recordNote;

	@Column(name = "recordLockMSG")
	protected String recordLockMSG;

	@Column(name = "recordedAmount")
	protected double recordedAmount;

	@Column(name = "toRecordAmount")
	protected double toRecordAmount;

	// --- verify step ---
	@Column(name = "verifyStatus")
	protected int verifyStatus;

	@Column(name = "verifyLock")
	protected boolean verifyLock;

	@Column(name = "verifyBy")
	protected String verifyBy;

	@Column(name = "verifyTime")
	protected LocalDateTime verifyTime;

	@Column(name = "verifyNote")
	protected String verifyNote;

	@Column(name = "verifyLockMSG")
	protected String verifyLockMSG;

	// --- currency / adjustment ---
	@Column(name = "currencyCode")
	protected String currencyCode;

	@Column(name = "exchangeRate")
	protected double exchangeRate;

	@Column(name = "amountInSetCurrency")
	protected double amountInSetCurrency;

	@Column(name = "recordedAmountInSetCurrency")
	protected double recordedAmountInSetCurrency;

	@Column(name = "toRecordAmountInSetCurrency")
	protected double toRecordAmountInSetCurrency;

	@Column(name = "adjustDirection")
	protected int adjustDirection;

	@Column(name = "adjustAmount")
	protected double adjustAmount;

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAccountTitleUUID() {
		return accountTitleUUID;
	}

	public void setAccountTitleUUID(String accountTitleUUID) {
		this.accountTitleUUID = accountTitleUUID;
	}

	public String getAccountObject() {
		return accountObject;
	}

	public void setAccountObject(String accountObject) {
		this.accountObject = accountObject;
	}

	public String getAccountantUUID() {
		return accountantUUID;
	}

	public void setAccountantUUID(String accountantUUID) {
		this.accountantUUID = accountantUUID;
	}

	public String getCashierUUID() {
		return cashierUUID;
	}

	public void setCashierUUID(String cashierUUID) {
		this.cashierUUID = cashierUUID;
	}

	public String getRefDocumentUUID() {
		return refDocumentUUID;
	}

	public void setRefDocumentUUID(String refDocumentUUID) {
		this.refDocumentUUID = refDocumentUUID;
	}

	public String getRefAccountObjectUUID() {
		return refAccountObjectUUID;
	}

	public void setRefAccountObjectUUID(String refAccountObjectUUID) {
		this.refAccountObjectUUID = refAccountObjectUUID;
	}

	public String getExecOrgUUID() {
		return execOrgUUID;
	}

	public void setExecOrgUUID(String execOrgUUID) {
		this.execOrgUUID = execOrgUUID;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public LocalDateTime getFinanceTime() {
		return financeTime;
	}

	public void setFinanceTime(LocalDateTime financeTime) {
		this.financeTime = financeTime;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public boolean getAuditLock() {
		return auditLock;
	}

	public void setAuditLock(boolean auditLock) {
		this.auditLock = auditLock;
	}

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public LocalDateTime getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(LocalDateTime auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public String getAuditLockMSG() {
		return auditLockMSG;
	}

	public void setAuditLockMSG(String auditLockMSG) {
		this.auditLockMSG = auditLockMSG;
	}

	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}

	public boolean getRecordLock() {
		return recordLock;
	}

	public void setRecordLock(boolean recordLock) {
		this.recordLock = recordLock;
	}

	public String getRecordBy() {
		return recordBy;
	}

	public void setRecordBy(String recordBy) {
		this.recordBy = recordBy;
	}

	public LocalDateTime getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(LocalDateTime recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecordNote() {
		return recordNote;
	}

	public void setRecordNote(String recordNote) {
		this.recordNote = recordNote;
	}

	public String getRecordLockMSG() {
		return recordLockMSG;
	}

	public void setRecordLockMSG(String recordLockMSG) {
		this.recordLockMSG = recordLockMSG;
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

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public boolean getVerifyLock() {
		return verifyLock;
	}

	public void setVerifyLock(boolean verifyLock) {
		this.verifyLock = verifyLock;
	}

	public String getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}

	public LocalDateTime getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(LocalDateTime verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyNote() {
		return verifyNote;
	}

	public void setVerifyNote(String verifyNote) {
		this.verifyNote = verifyNote;
	}

	public String getVerifyLockMSG() {
		return verifyLockMSG;
	}

	public void setVerifyLockMSG(String verifyLockMSG) {
		this.verifyLockMSG = verifyLockMSG;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getAmountInSetCurrency() {
		return amountInSetCurrency;
	}

	public void setAmountInSetCurrency(double amountInSetCurrency) {
		this.amountInSetCurrency = amountInSetCurrency;
	}

	public double getRecordedAmountInSetCurrency() {
		return recordedAmountInSetCurrency;
	}

	public void setRecordedAmountInSetCurrency(double v) {
		this.recordedAmountInSetCurrency = v;
	}

	public double getToRecordAmountInSetCurrency() {
		return toRecordAmountInSetCurrency;
	}

	public void setToRecordAmountInSetCurrency(double v) {
		this.toRecordAmountInSetCurrency = v;
	}

	public int getAdjustDirection() {
		return adjustDirection;
	}

	public void setAdjustDirection(int adjustDirection) {
		this.adjustDirection = adjustDirection;
	}

	public double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

}

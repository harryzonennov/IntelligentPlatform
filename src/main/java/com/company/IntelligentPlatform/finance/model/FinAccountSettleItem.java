package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ReferenceNode;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Migrated from: ThorsteinFinance - FinAccountSettleItem.java
 * New table: FinAccountSettleItem (schema: finance)
 * Settlement record for a FinAccount against an account object.
 */
@Entity
@Table(name = "FinAccountSettleItem", catalog = "finance")
public class FinAccountSettleItem extends ReferenceNode {

	@Column(name = "settleAccountValue")
	protected double settleAccountValue;

	@Column(name = "refAccObjectUUID")
	protected String refAccObjectUUID;

	@Column(name = "accountType")
	protected int accountType;

	@Column(name = "auditStatus")
	protected int auditStatus;

	@Column(name = "auditBy")
	protected String auditBy;

	@Column(name = "auditTime")
	protected LocalDateTime auditTime;

	@Column(name = "auditLockFlag")
	protected boolean auditLockFlag;

	@Column(name = "auditLockMSG")
	protected String auditLockMSG;

	@Column(name = "auditNote")
	protected String auditNote;

	@Column(name = "verifyStatus")
	protected int verifyStatus;

	@Column(name = "verifyBy")
	protected String verifyBy;

	@Column(name = "verifyTime")
	protected LocalDateTime verifyTime;

	@Column(name = "verifyLockFlag")
	protected boolean verifyLockFlag;

	@Column(name = "verifyLockMSG")
	protected String verifyLockMSG;

	@Column(name = "verifyNote")
	protected String verifyNote;

	@Column(name = "recordStatus")
	protected int recordStatus;

	@Column(name = "recordBy")
	protected String recordBy;

	@Column(name = "recordTime")
	protected LocalDateTime recordTime;

	@Column(name = "recordLockFlag")
	protected boolean recordLockFlag;

	@Column(name = "recordLockMSG")
	protected String recordLockMSG;

	@Column(name = "recordNote")
	protected String recordNote;

	public double getSettleAccountValue() {
		return settleAccountValue;
	}

	public void setSettleAccountValue(double settleAccountValue) {
		this.settleAccountValue = settleAccountValue;
	}

	public String getRefAccObjectUUID() {
		return refAccObjectUUID;
	}

	public void setRefAccObjectUUID(String refAccObjectUUID) {
		this.refAccObjectUUID = refAccObjectUUID;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
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

	public boolean isAuditLockFlag() {
		return auditLockFlag;
	}

	public void setAuditLockFlag(boolean auditLockFlag) {
		this.auditLockFlag = auditLockFlag;
	}

	public String getAuditLockMSG() {
		return auditLockMSG;
	}

	public void setAuditLockMSG(String auditLockMSG) {
		this.auditLockMSG = auditLockMSG;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
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

	public boolean isVerifyLockFlag() {
		return verifyLockFlag;
	}

	public void setVerifyLockFlag(boolean verifyLockFlag) {
		this.verifyLockFlag = verifyLockFlag;
	}

	public String getVerifyLockMSG() {
		return verifyLockMSG;
	}

	public void setVerifyLockMSG(String verifyLockMSG) {
		this.verifyLockMSG = verifyLockMSG;
	}

	public String getVerifyNote() {
		return verifyNote;
	}

	public void setVerifyNote(String verifyNote) {
		this.verifyNote = verifyNote;
	}

	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
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

	public boolean isRecordLockFlag() {
		return recordLockFlag;
	}

	public void setRecordLockFlag(boolean recordLockFlag) {
		this.recordLockFlag = recordLockFlag;
	}

	public String getRecordLockMSG() {
		return recordLockMSG;
	}

	public void setRecordLockMSG(String recordLockMSG) {
		this.recordLockMSG = recordLockMSG;
	}

	public String getRecordNote() {
		return recordNote;
	}

	public void setRecordNote(String recordNote) {
		this.recordNote = recordNote;
	}

}

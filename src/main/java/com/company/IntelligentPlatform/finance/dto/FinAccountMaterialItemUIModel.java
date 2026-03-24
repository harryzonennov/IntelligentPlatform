package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class FinAccountMaterialItemUIModel extends DocMatItemUIModel {

	protected String finAccountId;
	
	protected int auditStatus;

	protected String auditStatusValue;

	protected int verifyStatus;

	protected String verifyStatusValue;

	protected int recordStatus;

	protected String recordStatusValue;
	
	protected String statusValue;
	
	protected String recordTime;

	public String getFinAccountId() {
		return finAccountId;
	}

	public void setFinAccountId(String finAccountId) {
		this.finAccountId = finAccountId;
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

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	
}

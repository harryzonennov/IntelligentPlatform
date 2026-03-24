package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinFinance - FinAccountLog.java
 * New table: FinAccountLog (schema: finance)
 * Audit log entry for a FinAccount status change action.
 */
@Entity
@Table(name = "FinAccountLog", schema = "finance")
public class FinAccountLog extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.FinAccountLog;

	@Column(name = "financeDate")
	protected LocalDateTime financeDate;

	@Column(name = "previousAmount")
	protected double previousAmount;

	@Column(name = "currentAmount")
	protected double currentAmount;

	@Column(name = "actionCode")
	protected int actionCode;

	@Column(name = "auditStatus")
	protected int auditStatus;

	public LocalDateTime getFinanceDate() {
		return financeDate;
	}

	public void setFinanceDate(LocalDateTime financeDate) {
		this.financeDate = financeDate;
	}

	public double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public int getActionCode() {
		return actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

}

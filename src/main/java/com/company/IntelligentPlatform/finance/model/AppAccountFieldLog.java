package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * General Service Entity class for Application-Account fee value field change
 * log
 *
 * @author Zhang,Hang
 *
 */
public class AppAccountFieldLog extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = "AppAccountField";

	protected String previousValue;

	protected String currentValue;

	protected double previousAmount;

	protected double currentAmount;

	protected int actionCode;

	protected int auditStatus;

	/**
	 * field name to shown on UI
	 */
	protected String fieldName;

	public AppAccountFieldLog() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.auditStatus = FinAccount.AUDIT_UNDONE;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
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

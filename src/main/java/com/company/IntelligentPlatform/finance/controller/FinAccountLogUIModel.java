package com.company.IntelligentPlatform.finance.controller;

import com.company.IntelligentPlatform.finance.model.FinAccountLog;

import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class FinAccountLogUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "auditStatus", seName = FinAccountLog.SENAME, nodeName = FinAccountLog.NODENAME, nodeInstID = FinAccountLog.NODENAME, tabId = TABID_BASIC)
	protected int auditStatus;

	@ISEUIModelMapping(fieldName = "previousAmount", seName = FinAccountLog.SENAME, nodeName = FinAccountLog.NODENAME, nodeInstID = FinAccountLog.NODENAME, showOnList = false, searchFlag = false, tabId = TABID_BASIC)
	protected double previousAmount;

	@ISEUIModelMapping(fieldName = "currentAmount", seName = FinAccountLog.SENAME, nodeName = FinAccountLog.NODENAME, nodeInstID = FinAccountLog.NODENAME, showOnList = false, searchFlag = false, tabId = TABID_BASIC)
	protected double currentAmount;

	@ISEUIModelMapping(fieldName = "financeDate", seName = FinAccountLog.SENAME, nodeName = FinAccountLog.NODENAME, nodeInstID = FinAccountLog.NODENAME, tabId = TABID_BASIC)
	protected String financeDate;

	@ISEUIModelMapping(fieldName = "actionCode", seName = FinAccountLog.SENAME, nodeName = FinAccountLog.NODENAME, nodeInstID = FinAccountLog.NODENAME, tabId = TABID_BASIC)
	protected int actionCode;

	public int getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public double getPreviousAmount() {
		return this.previousAmount;
	}

	public void setPreviousAmount(double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getCurrentAmount() {
		return this.currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getFinanceDate() {
		return this.financeDate;
	}

	public void setFinanceDate(String financeDate) {
		this.financeDate = financeDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getActionCode() {
		return this.actionCode;
	}

	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

}

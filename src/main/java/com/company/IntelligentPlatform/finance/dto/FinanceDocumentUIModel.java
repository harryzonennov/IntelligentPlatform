package com.company.IntelligentPlatform.finance.dto;

import java.util.Date;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class FinanceDocumentUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "accountType", seName = "Account", nodeName = "ROOT")
	protected String accountType;

	@ISEUIModelMapping(fieldName = "paymentsType", seName = "Account", nodeName = "ROOT")
	protected int paymentsType;

	@ISEUIModelMapping(fieldName = "amount", seName = "Account", nodeName = "ROOT")
	protected double amount;

	@ISEUIModelMapping(fieldName = "docDate", seName = "Account", nodeName = "ROOT")
	protected Date docDate;

	@ISEUIModelMapping(fieldName = "id", seName = "LogonUser", nodeName = "ROOT")
	protected String accountantName;

	@ISEUIModelMapping(fieldName = "id", seName = "LogonUser", nodeName = "ROOT")
	protected String chashierName;

	@ISEUIModelMapping(fieldName = "rootNodeUUID;", seName = "Account", nodeName = "ROOT")
	protected String acountUUID;

	@ISEUIModelMapping(fieldName = "rootNodeUUID;", seName = "AccountTitle;", nodeName = "ROOT")
	protected String accountTitleUUID;


	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getPaymentsType() {
		return paymentsType;
	}

	public void setPaymentsType(int paymentsType) {
		this.paymentsType = paymentsType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getAccountantName() {
		return accountantName;
	}

	public void setAccountantName(String accountantName) {
		this.accountantName = accountantName;
	}

	public String getChashierName() {
		return chashierName;
	}

	public void setChashierName(String chashierName) {
		this.chashierName = chashierName;
	}

	public String getAcountUUID() {
		return acountUUID;
	}

	public void setAcountUUID(String acountUUID) {
		this.acountUUID = acountUUID;
	}

	public String getAccountTitleUUID() {
		return accountTitleUUID;
	}

	public void setAccountTitleUUID(String accountTitleUUID) {
		this.accountTitleUUID = accountTitleUUID;
	}
}
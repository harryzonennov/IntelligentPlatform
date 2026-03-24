package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import jakarta.persistence.*;

/**
 * Migrated from: ThorsteinFinance - FinanceDocument.hbm.xml
 * Old table: FinanceDocument (db_tfinance)
 * New table: FinanceDocument (schema: finance)
 */
@Entity
@Table(name = "FinanceDocument", schema = "finance")
public class FinanceDocument extends ServiceEntityNode {

	@Column(name = "documentType")
	protected int documentType;

	@Column(name = "objectName")
	protected String objectName;

	@Column(name = "accountTitleName")
	protected String accountTitleName;

	@Column(name = "paymentsType")
	protected int paymentsType;

	@Column(name = "amount")
	protected double amount;

	@Column(name = "accountant")
	protected String accountant;

	@Column(name = "cashier")
	protected String cashier;

	// Cross-module ref — UUID only, no FK
	@Column(name = "acountUUID")
	protected String acountUUID;

	@Column(name = "accountTitleUUID")
	protected String accountTitleUUID;

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getAccountTitleName() {
		return accountTitleName;
	}

	public void setAccountTitleName(String accountTitleName) {
		this.accountTitleName = accountTitleName;
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

	public String getAccountant() {
		return accountant;
	}

	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
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

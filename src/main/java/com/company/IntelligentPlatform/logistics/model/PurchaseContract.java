package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - PurchaseContract (extends DocumentContent)
 * Table: PurchaseContract (schema: logistics)
 *
 * Cross-module refs (stored as UUID String, no FK):
 *   refFinAccountUUID → FinAccount (finance module)
 */
@Entity
@Table(name = "PurchaseContract", schema = "logistics")
public class PurchaseContract extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.PurchaseContract;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_SUBMITTED = 2;

	public static final int STATUS_APPROVED = 3;

	public static final int STATUS_DELIVERYDONE = 200;

	public static final int STATUS_PROCESSDONE = 100;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

	@Column(name = "contractDetails", length = 4000)
	protected String contractDetails;

	@Column(name = "signDate")
	protected LocalDate signDate;

	@Column(name = "requireExecutionDate")
	protected LocalDate requireExecutionDate;

	@Column(name = "currencyCode")
	protected String currencyCode;

	@Column(name = "refFinAccountUUID")
	protected String refFinAccountUUID;

	@Column(name = "purchaseBatchNumber")
	protected String purchaseBatchNumber;

	@Column(name = "productionBatchNumber")
	protected String productionBatchNumber;

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public double getGrossPriceDisplay() {
		return grossPriceDisplay;
	}

	public void setGrossPriceDisplay(double grossPriceDisplay) {
		this.grossPriceDisplay = grossPriceDisplay;
	}

	public String getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public LocalDate getSignDate() {
		return signDate;
	}

	public void setSignDate(LocalDate signDate) {
		this.signDate = signDate;
	}

	public LocalDate getRequireExecutionDate() {
		return requireExecutionDate;
	}

	public void setRequireExecutionDate(LocalDate requireExecutionDate) {
		this.requireExecutionDate = requireExecutionDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRefFinAccountUUID() {
		return refFinAccountUUID;
	}

	public void setRefFinAccountUUID(String refFinAccountUUID) {
		this.refFinAccountUUID = refFinAccountUUID;
	}

	public String getPurchaseBatchNumber() {
		return purchaseBatchNumber;
	}

	public void setPurchaseBatchNumber(String purchaseBatchNumber) {
		this.purchaseBatchNumber = purchaseBatchNumber;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

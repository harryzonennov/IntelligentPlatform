package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesContract (extends DocumentContent)
 * Table: SalesContract (schema: sales)
 *
 * Cross-module ref: refFinAccountUUID → finance schema (UUID only, no FK)
 */
@Entity
@Table(name = "SalesContract", catalog = "sales")
public class SalesContract extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.SalesContract;

	public static final int STATUS_INITIAL        = 1;

	public static final int STATUS_SUBMITTED      = 2;

	public static final int STATUS_APPROVED       = 3;

	public static final int STATUS_INPLAN         = 4;

	public static final int STATUS_DELIVERYDONE   = 5;

	public static final int STATUS_PROCESSDONE    = 6;

	public static final int STATUS_CANCEL         = 7;

	public static final int STATUS_REJECT_APPROVAL = 305;

	public static final int STATUS_ARCHIVED       = 400;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

	@Column(name = "currencyCode")
	protected String currencyCode;

	@Column(name = "contractDetails", length = 2000)
	protected String contractDetails;

	@Column(name = "signDate")
	protected LocalDate signDate;

	@Column(name = "requireExecutionDate")
	protected LocalDate requireExecutionDate;

	@Column(name = "planExecutionDate")
	protected LocalDate planExecutionDate;

	@Column(name = "refFinAccountUUID")
	protected String refFinAccountUUID;

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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public LocalDate getPlanExecutionDate() {
		return planExecutionDate;
	}

	public void setPlanExecutionDate(LocalDate planExecutionDate) {
		this.planExecutionDate = planExecutionDate;
	}

	public String getRefFinAccountUUID() {
		return refFinAccountUUID;
	}

	public void setRefFinAccountUUID(String refFinAccountUUID) {
		this.refFinAccountUUID = refFinAccountUUID;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

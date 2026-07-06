package com.company.IntelligentPlatform.salesDistribution.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

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

	public static final int STATUS_INITIAL         = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED        = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_INPLAN          = 3;

	public static final int STATUS_DELIVERYDONE    = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_PROCESSDONE     = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_CANCEL          = DocumentContent.STATUS_CANCELED;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_SUBMITTED       = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ARCHIVED        = DocumentContent.STATUS_ARCHIVED;

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

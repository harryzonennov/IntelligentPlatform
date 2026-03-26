package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - PurchaseRequest (extends DocumentContent)
 * Table: PurchaseRequest (schema: logistics)
 */
@Entity
@Table(name = "PurchaseRequest", catalog = "logistics")
public class PurchaseRequest extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.PurchaseRequest;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_SUBMITTED = 2;

	public static final int STATUS_APPROVED = 3;

	public static final int STATUS_INPROCESS = 4;

	public static final int STATUS_DELIVERYDONE = 200;

	public static final int STATUS_PROCESSDONE = 100;

	public static final int STATUS_REJECT_APPROVAL = 305;

	public static final int STATUS_ARCHIVED = 400;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

	@Column(name = "currencyCode")
	protected String currencyCode;

	@Column(name = "planExecutionDate")
	protected LocalDate planExecutionDate;

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

	public LocalDate getPlanExecutionDate() {
		return planExecutionDate;
	}

	public void setPlanExecutionDate(LocalDate planExecutionDate) {
		this.planExecutionDate = planExecutionDate;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

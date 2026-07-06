package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - PurchaseRequest (extends DocumentContent)
 * Table: PurchaseRequest (schema: logistics)
 */
@Entity
@Table(name = "PurchaseRequest", catalog = "logistics")
public class PurchaseRequest extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.PurchaseRequest;

	public static final int STATUS_INITIAL = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_INPROCESS = DocumentContent.STATUS_INPROCESS;

	public static final int STATUS_DELIVERYDONE = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_PROCESSDONE = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;

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

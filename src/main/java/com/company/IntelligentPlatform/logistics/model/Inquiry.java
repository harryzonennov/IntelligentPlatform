package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - Inquiry (extends DocumentContent)
 * Table: Inquiry (schema: logistics)
 */
@Entity
@Table(name = "Inquiry", catalog = "logistics")
public class Inquiry extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.Inquiry;

	public static final int STATUS_INIT = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_INITIAL = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_INPROCESS = DocumentContent.STATUS_INPROCESS;

	public static final int STATUS_DELIVERYDONE = DocumentContent.STATUS_DELIVERYDONE;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

	@Column(name = "contractDetails", length = 800)
	protected String contractDetails;

	@Column(name = "signDate")
	protected LocalDate signDate;

	@Column(name = "requireExecutionDate")
	protected LocalDate requireExecutionDate;

	@Column(name = "currencyCode")
	protected String currencyCode;

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

}

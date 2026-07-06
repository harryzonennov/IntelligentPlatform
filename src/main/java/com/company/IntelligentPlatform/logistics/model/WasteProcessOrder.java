package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - WasteProcessOrder (extends DocumentContent)
 * Table: WasteProcessOrder (schema: logistics)
 */
@Entity
@Table(name = "WasteProcessOrder", catalog = "logistics")
public class WasteProcessOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.WasteProcessOrder;

	public static final int STATUS_INITIAL = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_PROCESSDONE = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_CANCEL = DocumentContent.STATUS_CANCELED;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;

	public static final int PROCESSTYPE_DISCARD = 1;

	public static final int PROCESSTYPE_SALESAS_WASTE = 2;

	public static final int PROCESSTYPE_SALESTO_SUPPLIER = 3;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

	@Column(name = "currencyCode")
	protected String currencyCode;

	@Column(name = "processType")
	protected int processType;

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

	public int getProcessType() {
		return processType;
	}

	public void setProcessType(int processType) {
		this.processType = processType;
	}

}

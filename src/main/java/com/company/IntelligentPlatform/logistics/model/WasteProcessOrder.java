package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - WasteProcessOrder (extends DocumentContent)
 * Table: WasteProcessOrder (schema: logistics)
 */
@Entity
@Table(name = "WasteProcessOrder", schema = "logistics")
public class WasteProcessOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.WasteProcessOrder;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_SUBMITTED = 2;

	public static final int STATUS_APPROVED = 3;

	public static final int STATUS_PROCESSDONE = 100;

	public static final int STATUS_DELIVERYDONE = 100;

	public static final int STATUS_CANCEL = 6;

	public static final int STATUS_REJECT_APPROVAL = 305;

	public static final int STATUS_ARCHIVED = 400;

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

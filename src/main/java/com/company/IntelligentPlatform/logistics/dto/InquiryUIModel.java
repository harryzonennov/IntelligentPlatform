package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.Inquiry;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;

public class InquiryUIModel extends DocumentUIModel {

	protected String currencyCode;

	@ISEUIModelMapping(fieldName = "signDate", seName = Inquiry.SENAME, nodeName = Inquiry.NODENAME, nodeInstID = Inquiry.SENAME, tabId = TABID_BASIC)
	protected String signDate;
	
	@ISEUIModelMapping(fieldName = "requireExecutionDate", seName = Inquiry.SENAME, nodeName = Inquiry.NODENAME, nodeInstID = Inquiry.SENAME, tabId = TABID_BASIC)
	protected String requireExecutionDate;

	protected double grossPrice;

	protected double grossPriceDisplay;

	@ISEUIModelMapping(fieldName = "contractDetails", seName = Inquiry.SENAME, nodeName = Inquiry.NODENAME, nodeInstID = Inquiry.SENAME, tabId = TABID_BASIC)
	protected String contractDetails;

	public String getSignDate() {
		return this.signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getRequireExecutionDate() {
		return requireExecutionDate;
	}

	public void setRequireExecutionDate(String requireExecutionDate) {
		this.requireExecutionDate = requireExecutionDate;
	}

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
		return this.contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}

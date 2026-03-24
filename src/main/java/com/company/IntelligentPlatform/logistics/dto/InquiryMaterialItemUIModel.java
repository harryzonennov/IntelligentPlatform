package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class InquiryMaterialItemUIModel extends DocMatItemUIModel {
	
	protected String requireShippingTime;

	protected String shippingPoint;
	
	protected String currencyCode;

	protected double firstUnitPrice;

	protected double firstItemPrice;

	public String getRequireShippingTime() {
		return this.requireShippingTime;
	}

	public void setRequireShippingTime(String requireShippingTime) {
		this.requireShippingTime = requireShippingTime;
	}

	public String getShippingPoint() {
		return this.shippingPoint;
	}

	public void setShippingPoint(String shippingPoint) {
		this.shippingPoint = shippingPoint;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getFirstUnitPrice() {
		return firstUnitPrice;
	}

	public void setFirstUnitPrice(double firstUnitPrice) {
		this.firstUnitPrice = firstUnitPrice;
	}

	public double getFirstItemPrice() {
		return firstItemPrice;
	}

	public void setFirstItemPrice(double firstItemPrice) {
		this.firstItemPrice = firstItemPrice;
	}
	
}

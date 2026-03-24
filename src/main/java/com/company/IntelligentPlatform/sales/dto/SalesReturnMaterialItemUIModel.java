package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;

public class SalesReturnMaterialItemUIModel extends DocMatItemUIModel{

	protected String currencyCode;

	protected double prevProfAmount;

	protected String prevProfAmountLabel;

	protected String prevProfRefUnitUUID;

	protected String prevProfRefUnitName;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getPrevProfAmount() {
		return prevProfAmount;
	}

	public void setPrevProfAmount(double prevProfAmount) {
		this.prevProfAmount = prevProfAmount;
	}

	public String getPrevProfAmountLabel() {
		return prevProfAmountLabel;
	}

	public void setPrevProfAmountLabel(String prevProfAmountLabel) {
		this.prevProfAmountLabel = prevProfAmountLabel;
	}

	public String getPrevProfRefUnitUUID() {
		return prevProfRefUnitUUID;
	}

	public void setPrevProfRefUnitUUID(String prevProfRefUnitUUID) {
		this.prevProfRefUnitUUID = prevProfRefUnitUUID;
	}

	public String getPrevProfRefUnitName() {
		return prevProfRefUnitName;
	}

	public void setPrevProfRefUnitName(String prevProfRefUnitName) {
		this.prevProfRefUnitName = prevProfRefUnitName;
	}
}
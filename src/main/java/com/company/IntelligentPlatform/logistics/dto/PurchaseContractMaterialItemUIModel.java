package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;

public class PurchaseContractMaterialItemUIModel extends DocMatItemUIModel {

	protected String requireShippingTime;

	protected String shippingPoint;
		
	protected String signDate;

	protected String requireExecutionDate;
    
    protected String currencyCode;
	
	protected int splitEnableFlag;

	public PurchaseContractMaterialItemUIModel() {
		super();
		this.splitEnableFlag = StandardSwitchProxy.SWITCH_ON;
	}

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
	
	public String getSignDate() {
		return signDate;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getSplitEnableFlag() {
		return splitEnableFlag;
	}

	public void setSplitEnableFlag(int splitEnableFlag) {
		this.splitEnableFlag = splitEnableFlag;
	}
	
}

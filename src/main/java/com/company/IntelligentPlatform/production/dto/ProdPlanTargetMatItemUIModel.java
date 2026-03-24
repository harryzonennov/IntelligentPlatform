package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class ProdPlanTargetMatItemUIModel extends DocMatItemUIModel {
	
	@ISEDropDownResourceMapping(resouceMapping = "ProdOrderTargetMatItem_status", valueFieldName = "")
	protected int itemStatus;
	
	protected String orderId;
	
	protected int orderStatus;
	
	protected String orderStatusValue;
	
	protected int processIndex;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

}

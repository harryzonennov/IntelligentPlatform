package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ProdPickingRefOrderItemUIModel extends SEUIComModel {

	protected int processIndex;

	protected String refProdOrderUUID;

	protected String refUnitUUID;
	
	protected String refUnitName;

	protected double amount;

	protected String refOrderName;

	protected String refOrderId;

	protected String orderMaterialSKUName;

	protected String orderMaterialSKUId;
	
	protected String orderMaterialSKUUUID;
	
	protected double orderCost;

	public int getProcessIndex() {
		return this.processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefProdOrderUUID() {
		return this.refProdOrderUUID;
	}

	public void setRefProdOrderUUID(String refProdOrderUUID) {
		this.refProdOrderUUID = refProdOrderUUID;
	}

	public String getRefUnitUUID() {
		return this.refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getRefUnitName() {
		return refUnitName;
	}

	public void setRefUnitName(String refUnitName) {
		this.refUnitName = refUnitName;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefOrderName() {
		return refOrderName;
	}

	public void setRefOrderName(String refOrderName) {
		this.refOrderName = refOrderName;
	}

	public String getRefOrderId() {
		return refOrderId;
	}

	public void setRefOrderId(String refOrderId) {
		this.refOrderId = refOrderId;
	}

	public String getOrderMaterialSKUName() {
		return orderMaterialSKUName;
	}

	public void setOrderMaterialSKUName(String orderMaterialSKUName) {
		this.orderMaterialSKUName = orderMaterialSKUName;
	}

	public String getOrderMaterialSKUId() {
		return orderMaterialSKUId;
	}

	public void setOrderMaterialSKUId(String orderMaterialSKUId) {
		this.orderMaterialSKUId = orderMaterialSKUId;
	}

	public String getOrderMaterialSKUUUID() {
		return orderMaterialSKUUUID;
	}

	public void setOrderMaterialSKUUUID(String orderMaterialSKUUUID) {
		this.orderMaterialSKUUUID = orderMaterialSKUUUID;
	}

	public double getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(double orderCost) {
		this.orderCost = orderCost;
	}

}

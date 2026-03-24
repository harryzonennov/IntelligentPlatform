package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProdPickingRefOrderItem extends ServiceEntityNode {


	public final static String NODENAME = IServiceModelConstants.ProdPickingRefOrderItem;

	public final static String SENAME = ProdPickingOrder.SENAME;

	protected int processIndex;

	protected String refProdOrderUUID;

	protected double amount;

	protected String refUnitUUID;

	protected double orderCost;

	public ProdPickingRefOrderItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefProdOrderUUID() {
		return refProdOrderUUID;
	}

	public void setRefProdOrderUUID(String refProdOrderUUID) {
		this.refProdOrderUUID = refProdOrderUUID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public double getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(double orderCost) {
		this.orderCost = orderCost;
	}

}

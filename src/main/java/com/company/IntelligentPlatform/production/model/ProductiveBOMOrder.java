package com.company.IntelligentPlatform.production.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class ProductiveBOMOrder extends ServiceEntityNode {

	public final static String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public final static String SENAME = "BillOfMaterialOrder";

    public static final int STATUS_INITIAL = 1;

	public static final int STATUS_INUSE = 2;

	public static final int STATUS_RETIRED = 3;

	protected String refMaterialSKUUUID;

	protected double amount;

	protected double amountWithLossRate;

	protected String refUnitUUID;

	protected int status;

	protected int itemCategory;

	protected Date planStartDate;

	protected Date actualStartDate;

	protected Date planStartPrepareDate;

	protected Date actualStartPrepareDate;

	protected double selfLeadTime;

	protected double comLeadTime;

	protected String refRouteOrderUUID;

	protected int leadTimeCalMode;

	public ProductiveBOMOrder() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.parentNodeUUID = super.uuid;
		this.rootNodeUUID = super.uuid;
		this.status = STATUS_INITIAL;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(Date planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public Date getActualStartPrepareDate() {
		return actualStartPrepareDate;
	}

	public void setActualStartPrepareDate(Date actualStartPrepareDate) {
		this.actualStartPrepareDate = actualStartPrepareDate;
	}

	public double getSelfLeadTime() {
		return selfLeadTime;
	}

	public void setSelfLeadTime(double selfLeadTime) {
		this.selfLeadTime = selfLeadTime;
	}

	public double getComLeadTime() {
		return comLeadTime;
	}

	public void setComLeadTime(double comLeadTime) {
		this.comLeadTime = comLeadTime;
	}

	public String getRefRouteOrderUUID() {
		return refRouteOrderUUID;
	}

	public void setRefRouteOrderUUID(String refRouteOrderUUID) {
		this.refRouteOrderUUID = refRouteOrderUUID;
	}

	public int getLeadTimeCalMode() {
		return leadTimeCalMode;
	}

	public void setLeadTimeCalMode(int leadTimeCalMode) {
		this.leadTimeCalMode = leadTimeCalMode;
	}

	public double getAmountWithLossRate() {
		return amountWithLossRate;
	}

	public void setAmountWithLossRate(double amountWithLossRate) {
		this.amountWithLossRate = amountWithLossRate;
	}

}

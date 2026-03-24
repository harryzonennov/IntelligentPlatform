package com.company.IntelligentPlatform.production.model;

import java.time.LocalDateTime;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProductiveBOMItem extends BillOfMaterialItem{

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProductiveBOMItem;

	protected double amount;

	protected double amountWithLossRate;

	protected String refUnitUUID;

	protected int status;

	protected LocalDateTime planStartDate;

	protected LocalDateTime actualStartDate;

	protected LocalDateTime planStartPrepareDate;

	protected LocalDateTime actualStartPrepareDate;

	protected LocalDateTime planEndDate;

	protected LocalDateTime acutalEndDate;

	protected double selfLeadTime;

	protected double comLeadTime;

	protected String refRouteOrderUUID;

	protected int leadTimeCalMode;

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

	public LocalDateTime getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(LocalDateTime planStartDate) {
		this.planStartDate = planStartDate;
	}

	public LocalDateTime getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(LocalDateTime actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public LocalDateTime getPlanStartPrepareDate() {
		return planStartPrepareDate;
	}

	public void setPlanStartPrepareDate(LocalDateTime planStartPrepareDate) {
		this.planStartPrepareDate = planStartPrepareDate;
	}

	public LocalDateTime getActualStartPrepareDate() {
		return actualStartPrepareDate;
	}

	public void setActualStartPrepareDate(LocalDateTime actualStartPrepareDate) {
		this.actualStartPrepareDate = actualStartPrepareDate;
	}


	public double getAmountWithLossRate() {
		return amountWithLossRate;
	}

	public void setAmountWithLossRate(double amountWithLossRate) {
		this.amountWithLossRate = amountWithLossRate;
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

	public LocalDateTime getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(LocalDateTime planEndDate) {
		this.planEndDate = planEndDate;
	}

	public LocalDateTime getAcutalEndDate() {
		return acutalEndDate;
	}

	public void setAcutalEndDate(LocalDateTime acutalEndDate) {
		this.acutalEndDate = acutalEndDate;
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

}

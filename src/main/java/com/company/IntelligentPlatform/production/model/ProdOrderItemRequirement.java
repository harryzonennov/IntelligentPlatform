package com.company.IntelligentPlatform.production.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;

public class ProdOrderItemRequirement extends ReferenceNode {

	public final static String NODENAME = IServiceModelConstants.ProdOrderItemRequirement;

	public final static String SENAME = ProductionOrder.SENAME;

	protected int itemIndex;

	protected double amount;

	protected String refUnitUUID;

	protected int status;

	protected Date planStartDate;

	protected Date actualStartDate;

	protected Date planStartPrepareDate;

	protected Date actualStartPrepareDate;

	protected double bomLeadTime;

	protected double compoundLeadTime;

	protected int documentType;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_INPROCESS = 2;

	public static final int STATUS_AVAILABLE = 3;

	public ProdOrderItemRequirement() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
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

	public double getBomLeadTime() {
		return bomLeadTime;
	}

	public void setBomLeadTime(double bomLeadTime) {
		this.bomLeadTime = bomLeadTime;
	}

	public double getCompoundLeadTime() {
		return compoundLeadTime;
	}

	public void setCompoundLeadTime(double compoundLeadTime) {
		this.compoundLeadTime = compoundLeadTime;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

}

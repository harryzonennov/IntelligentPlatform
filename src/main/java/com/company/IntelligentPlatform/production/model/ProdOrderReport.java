package com.company.IntelligentPlatform.production.model;

import java.util.Date;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProdOrderReport extends ServiceEntityNode {

	public final static String NODENAME = IServiceModelConstants.ProdOrderReport;

	public final static String SENAME = ProductionOrder.SENAME;

	protected int reportStatus;

	protected int reportCategory;

	protected Date reportTime;

	protected String reportedBy;

	protected String refMaterialSKUUUID;

	protected double grossAmount;

	protected double grossPrice;

	protected String refUnitUUID;

	protected String productionBatchNumber;

	public static final int REPCATEGORY_DONE = 1;

	public static final int REPCATEGORY_PROBLEM = 2;

	public static final int REPSTATUS_INIT = 1;

	public static final int REPSTATUS_FINISHED = 2;

	public ProdOrderReport() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.reportCategory = REPCATEGORY_DONE;
		this.reportStatus = REPSTATUS_INIT;
	}

	public int getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public int getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(int reportCategory) {
		this.reportCategory = reportCategory;
	}

	public String getRefMaterialSKUUUID() {
		return refMaterialSKUUUID;
	}

	public void setRefMaterialSKUUUID(String refMaterialSKUUUID) {
		this.refMaterialSKUUUID = refMaterialSKUUUID;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

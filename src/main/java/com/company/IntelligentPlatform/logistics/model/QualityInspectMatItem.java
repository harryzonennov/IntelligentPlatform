package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - QualityInspectMatItem (extends DocMatItemNode)
 * Table: QualityInspectMatItem (schema: logistics)
 */
@Entity
@Table(name = "QualityInspectMatItem", schema = "logistics")
public class QualityInspectMatItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.QualityInspectMatItem;

	@Column(name = "itemInspectType")
	protected int itemInspectType;

	@Column(name = "productionBatchNumber")
	protected String productionBatchNumber;

	@Column(name = "itemCheckStatus")
	protected int itemCheckStatus;

	@Column(name = "checkDate")
	protected LocalDate checkDate;

	@Column(name = "checkTimes")
	protected int checkTimes;

	@Column(name = "itemCheckResult", length = 3000)
	protected String itemCheckResult;

	@Column(name = "sampleRate")
	protected double sampleRate;

	@Column(name = "sampleAmount")
	protected double sampleAmount;

	@Column(name = "sampleUnitUUID")
	protected String sampleUnitUUID;

	@Column(name = "refWarehouseAreaUUID")
	protected String refWarehouseAreaUUID;

	@Column(name = "failAmount")
	protected double failAmount;

	@Column(name = "failRefUnitUUID")
	protected String failRefUnitUUID;

	@Column(name = "refWasteWarehouseUUID")
	protected String refWasteWarehouseUUID;

	@Column(name = "refWasteWareAreaUUID")
	protected String refWasteWareAreaUUID;

	public int getItemInspectType() {
		return itemInspectType;
	}

	public void setItemInspectType(int itemInspectType) {
		this.itemInspectType = itemInspectType;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public int getItemCheckStatus() {
		return itemCheckStatus;
	}

	public void setItemCheckStatus(int itemCheckStatus) {
		this.itemCheckStatus = itemCheckStatus;
	}

	public LocalDate getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(LocalDate checkDate) {
		this.checkDate = checkDate;
	}

	public int getCheckTimes() {
		return checkTimes;
	}

	public void setCheckTimes(int checkTimes) {
		this.checkTimes = checkTimes;
	}

	public String getItemCheckResult() {
		return itemCheckResult;
	}

	public void setItemCheckResult(String itemCheckResult) {
		this.itemCheckResult = itemCheckResult;
	}

	public double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public double getSampleAmount() {
		return sampleAmount;
	}

	public void setSampleAmount(double sampleAmount) {
		this.sampleAmount = sampleAmount;
	}

	public String getSampleUnitUUID() {
		return sampleUnitUUID;
	}

	public void setSampleUnitUUID(String sampleUnitUUID) {
		this.sampleUnitUUID = sampleUnitUUID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public double getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(double failAmount) {
		this.failAmount = failAmount;
	}

	public String getFailRefUnitUUID() {
		return failRefUnitUUID;
	}

	public void setFailRefUnitUUID(String failRefUnitUUID) {
		this.failRefUnitUUID = failRefUnitUUID;
	}

	public String getRefWasteWarehouseUUID() {
		return refWasteWarehouseUUID;
	}

	public void setRefWasteWarehouseUUID(String refWasteWarehouseUUID) {
		this.refWasteWarehouseUUID = refWasteWarehouseUUID;
	}

	public String getRefWasteWareAreaUUID() {
		return refWasteWareAreaUUID;
	}

	public void setRefWasteWareAreaUUID(String refWasteWareAreaUUID) {
		this.refWasteWareAreaUUID = refWasteWareAreaUUID;
	}

}

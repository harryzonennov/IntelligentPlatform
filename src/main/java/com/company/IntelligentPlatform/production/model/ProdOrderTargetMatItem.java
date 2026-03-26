package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - ProdOrderTargetMatItem (extends DocMatItemNode)
 * Table: ProdOrderTargetMatItem (schema: production)
 *
 * Represents a target output material item of a production order.
 */
@Entity
@Table(name = "ProdOrderTargetMatItem", catalog = "production")
public class ProdOrderTargetMatItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.ProdOrderTargetMatItem;

	public static final int ITEM_STATUS_INITIAL   = 1;

	public static final int ITEM_STATUS_INPROCESS = 2;

	public static final int ITEM_STATUS_FINISHED  = 3;

	public static final int STATUS_INIT = 1;

	public static final String FEILD_REF_SERIALID = "refSerialId";

	public static final int STATUS_INPROCESS = 2;

	public static final int STATUS_DONE_PRODUCTION = 100;

	public static final int STATUS_CANCELED = 900;

	@Column(name = "itemStatus")
	protected int itemStatus;

	@Column(name = "processIndex")
	protected int processIndex;

	@Column(name = "refSerialId")
	protected String refSerialId;

	@Column(name = "refUnitUUID")
	protected String refUnitUUID;

	@Column(name = "planAmount")
	protected double planAmount;

	@Column(name = "actualAmount")
	protected double actualAmount;

	@Column(name = "scrapAmount")
	protected double scrapAmount;

	@Column(name = "unitCost")
	protected double unitCost;

	@Column(name = "grossCost")
	protected double grossCost;

	@Column(name = "refWarehouseUUID")
	protected String refWarehouseUUID;

	@Column(name = "refWarehouseAreaUUID")
	protected String refWarehouseAreaUUID;

	@Column(name = "productionBatchNumber")
	protected String productionBatchNumber;

	@Column(name = "productionDate")
	protected LocalDate productionDate;

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(String refSerialId) {
		this.refSerialId = refSerialId;
	}

	public String getRefUnitUUID() {
		return refUnitUUID;
	}

	public void setRefUnitUUID(String refUnitUUID) {
		this.refUnitUUID = refUnitUUID;
	}

	public double getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(double planAmount) {
		this.planAmount = planAmount;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public double getScrapAmount() {
		return scrapAmount;
	}

	public void setScrapAmount(double scrapAmount) {
		this.scrapAmount = scrapAmount;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public double getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(double grossCost) {
		this.grossCost = grossCost;
	}

	public String getRefWarehouseUUID() {
		return refWarehouseUUID;
	}

	public void setRefWarehouseUUID(String refWarehouseUUID) {
		this.refWarehouseUUID = refWarehouseUUID;
	}

	public String getRefWarehouseAreaUUID() {
		return refWarehouseAreaUUID;
	}

	public void setRefWarehouseAreaUUID(String refWarehouseAreaUUID) {
		this.refWarehouseAreaUUID = refWarehouseAreaUUID;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

}

package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InventoryCheckOrder (extends DocumentContent)
 * Table: InventoryCheckOrder (schema: logistics)
 */
@Entity
@Table(name = "InventoryCheckOrder", catalog = "logistics")
public class InventoryCheckOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InventoryCheckOrder;

	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_INPROCESS = 2;

	public static final int STATUS_SUBMITTED = 3;

	public static final int STATUS_APPROVED = 3;

	public static final int STATUS_PROCESSDONE = 100;

	public static final int STATUS_DELIVERYDONE = 200;

	public static final int STATUS_REJECTED = 305;

	@Column(name = "refWarehouseUUID")
	protected String refWarehouseUUID;

	@Column(name = "refWarehouseAreaUUID")
	protected String refWarehouseAreaUUID;

	@Column(name = "grossUpdateValue")
	protected double grossUpdateValue;

	@Column(name = "grossCheckResult")
	protected int grossCheckResult;

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

	public double getGrossUpdateValue() {
		return grossUpdateValue;
	}

	public void setGrossUpdateValue(double grossUpdateValue) {
		this.grossUpdateValue = grossUpdateValue;
	}

	public int getGrossCheckResult() {
		return grossCheckResult;
	}

	public void setGrossCheckResult(int grossCheckResult) {
		this.grossCheckResult = grossCheckResult;
	}

}

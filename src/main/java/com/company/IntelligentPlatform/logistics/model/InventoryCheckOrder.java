package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InventoryCheckOrder (extends DocumentContent)
 * Table: InventoryCheckOrder (schema: logistics)
 */
@Entity
@Table(name = "InventoryCheckOrder", catalog = "logistics")
public class InventoryCheckOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.InventoryCheckOrder;

	public static final int STATUS_INITIAL = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_INPROCESS = DocumentContent.STATUS_INPROCESS;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_PROCESSDONE = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_DELIVERYDONE = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_REJECTED = DocumentContent.STATUS_REJECT_APPROVAL;

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

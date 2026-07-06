package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - WarehouseStore (extends DocumentContent)
 * Table: WarehouseStore (schema: logistics)
 */
@Entity
@Table(name = "WarehouseStore", catalog = "logistics")
public class WarehouseStore extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.WarehouseStore;

	public static final int STATUS_INITIAL = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_INPROCESS = DocumentContent.STATUS_INPROCESS;

	public static final int STATUS_DELIVERYDONE = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_PROCESSDONE = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_SUBMITTED = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ARCHIVED = DocumentContent.STATUS_ARCHIVED;

	public static final int STATUS_SUCCESS = 3;

	public static final int STATUS_FAILURE = 4;

	public static final String FIELD_REF_WAREHOUSE_UUID = "refWarehouseUUID";

	@Column(name = "refWarehouseUUID")
	protected String refWarehouseUUID;

	@Column(name = "refWarehouseAreaUUID")
	protected String refWarehouseAreaUUID;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

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

	public double getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(double grossPrice) {
		this.grossPrice = grossPrice;
	}

	public double getGrossPriceDisplay() {
		return grossPriceDisplay;
	}

	public void setGrossPriceDisplay(double grossPriceDisplay) {
		this.grossPriceDisplay = grossPriceDisplay;
	}

}

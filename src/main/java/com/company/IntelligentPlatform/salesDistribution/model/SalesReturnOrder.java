package com.company.IntelligentPlatform.salesDistribution.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesReturnOrder (extends DocumentContent)
 * Table: SalesReturnOrder (schema: sales)
 *
 * Cross-module refs:
 *   refInWarehouseUUID    → logistics/Warehouse
 *   refInboundDeliveryUUID → logistics/InboundDelivery
 */
@Entity
@Table(name = "SalesReturnOrder", catalog = "sales")
public class SalesReturnOrder extends DocumentContent {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SalesReturnOrder;

	public static final int STATUS_INITIAL         = DocumentContent.STATUS_INITIAL;

	public static final int STATUS_APPROVED        = DocumentContent.STATUS_APPROVED;

	public static final int STATUS_INDELIVERY      = DocumentContent.STATUS_INPROCESS;

	public static final int STATUS_DELIVERY_DONE   = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_DELIVERYDONE    = DocumentContent.STATUS_DELIVERYDONE;

	public static final int STATUS_PROCESS_DONE    = DocumentContent.STATUS_PROCESSDONE;

	public static final int STATUS_REJECT_APPROVAL = DocumentContent.STATUS_REJECT_APPROVAL;

	public static final int STATUS_SUBMITTED       = DocumentContent.STATUS_SUBMITTED;

	public static final int STATUS_ARCHIVED        = DocumentContent.STATUS_ARCHIVED;

	@Column(name = "grossPrice")
	protected double grossPrice;

	@Column(name = "grossPriceDisplay")
	protected double grossPriceDisplay;

	@Column(name = "refInWarehouseUUID")
	protected String refInWarehouseUUID;

	@Column(name = "refInboundDeliveryUUID")
	protected String refInboundDeliveryUUID;

	@Column(name = "barcode")
	protected String barcode;

	@Column(name = "taxRate")
	protected double taxRate;

	@Column(name = "productionBatchNumber")
	protected String productionBatchNumber;

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

	public String getRefInWarehouseUUID() {
		return refInWarehouseUUID;
	}

	public void setRefInWarehouseUUID(String refInWarehouseUUID) {
		this.refInWarehouseUUID = refInWarehouseUUID;
	}

	public String getRefInboundDeliveryUUID() {
		return refInboundDeliveryUUID;
	}

	public void setRefInboundDeliveryUUID(String refInboundDeliveryUUID) {
		this.refInboundDeliveryUUID = refInboundDeliveryUUID;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public String getProductionBatchNumber() {
		return productionBatchNumber;
	}

	public void setProductionBatchNumber(String productionBatchNumber) {
		this.productionBatchNumber = productionBatchNumber;
	}

}

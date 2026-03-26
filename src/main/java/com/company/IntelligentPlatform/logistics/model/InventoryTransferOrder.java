package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InventoryTransferOrder (extends Delivery)
 * Table: InventoryTransferOrder (schema: logistics)
 *
 * Cross-module refs (stored as UUID String, no FK):
 *   refInboundWarehouseUUID  → Warehouse
 *   refInboundWarehouseAreaUUID → Warehouse area
 *   refInboundDeliveryUUID → InboundDelivery (same module)
 *   refOutboundDeliveryUUID → OutboundDelivery (same module)
 */
@Entity
@Table(name = "InventoryTransferOrder", catalog = "logistics")
public class InventoryTransferOrder extends Delivery {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InventoryTransferOrder;

	@Column(name = "grossOutboundFee")
	protected double grossOutboundFee;

	@Column(name = "grossStorageFee")
	protected double grossStorageFee;

	@Column(name = "refInboundWarehouseUUID")
	protected String refInboundWarehouseUUID;

	@Column(name = "refInboundWarehouseAreaUUID")
	protected String refInboundWarehouseAreaUUID;

	@Column(name = "refInboundDeliveryUUID")
	protected String refInboundDeliveryUUID;

	@Column(name = "refOutboundDeliveryUUID")
	protected String refOutboundDeliveryUUID;

	public double getGrossOutboundFee() {
		return grossOutboundFee;
	}

	public void setGrossOutboundFee(double grossOutboundFee) {
		this.grossOutboundFee = grossOutboundFee;
	}

	public double getGrossStorageFee() {
		return grossStorageFee;
	}

	public void setGrossStorageFee(double grossStorageFee) {
		this.grossStorageFee = grossStorageFee;
	}

	public String getRefInboundWarehouseUUID() {
		return refInboundWarehouseUUID;
	}

	public void setRefInboundWarehouseUUID(String refInboundWarehouseUUID) {
		this.refInboundWarehouseUUID = refInboundWarehouseUUID;
	}

	public String getRefInboundWarehouseAreaUUID() {
		return refInboundWarehouseAreaUUID;
	}

	public void setRefInboundWarehouseAreaUUID(String refInboundWarehouseAreaUUID) {
		this.refInboundWarehouseAreaUUID = refInboundWarehouseAreaUUID;
	}

	public String getRefInboundDeliveryUUID() {
		return refInboundDeliveryUUID;
	}

	public void setRefInboundDeliveryUUID(String refInboundDeliveryUUID) {
		this.refInboundDeliveryUUID = refInboundDeliveryUUID;
	}

	public String getRefOutboundDeliveryUUID() {
		return refOutboundDeliveryUUID;
	}

	public void setRefOutboundDeliveryUUID(String refOutboundDeliveryUUID) {
		this.refOutboundDeliveryUUID = refOutboundDeliveryUUID;
	}

}

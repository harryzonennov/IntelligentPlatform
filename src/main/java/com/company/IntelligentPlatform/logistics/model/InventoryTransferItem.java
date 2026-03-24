package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InventoryTransferItem (extends DeliveryItem)
 * Table: InventoryTransferItem (schema: logistics)
 */
@Entity
@Table(name = "InventoryTransferItem", schema = "logistics")
public class InventoryTransferItem extends DeliveryItem {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InventoryTransferItem;

	@Column(name = "outboundFee")
	protected double outboundFee;

	@Column(name = "storageFee")
	protected double storageFee;

	@Column(name = "refOutboundItemUUID")
	protected String refOutboundItemUUID;

	@Column(name = "refInboundItemUUID")
	protected String refInboundItemUUID;

	@Column(name = "refStoreItemUUID")
	protected String refStoreItemUUID;

	@Column(name = "itemStatus")
	protected int itemStatus;

	@Column(name = "storeDay")
	protected int storeDay;

	public double getOutboundFee() {
		return outboundFee;
	}

	public void setOutboundFee(double outboundFee) {
		this.outboundFee = outboundFee;
	}

	public double getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(double storageFee) {
		this.storageFee = storageFee;
	}

	public String getRefOutboundItemUUID() {
		return refOutboundItemUUID;
	}

	public void setRefOutboundItemUUID(String refOutboundItemUUID) {
		this.refOutboundItemUUID = refOutboundItemUUID;
	}

	public String getRefInboundItemUUID() {
		return refInboundItemUUID;
	}

	public void setRefInboundItemUUID(String refInboundItemUUID) {
		this.refInboundItemUUID = refInboundItemUUID;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public int getStoreDay() {
		return storeDay;
	}

	public void setStoreDay(int storeDay) {
		this.storeDay = storeDay;
	}

}

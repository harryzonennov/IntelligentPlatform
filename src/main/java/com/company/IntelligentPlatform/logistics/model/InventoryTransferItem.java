package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocumentContent;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinLogistics - InventoryTransferItem (extends DeliveryItem)
 * Table: InventoryTransferItem (schema: logistics)
 */
@Entity
@Table(name = "InventoryTransferItem", catalog = "logistics")
public class InventoryTransferItem extends DeliveryItem {

	public static final String NODENAME = IServiceModelConstants.InventoryTransferItem;

	public static final String SENAME = IServiceModelConstants.InventoryTransferOrder;

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

	public InventoryTransferItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = DocumentContent.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER;
	}

}

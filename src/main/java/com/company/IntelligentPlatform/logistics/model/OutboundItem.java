package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - OutboundItem (extends DeliveryItem)
 * Table: OutboundItem (schema: logistics)
 *
 * Cross-module ref: refStoreItemUUID → WarehouseStoreItem (same module)
 */
@Entity
@Table(name = "OutboundItem", catalog = "logistics")
public class OutboundItem extends DeliveryItem {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.OutboundItem;

	@Column(name = "outboundFee")
	protected double outboundFee;

	@Column(name = "storageFee")
	protected double storageFee;

	@Column(name = "refStoreItemUUID")
	protected String refStoreItemUUID;

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

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public int getStoreDay() {
		return storeDay;
	}

	public void setStoreDay(int storeDay) {
		this.storeDay = storeDay;
	}

}

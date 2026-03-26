package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InboundItem (extends DeliveryItem)
 * Table: InboundItem (schema: logistics)
 *
 * Cross-module ref: refStoreItemUUID → WarehouseStoreItem (same module)
 */
@Entity
@Table(name = "InboundItem", catalog = "logistics")
public class InboundItem extends DeliveryItem {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InboundItem;

	@Column(name = "inboundFee")
	protected double inboundFee;

	@Column(name = "refStoreItemUUID")
	protected String refStoreItemUUID;

	public double getInboundFee() {
		return inboundFee;
	}

	public void setInboundFee(double inboundFee) {
		this.inboundFee = inboundFee;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

}

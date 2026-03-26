package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - OutboundDelivery (extends Delivery)
 * Table: OutboundDelivery (schema: logistics)
 */
@Entity
@Table(name = "OutboundDelivery", catalog = "logistics")
public class OutboundDelivery extends Delivery {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.OutboundDelivery;

	@Column(name = "grossOutboundFee")
	protected double grossOutboundFee;

	@Column(name = "grossStorageFee")
	protected double grossStorageFee;

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

}

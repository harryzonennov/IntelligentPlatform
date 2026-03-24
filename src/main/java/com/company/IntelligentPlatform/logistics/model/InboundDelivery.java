package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - InboundDelivery (extends Delivery)
 * Table: InboundDelivery (schema: logistics)
 */
@Entity
@Table(name = "InboundDelivery", schema = "logistics")
public class InboundDelivery extends Delivery {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.InboundDelivery;

	@Column(name = "grossInboundFee")
	protected double grossInboundFee;

	public double getGrossInboundFee() {
		return grossInboundFee;
	}

	public void setGrossInboundFee(double grossInboundFee) {
		this.grossInboundFee = grossInboundFee;
	}

}

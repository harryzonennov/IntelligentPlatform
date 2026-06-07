package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "OutboundDeliveryAttachment", catalog = "logistics")
public class OutboundDeliveryAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.OutboundDeliveryAttachment;

	public static final String SENAME = IServiceModelConstants.OutboundDelivery;

	public OutboundDeliveryAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

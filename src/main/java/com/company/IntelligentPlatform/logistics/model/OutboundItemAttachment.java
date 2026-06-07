package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "OutboundItemAttachment", catalog = "logistics")
public class OutboundItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.OutboundItemAttachment;

	public static final String SENAME = IServiceModelConstants.OutboundDelivery;

	public OutboundItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InboundItemAttachment", catalog = "logistics")
public class InboundItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InboundItemAttachment;

	public static final String SENAME = IServiceModelConstants.InboundDelivery;

	public InboundItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

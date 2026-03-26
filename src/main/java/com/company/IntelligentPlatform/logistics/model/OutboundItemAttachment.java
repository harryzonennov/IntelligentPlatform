package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class OutboundItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.OutboundItemAttachment;

	public static final String SENAME = IServiceModelConstants.OutboundDelivery;

	public OutboundItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

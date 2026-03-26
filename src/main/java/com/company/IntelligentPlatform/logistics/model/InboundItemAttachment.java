package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InboundItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InboundItemAttachment;

	public static final String SENAME = IServiceModelConstants.InboundDelivery;

	public InboundItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

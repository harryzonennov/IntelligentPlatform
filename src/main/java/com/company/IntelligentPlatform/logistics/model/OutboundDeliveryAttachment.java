package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class OutboundDeliveryAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.OutboundDeliveryAttachment;

	public static final String SENAME = IServiceModelConstants.OutboundDelivery;

	public OutboundDeliveryAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InboundDeliveryAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InboundDeliveryAttachment;

	public static final String SENAME = IServiceModelConstants.InboundDelivery;

	public InboundDeliveryAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

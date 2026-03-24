package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class PurchaseRequestAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseRequestAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseRequest;

	public PurchaseRequestAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

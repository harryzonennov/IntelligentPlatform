package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class PurchaseReturnOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseReturnOrderAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseReturnOrder;

	public PurchaseReturnOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

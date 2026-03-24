package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class PurchaseReturnMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseReturnMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseReturnOrder;

	public PurchaseReturnMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

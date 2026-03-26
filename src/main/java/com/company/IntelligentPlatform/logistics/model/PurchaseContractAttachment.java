package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class PurchaseContractAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseContractAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseContract;

	public PurchaseContractAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

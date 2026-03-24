package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class PurchaseContractMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseContractMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseContract;

	public PurchaseContractMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}


}

package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InventoryCheckItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryCheckItemAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryCheckOrder;

	public InventoryCheckItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

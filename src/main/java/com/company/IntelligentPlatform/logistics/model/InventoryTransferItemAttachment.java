package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InventoryTransferItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryTransferItemAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryTransferOrder;

	public InventoryTransferItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

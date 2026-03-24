package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InventoryTransferOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryTransferOrderAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryTransferOrder;

	public InventoryTransferOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

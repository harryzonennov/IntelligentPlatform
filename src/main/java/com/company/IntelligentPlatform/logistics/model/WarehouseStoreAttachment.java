package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class WarehouseStoreAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WarehouseStoreAttachment;

	public static final String SENAME = IServiceModelConstants.WarehouseStore;

	public WarehouseStoreAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

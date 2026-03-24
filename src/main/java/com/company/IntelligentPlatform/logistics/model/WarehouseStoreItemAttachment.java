package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class WarehouseStoreItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WarehouseStoreItemAttachment;

	public static final String SENAME = IServiceModelConstants.WarehouseStore;

	public WarehouseStoreItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

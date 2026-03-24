package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class WarehouseAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WarehouseAttachment;

	public static final String SENAME = IServiceModelConstants.Warehouse;

	public WarehouseAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

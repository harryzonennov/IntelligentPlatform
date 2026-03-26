package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class RepairProdTargetItemAttachment extends ProdOrderTargetItemAttachment {

	public static final String NODENAME = IServiceModelConstants.RepairProdTargetItemAttachment;

	public static final String SENAME = RepairProdOrder.SENAME;

	public RepairProdTargetItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

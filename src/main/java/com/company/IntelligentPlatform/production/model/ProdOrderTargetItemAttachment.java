package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class ProdOrderTargetItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.ProdOrderTargetItemAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionOrder;

	public ProdOrderTargetItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

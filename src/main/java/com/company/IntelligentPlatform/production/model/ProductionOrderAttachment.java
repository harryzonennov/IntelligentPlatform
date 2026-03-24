package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class ProductionOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.ProductionOrderAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionOrder;

	public ProductionOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}


}

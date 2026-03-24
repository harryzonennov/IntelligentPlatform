package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProductionPlanAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.ProductionPlanAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionPlan;

	public ProductionPlanAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

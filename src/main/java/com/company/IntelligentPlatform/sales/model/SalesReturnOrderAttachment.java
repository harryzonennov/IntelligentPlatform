package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class SalesReturnOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesReturnOrderAttachment;

	public static final String SENAME = IServiceModelConstants.SalesReturnOrder;

	public SalesReturnOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

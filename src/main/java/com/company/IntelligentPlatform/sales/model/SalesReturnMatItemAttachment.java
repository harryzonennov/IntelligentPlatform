package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class SalesReturnMatItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesReturnMatItemAttachment;

	public static final String SENAME = IServiceModelConstants.SalesReturnOrder;

	public SalesReturnMatItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

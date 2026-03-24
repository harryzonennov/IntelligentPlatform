package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class SalesForcastAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesForcastAttachment;

	public static final String SENAME = IServiceModelConstants.SalesForcast;

	public SalesForcastAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

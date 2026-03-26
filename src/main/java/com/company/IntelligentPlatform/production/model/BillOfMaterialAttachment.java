package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class BillOfMaterialAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.BillOfMaterialAttachment;

	public static final String SENAME = IServiceModelConstants.BillOfMaterialOrder;

	public BillOfMaterialAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

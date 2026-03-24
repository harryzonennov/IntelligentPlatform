package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class MaterialSKUAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialSKUAttachment;

	public static final String SENAME = IServiceModelConstants.MaterialStockKeepUnit;

	public MaterialSKUAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class MaterialSKUUnitAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialSKUUnitAttachment;

	public static final String SENAME = IServiceModelConstants.MaterialStockKeepUnit;

	public MaterialSKUUnitAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

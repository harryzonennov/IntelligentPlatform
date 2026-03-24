package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class MaterialUnitAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialUnitAttachment;

	public static final String SENAME = IServiceModelConstants.Material;

	public MaterialUnitAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

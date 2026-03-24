package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class MaterialAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialAttachment;

	public static final String SENAME = IServiceModelConstants.Material;

	public MaterialAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

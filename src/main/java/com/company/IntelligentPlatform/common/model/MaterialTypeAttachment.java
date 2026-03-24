package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class MaterialTypeAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialTypeAttachment;

	public static final String SENAME = IServiceModelConstants.MaterialType;

	public MaterialTypeAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

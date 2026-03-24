package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class QualityInsMatItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.QualityInsMatItemAttachment;

	public static final String SENAME = IServiceModelConstants.QualityInspectOrder;

	public QualityInsMatItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}


}

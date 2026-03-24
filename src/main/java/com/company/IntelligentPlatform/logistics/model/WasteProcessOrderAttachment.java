package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class WasteProcessOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WasteProcessOrderAttachment;

	public static final String SENAME = WasteProcessOrder.SENAME;

	public WasteProcessOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

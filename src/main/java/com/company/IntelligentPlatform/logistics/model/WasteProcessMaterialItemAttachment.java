package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class WasteProcessMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WasteProcessMaterialItemAttachment;

	public static final String SENAME = WasteProcessOrder.SENAME;

	public WasteProcessMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InquiryMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InquiryMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.Inquiry;

	public InquiryMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

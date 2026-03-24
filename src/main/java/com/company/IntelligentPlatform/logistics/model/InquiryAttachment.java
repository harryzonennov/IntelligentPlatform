package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InquiryAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InquiryAttachment;

	public static final String SENAME = IServiceModelConstants.Inquiry;

	public InquiryAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

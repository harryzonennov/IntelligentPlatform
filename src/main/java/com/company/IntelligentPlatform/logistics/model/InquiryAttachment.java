package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InquiryAttachment", catalog = "logistics")
public class InquiryAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InquiryAttachment;

	public static final String SENAME = IServiceModelConstants.Inquiry;

	public InquiryAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

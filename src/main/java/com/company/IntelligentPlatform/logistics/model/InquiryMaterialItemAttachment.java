package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InquiryMaterialItemAttachment", catalog = "logistics")
public class InquiryMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InquiryMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.Inquiry;

	public InquiryMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

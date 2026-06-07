package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "PurchaseRequestAttachment", catalog = "logistics")
public class PurchaseRequestAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseRequestAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseRequest;

	public PurchaseRequestAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

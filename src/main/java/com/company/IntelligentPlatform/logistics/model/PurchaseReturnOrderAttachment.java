package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "PurchaseReturnOrderAttachment", catalog = "logistics")
public class PurchaseReturnOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseReturnOrderAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseReturnOrder;

	public PurchaseReturnOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

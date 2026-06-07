package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "PurchaseRequestMaterialItemAttachment", catalog = "logistics")
public class PurchaseRequestMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseRequestMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseRequest;

	public PurchaseRequestMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

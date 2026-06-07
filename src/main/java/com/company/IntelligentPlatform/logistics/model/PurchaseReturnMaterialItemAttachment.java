package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "PurchaseReturnMaterialItemAttachment", catalog = "logistics")
public class PurchaseReturnMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseReturnMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseReturnOrder;

	public PurchaseReturnMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

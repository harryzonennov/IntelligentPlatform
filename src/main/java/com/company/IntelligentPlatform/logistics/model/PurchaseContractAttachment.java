package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PurchaseContractAttachment", catalog = "logistics")
public class PurchaseContractAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseContractAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseContract;

	public PurchaseContractAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

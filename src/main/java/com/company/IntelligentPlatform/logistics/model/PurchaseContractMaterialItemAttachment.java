package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "PurchaseContractMaterialItemAttachment", catalog = "logistics")
public class PurchaseContractMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseContractMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.PurchaseContract;

	public PurchaseContractMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

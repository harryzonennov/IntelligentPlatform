package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InventoryTransferOrderAttachment", catalog = "logistics")
public class InventoryTransferOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryTransferOrderAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryTransferOrder;

	public InventoryTransferOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

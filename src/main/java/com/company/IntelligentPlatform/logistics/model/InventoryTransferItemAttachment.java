package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InventoryTransferItemAttachment", catalog = "logistics")
public class InventoryTransferItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryTransferItemAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryTransferOrder;

	public InventoryTransferItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

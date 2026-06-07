package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InventoryCheckAttachment", catalog = "logistics")
public class InventoryCheckAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryCheckAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryCheckOrder;

	public InventoryCheckAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

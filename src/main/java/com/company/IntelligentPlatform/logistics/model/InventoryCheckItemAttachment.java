package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "InventoryCheckItemAttachment", catalog = "logistics")
public class InventoryCheckItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.InventoryCheckItemAttachment;

	public static final String SENAME = IServiceModelConstants.InventoryCheckOrder;

	public InventoryCheckItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

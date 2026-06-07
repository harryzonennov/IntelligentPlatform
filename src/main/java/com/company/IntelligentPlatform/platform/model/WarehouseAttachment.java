package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "WarehouseAttachment", catalog = "platform")
public class WarehouseAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WarehouseAttachment;

	public static final String SENAME = IServiceModelConstants.Warehouse;

	public WarehouseAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

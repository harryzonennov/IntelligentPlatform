package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "WarehouseStoreAttachment", catalog = "logistics")
public class WarehouseStoreAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WarehouseStoreAttachment;

	public static final String SENAME = IServiceModelConstants.WarehouseStore;

	public WarehouseStoreAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

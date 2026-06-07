package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "WarehouseStoreItemAttachment", catalog = "logistics")
public class WarehouseStoreItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WarehouseStoreItemAttachment;

	public static final String SENAME = IServiceModelConstants.WarehouseStore;

	public WarehouseStoreItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

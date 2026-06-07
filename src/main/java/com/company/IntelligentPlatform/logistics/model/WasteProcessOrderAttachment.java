package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "WasteProcessOrderAttachment", catalog = "logistics")
public class WasteProcessOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WasteProcessOrderAttachment;

	public static final String SENAME = WasteProcessOrder.SENAME;

	public WasteProcessOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

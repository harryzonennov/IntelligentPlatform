package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "WasteProcessMaterialItemAttachment", catalog = "logistics")
public class WasteProcessMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.WasteProcessMaterialItemAttachment;

	public static final String SENAME = WasteProcessOrder.SENAME;

	public WasteProcessMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

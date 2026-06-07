package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "MaterialSKUAttachment", catalog = "platform")
public class MaterialSKUAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialSKUAttachment;

	public static final String SENAME = IServiceModelConstants.MaterialStockKeepUnit;

	public MaterialSKUAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

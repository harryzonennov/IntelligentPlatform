package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "MaterialSKUUnitAttachment", catalog = "platform")
public class MaterialSKUUnitAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialSKUUnitAttachment;

	public static final String SENAME = IServiceModelConstants.MaterialStockKeepUnit;

	public MaterialSKUUnitAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

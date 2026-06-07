package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "MaterialTypeAttachment", catalog = "platform")
public class MaterialTypeAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialTypeAttachment;

	public static final String SENAME = IServiceModelConstants.MaterialType;

	public MaterialTypeAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "MaterialAttachment", catalog = "platform")
public class MaterialAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialAttachment;

	public static final String SENAME = IServiceModelConstants.Material;

	public MaterialAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

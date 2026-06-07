package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "MaterialUnitAttachment", catalog = "platform")
public class MaterialUnitAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.MaterialUnitAttachment;

	public static final String SENAME = IServiceModelConstants.Material;

	public MaterialUnitAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

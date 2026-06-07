package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "BillOfMaterialAttachment", catalog = "production")
public class BillOfMaterialAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.BillOfMaterialAttachment;

	public static final String SENAME = IServiceModelConstants.BillOfMaterialOrder;

	public BillOfMaterialAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

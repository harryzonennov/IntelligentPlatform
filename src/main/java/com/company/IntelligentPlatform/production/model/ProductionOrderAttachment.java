package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "ProductionOrderAttachment", catalog = "production")
public class ProductionOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.ProductionOrderAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionOrder;

	public ProductionOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

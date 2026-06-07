package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "ProdOrderTargetItemAttachment", catalog = "production")
public class ProdOrderTargetItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.ProdOrderTargetItemAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionOrder;

	public ProdOrderTargetItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

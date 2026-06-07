package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocAttachmentNode;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "ProductionPlanAttachment", catalog = "production")
public class ProductionPlanAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.ProductionPlanAttachment;

	public static final String SENAME = IServiceModelConstants.ProductionPlan;

	public ProductionPlanAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

package com.company.IntelligentPlatform.salesDistribution.model;

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
@Table(name = "SalesReturnOrderAttachment", catalog = "sales")
public class SalesReturnOrderAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesReturnOrderAttachment;

	public static final String SENAME = IServiceModelConstants.SalesReturnOrder;

	public SalesReturnOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

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
@Table(name = "SalesReturnMatItemAttachment", catalog = "sales")
public class SalesReturnMatItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesReturnMatItemAttachment;

	public static final String SENAME = IServiceModelConstants.SalesReturnOrder;

	public SalesReturnMatItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

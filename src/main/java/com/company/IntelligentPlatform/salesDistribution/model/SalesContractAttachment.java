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
@Table(name = "SalesContractAttachment", catalog = "sales")
public class SalesContractAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesContractAttachment;

	public static final String SENAME = IServiceModelConstants.SalesContract;

	public SalesContractAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

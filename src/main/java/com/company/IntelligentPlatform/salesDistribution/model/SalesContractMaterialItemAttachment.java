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
@Table(name = "SalesContractMaterialItemAttachment", catalog = "sales")
public class SalesContractMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesContractMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.SalesContract;

	public SalesContractMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

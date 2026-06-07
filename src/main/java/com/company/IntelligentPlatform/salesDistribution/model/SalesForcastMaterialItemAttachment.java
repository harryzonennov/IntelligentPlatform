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
@Table(name = "SalesForcastMaterialItemAttachment", catalog = "sales")
public class SalesForcastMaterialItemAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesForcastMaterialItemAttachment;

	public static final String SENAME = IServiceModelConstants.SalesForcast;

	public SalesForcastMaterialItemAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

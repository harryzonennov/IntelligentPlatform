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
@Table(name = "SalesForcastAttachment", catalog = "sales")
public class SalesForcastAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.SalesForcastAttachment;

	public static final String SENAME = IServiceModelConstants.SalesForcast;

	public SalesForcastAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

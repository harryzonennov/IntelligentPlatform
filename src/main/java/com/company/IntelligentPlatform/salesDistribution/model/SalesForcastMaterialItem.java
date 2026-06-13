package com.company.IntelligentPlatform.salesDistribution.model;

import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesForcastMaterialItem (extends DocMatItemNode)
 * Table: SalesForcastMaterialItem (schema: sales)
 */
@Entity
@Table(name = "SalesForcastMaterialItem", catalog = "sales")
public class SalesForcastMaterialItem extends DocMatItemNode {

	public static final String NODENAME = IServiceModelConstants.SalesForcastMaterialItem;

	public static final String SENAME = IServiceModelConstants.SalesForcast;

	public SalesForcastMaterialItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST;
	}

}

package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesForcastMaterialItem (extends DocMatItemNode)
 * Table: SalesForcastMaterialItem (schema: sales)
 */
@Entity
@Table(name = "SalesForcastMaterialItem", schema = "sales")
public class SalesForcastMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.SalesForcastMaterialItem;

}

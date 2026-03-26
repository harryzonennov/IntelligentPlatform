package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - RepairProdOrder (extends ProductionOrder)
 * Table: RepairProdOrder (schema: production)
 */
@Entity
@Table(name = "RepairProdOrder", catalog = "production")
public class RepairProdOrder extends ProductionOrder {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.RepairProdOrder;

	public static final int GENITEM_MODE_NO_NEED   = 1;

	public static final int GENITEM_MODE_ADD_MANUAL = 2;

}

package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - BillOfMaterialTemplate (extends BillOfMaterialOrder)
 * Table: BillOfMaterialTemplate (schema: production)
 *
 * Reusable BOM template that BillOfMaterialOrder instances can reference via refTemplateUUID.
 */
@Entity
@Table(name = "BillOfMaterialTemplate", schema = "production")
public class BillOfMaterialTemplate extends BillOfMaterialOrder {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.BillOfMaterialTemplate;

}

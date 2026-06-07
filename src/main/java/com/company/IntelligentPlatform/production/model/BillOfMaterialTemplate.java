package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinProduction - BillOfMaterialTemplate (extends BillOfMaterialOrder)
 * Table: BillOfMaterialTemplate (schema: production)
 *
 * Reusable BOM template that BillOfMaterialOrder instances can reference via refTemplateUUID.
 */
@Entity
@Table(name = "BillOfMaterialTemplate", catalog = "production")
public class BillOfMaterialTemplate extends BillOfMaterialOrder {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.BillOfMaterialTemplate;

}

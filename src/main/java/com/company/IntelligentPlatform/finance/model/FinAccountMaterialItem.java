package com.company.IntelligentPlatform.finance.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinFinance - FinAccountMaterialItem.java
 * New table: FinAccountMaterialItem (schema: finance)
 * Hierarchy: ServiceEntityNode → ReferenceNode → DocMatItemNode → FinAccountMaterialItem
 * Line-item node of a FinAccount. All material/price fields inherited from DocMatItemNode.
 */
@Entity
@Table(name = "FinAccountMaterialItem", catalog = "finance")
public class FinAccountMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.FinAccountMaterialItem;

}

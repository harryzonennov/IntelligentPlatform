package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - PurchaseRequestMaterialItem (extends DocMatItemNode)
 * Table: PurchaseRequestMaterialItem (schema: logistics)
 */
@Entity
@Table(name = "PurchaseRequestMaterialItem", catalog = "logistics")
public class PurchaseRequestMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.PurchaseRequestMaterialItem;

	@Column(name = "itemStatus")
	protected int itemStatus;

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

}

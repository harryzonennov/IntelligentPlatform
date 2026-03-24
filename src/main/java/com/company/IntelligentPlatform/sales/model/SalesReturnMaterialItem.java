package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesReturnMaterialItem (extends DocMatItemNode)
 * Table: SalesReturnMaterialItem (schema: sales)
 *
 * Cross-module ref: refFinAccountUUID → finance schema
 */
@Entity
@Table(name = "SalesReturnMaterialItem", schema = "sales")
public class SalesReturnMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.SalesReturnMaterialItem;

	public static final int STATUS_INITIAL           = 1;

	public static final int STATUS_DONE              = 2;

	public static final int AVAILABLE_CHECK_INITIAL  = 1;

	public static final int AVAILABLE_CHECK_OK       = 2;

	public static final int AVAILABLE_CHECK_ERROR    = 3;

	@Column(name = "refFinAccountUUID")
	protected String refFinAccountUUID;

	@Column(name = "refDocItemUUID")
	protected String refDocItemUUID;

	@Column(name = "refDocItemType")
	protected int refDocItemType;

	public String getRefFinAccountUUID() {
		return refFinAccountUUID;
	}

	public void setRefFinAccountUUID(String refFinAccountUUID) {
		this.refFinAccountUUID = refFinAccountUUID;
	}

	public String getRefDocItemUUID() {
		return refDocItemUUID;
	}

	public void setRefDocItemUUID(String refDocItemUUID) {
		this.refDocItemUUID = refDocItemUUID;
	}

	public int getRefDocItemType() {
		return refDocItemType;
	}

	public void setRefDocItemType(int refDocItemType) {
		this.refDocItemType = refDocItemType;
	}

}

package com.company.IntelligentPlatform.salesDistribution.model;

import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.DocumentContent;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinSalesDistribution - SalesReturnMaterialItem (extends DocMatItemNode)
 * Table: SalesReturnMaterialItem (schema: sales)
 *
 * Cross-module ref: refFinAccountUUID → finance schema
 */
@Entity
@Table(name = "SalesReturnMaterialItem", catalog = "sales")
public class SalesReturnMaterialItem extends DocMatItemNode {

	public static final String NODENAME = IServiceModelConstants.SalesReturnMaterialItem;

	public static final String SENAME = IServiceModelConstants.SalesReturnOrder;

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

	public SalesReturnMaterialItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = DocumentContent.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER;
	}

}

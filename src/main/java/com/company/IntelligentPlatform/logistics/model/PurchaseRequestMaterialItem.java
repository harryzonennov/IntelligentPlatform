package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.DocumentContent;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinLogistics - PurchaseRequestMaterialItem (extends DocMatItemNode)
 * Table: PurchaseRequestMaterialItem (schema: logistics)
 */
@Entity
@Table(name = "PurchaseRequestMaterialItem", catalog = "logistics")
public class PurchaseRequestMaterialItem extends DocMatItemNode {

	public static final String NODENAME = IServiceModelConstants.PurchaseRequestMaterialItem;

	public static final String SENAME = IServiceModelConstants.PurchaseRequest;

	@Column(name = "itemStatus")
	protected int itemStatus;

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public PurchaseRequestMaterialItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = DocumentContent.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST;
	}

}

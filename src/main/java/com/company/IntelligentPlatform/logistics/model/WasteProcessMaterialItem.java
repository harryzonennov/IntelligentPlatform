package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.IDefDocumentResource;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

/**
 * Migrated from: ThorsteinLogistics - WasteProcessMaterialItem (extends DocMatItemNode)
 * Table: WasteProcessMaterialItem (schema: logistics)
 */
@Entity
@Table(name = "WasteProcessMaterialItem", catalog = "logistics")
public class WasteProcessMaterialItem extends DocMatItemNode {

	public static final String NODENAME = IServiceModelConstants.WasteProcessMaterialItem;

	public static final String SENAME = IServiceModelConstants.WasteProcessOrder;

	@Column(name = "storeCheckStatus")
	protected int storeCheckStatus;

	@Column(name = "refStoreItemUUID")
	protected String refStoreItemUUID;

	public int getStoreCheckStatus() {
		return storeCheckStatus;
	}

	public void setStoreCheckStatus(int storeCheckStatus) {
		this.storeCheckStatus = storeCheckStatus;
	}

	public String getRefStoreItemUUID() {
		return refStoreItemUUID;
	}

	public void setRefStoreItemUUID(String refStoreItemUUID) {
		this.refStoreItemUUID = refStoreItemUUID;
	}

	public WasteProcessMaterialItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER;
	}

}

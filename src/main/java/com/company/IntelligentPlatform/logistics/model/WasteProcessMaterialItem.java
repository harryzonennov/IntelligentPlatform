package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import jakarta.persistence.*;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Migrated from: ThorsteinLogistics - WasteProcessMaterialItem (extends DocMatItemNode)
 * Table: WasteProcessMaterialItem (schema: logistics)
 */
@Entity
@Table(name = "WasteProcessMaterialItem", schema = "logistics")
public class WasteProcessMaterialItem extends DocMatItemNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;
	public static final String SENAME = IServiceModelConstants.WasteProcessMaterialItem;

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

}

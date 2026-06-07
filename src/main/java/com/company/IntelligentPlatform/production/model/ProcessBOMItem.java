package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Entity
@Table(name = "ProcessBOMItem", catalog = "production")
public class ProcessBOMItem extends ServiceEntityNode {

	public final static String NODENAME = IServiceModelConstants.ProcessBOMItem;

	public final static String SENAME = ProcessBOMOrder.SENAME;

	protected String refProssRouteProcessItemUUID;

	protected int layer;

	protected String refParentItemUUID;

	/**
	 * Point to material category
	 */
	protected int itemCategory;

	public ProcessBOMItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

	public String getRefProssRouteProcessItemUUID() {
		return refProssRouteProcessItemUUID;
	}

	public void setRefProssRouteProcessItemUUID(String refProssRouteProcessItemUUID) {
		this.refProssRouteProcessItemUUID = refProssRouteProcessItemUUID;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getRefParentItemUUID() {
		return refParentItemUUID;
	}

	public void setRefParentItemUUID(String refParentItemUUID) {
		this.refParentItemUUID = refParentItemUUID;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

}

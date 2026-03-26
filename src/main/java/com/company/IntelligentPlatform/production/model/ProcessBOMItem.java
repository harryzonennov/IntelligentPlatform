package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

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

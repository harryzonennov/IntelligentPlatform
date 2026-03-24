package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Model to record each sub item produced for target product
 * @author Zhang, Hang
 *
 */
public class ProdOrderTarSubItem extends DocMatItemNode {


	public final static String NODENAME = IServiceModelConstants.ProdOrderTarSubItem;

	public final static String SENAME = ProductionOrder.SENAME;

	protected int layer;

	protected String refParentItemUUID;

	protected String refBOMItemUUID;

	protected String refWocUUID;

	protected int processIndex;

	protected String refSerialId;

	public ProdOrderTarSubItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER;
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

	public String getRefBOMItemUUID() {
		return refBOMItemUUID;
	}

	public void setRefBOMItemUUID(String refBOMItemUUID) {
		this.refBOMItemUUID = refBOMItemUUID;
	}

	public String getRefWocUUID() {
		return refWocUUID;
	}

	public void setRefWocUUID(String refWocUUID) {
		this.refWocUUID = refWocUUID;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(String refSerialId) {
		this.refSerialId = refSerialId;
	}

}

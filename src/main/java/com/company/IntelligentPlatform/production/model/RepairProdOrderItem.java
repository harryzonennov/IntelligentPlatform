package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class RepairProdOrderItem extends ProductionOrderItem {


	public final static String NODENAME = IServiceModelConstants.RepairProdOrderItem;

	public final static String SENAME = RepairProdOrder.SENAME;

	public RepairProdOrderItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDERITEM;
	}


}

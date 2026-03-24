package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Model to
 * @author Zhang, Hang
 *
 */
public class RepairProdTargetMatItem extends ProdOrderTargetMatItem {


	public final static String NODENAME = IServiceModelConstants.RepairProdTargetMatItem;

	public final static String SENAME = RepairProdOrder.SENAME;

	public RepairProdTargetMatItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = STATUS_INIT;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER;
	}


}

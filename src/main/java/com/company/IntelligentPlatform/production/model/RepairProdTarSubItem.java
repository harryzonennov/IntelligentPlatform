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
public class RepairProdTarSubItem extends ProdOrderTarSubItem {


	public final static String NODENAME = IServiceModelConstants.RepairProdTarSubItem;

	public final static String SENAME = RepairProdOrder.SENAME;


	public RepairProdTarSubItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDER;
	}


}

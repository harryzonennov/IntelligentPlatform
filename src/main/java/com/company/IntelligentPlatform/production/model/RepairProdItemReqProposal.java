package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Pay attention:this model will not be connect to persistence
 * @author Zhang,Hang
 *
 */
public class RepairProdItemReqProposal extends ProdOrderItemReqProposal{


	public final static String NODENAME = IServiceModelConstants.RepairProdItemReqProposal;

	public final static String SENAME = RepairProdOrderItem.SENAME;

	public RepairProdItemReqProposal() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = ProductionPlanItem.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_REPAIRPRODORDERITEM;
	}

}

package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Pay attention:this model will not be connect to persistence
 * @author Zhang,Hang
 *
 */
public class ProdOrderItemReqProposal extends ProdItemReqProposalTemplate{

	public final static String NODENAME = IServiceModelConstants.ProdOrderItemReqProposal;

	public final static String SENAME = ProductionOrder.SENAME;

	public ProdOrderItemReqProposal() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = ProductionPlanItem.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTORDERITEM;
	}

}

package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 *
 * @author Zhang,Hang
 *
 */
public class ProdPlanItemReqProposal extends ProdItemReqProposalTemplate{

	public final static String NODENAME = IServiceModelConstants.ProdPlanItemReqProposal;

	public final static String SENAME = ProductionPlan.SENAME;

	public ProdPlanItemReqProposal() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = ProductionPlanItem.STATUS_INITIAL;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN;
	}

}

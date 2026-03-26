package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProdPlanSupplyWarehouse extends ReferenceNode{

	public final static String NODENAME = IServiceModelConstants.ProdPlanSupplyWarehouse;

	public final static String SENAME = ProductionPlan.SENAME;

	public ProdPlanSupplyWarehouse() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

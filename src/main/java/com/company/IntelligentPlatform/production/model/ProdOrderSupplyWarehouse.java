package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ReferenceNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class ProdOrderSupplyWarehouse extends ReferenceNode{

	public final static String NODENAME = IServiceModelConstants.ProdOrderSupplyWarehouse;

	public final static String SENAME = ProductionOrder.SENAME;

	public ProdOrderSupplyWarehouse() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

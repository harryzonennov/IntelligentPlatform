package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class RepairProdSupplyWarehouse extends ProdOrderSupplyWarehouse{

	public final static String NODENAME = IServiceModelConstants.RepairProdSupplyWarehouse;

	public final static String SENAME = RepairProdOrder.SENAME;

	public RepairProdSupplyWarehouse() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

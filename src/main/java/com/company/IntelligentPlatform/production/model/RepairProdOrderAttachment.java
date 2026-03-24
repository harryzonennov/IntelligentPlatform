package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class RepairProdOrderAttachment extends ProductionOrderAttachment {

	public static final String NODENAME = IServiceModelConstants.RepairProdOrderAttachment;

	public static final String SENAME = RepairProdOrder.SENAME;

	public RepairProdOrderAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}


}

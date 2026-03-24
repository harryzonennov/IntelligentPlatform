package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class RepairProdOrderParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.RepairProdOrderParty;

	public static final String SENAME = RepairProdOrder.SENAME;

	public static final String PARTY_NODEINST_SOLD_CUSTOMER = DocInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER;

	public static final String PARTY_NODEINST_SOLD_ORG = DocInvolveParty.PARTY_NODEINST_SOLD_ORG;

	public static final String PARTY_NODEINST_PROD_ORG = DocInvolveParty.PARTY_NODEINST_PROD_ORG;

	public static final String PARTY_NODEINST_SUPPORT_ORG = DocInvolveParty.PARTY_NODEINST_SUPPORT_ORG;

	public static final String PARTY_NODEINST_PUR_ORG = DocInvolveParty.PARTY_NODEINST_PUR_ORG;

	public RepairProdOrderParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

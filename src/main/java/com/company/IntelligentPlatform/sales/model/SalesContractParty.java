package com.company.IntelligentPlatform.sales.model;

import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class SalesContractParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.SalesContractParty;

	public static final String SENAME = SalesContract.SENAME;

	public static final int ROLE_SOLD_TO_PARTY = 1;

	public static final int ROLE_SOLD_FROM_PARTY = 2;

	public static final String PARTY_NODEINST_SOLD_ORG = DocInvolveParty.PARTY_NODEINST_SOLD_ORG;

	public static final String PARTY_NODEINST_SOLD_CUSTOMER = DocInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER;

	public SalesContractParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocItemParty;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class InventoryTransferItemParty extends DocItemParty {


	public final static String NODENAME = IServiceModelConstants.InventoryTransferItemParty;

	public final static String SENAME = InventoryTransferOrder.SENAME;

	public static final String PARTY_NODEINST_SOLD_CUSTOMER = DocInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER;

	public static final String PARTY_NODEINST_PUR_SUPPLIER = DocInvolveParty.PARTY_NODEINST_PUR_SUPPLIER;

	public static final String PARTY_NODEINST_SOLD_ORG = DocInvolveParty.PARTY_NODEINST_SOLD_ORG;

	public static final String PARTY_NODEINST_PROD_ORG = DocInvolveParty.PARTY_NODEINST_PROD_ORG;

	public static final String PARTY_NODEINST_SUPPORT_ORG = DocInvolveParty.PARTY_NODEINST_SUPPORT_ORG;

	public static final String PARTY_NODEINST_PUR_ORG = DocInvolveParty.PARTY_NODEINST_PUR_ORG;

	public InventoryTransferItemParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

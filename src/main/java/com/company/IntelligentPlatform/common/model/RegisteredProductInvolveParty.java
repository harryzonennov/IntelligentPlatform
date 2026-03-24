package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.InvolvePartyTemplate;

public class RegisteredProductInvolveParty extends InvolvePartyTemplate{

	public static final String NODENAME = IServiceModelConstants.RegisteredProductInvolveParty;

	public static final String SENAME = IServiceModelConstants.RegisteredProduct;

	public static final String NODEINST_SALESBY = "salesByParty";  // organization type

	public static final String NODEINST_SALESTO = "salesToParty"; // customer type

	public static final String NODEINST_PURCHASEBY = "purchaseByParty";  // organization type

	public static final String NODEINST_PURCHASEFROM = "purchaseFromParty"; // supplier type

	public static final String NODEINST_PRODUCTBY = "productBy"; // organization type

	public static final String NODEINST_SUPPORTBY = "supportBy"; // organization type

	public static final int ROLE_ID_SALESBY = InvolvePartyTemplate.PARTY_ROLE_SALESORG;

	public static final int ROLE_ID_SALESTO = InvolvePartyTemplate.PARTY_ROLE_CUSTOMER;

	public static final int ROLE_ID_PURCHASEBY = InvolvePartyTemplate.PARTY_ROLE_PURORG;

	public static final int ROLE_ID_PURCHASEFROM = InvolvePartyTemplate.PARTY_ROLE_PURORG;

	public static final int ROLE_ID_PRODUCTBY = InvolvePartyTemplate.PARTY_ROLE_PRODORG;

	public static final int ROLE_ID_SUPPORTBY = InvolvePartyTemplate.PARTY_ROLE_SUPPORTORG;

	public RegisteredProductInvolveParty() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}
}

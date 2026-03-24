package com.company.IntelligentPlatform.logistics.model;

import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

public class InquiryParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.InquiryParty;

	public static final String SENAME = Inquiry.SENAME;

	public static final int ROLE_PARTYA = 1;

	public static final int ROLE_PARTYB = 2;

	public static final String PARTY_NODEINST_PUR_ORG = DocInvolveParty.PARTY_NODEINST_PUR_ORG;

	public static final String PARTY_NODEINST_PUR_SUPPLIER = DocInvolveParty.PARTY_NODEINST_PUR_SUPPLIER;

	public InquiryParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

package com.company.IntelligentPlatform.logistics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocInvolveParty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "PurchaseRequestParty", catalog = "logistics")
public class PurchaseRequestParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.PurchaseRequestParty;

	public static final String SENAME = PurchaseRequest.SENAME;

	public static final int ROLE_PARTYA = 1;

	public static final int ROLE_PARTYB = 2;

	public static final String PARTY_NODEINST_PUR_SUPPLIER = DocInvolveParty.PARTY_NODEINST_PUR_SUPPLIER;

	public static final String PARTY_NODEINST_PUR_ORG = DocInvolveParty.PARTY_NODEINST_PUR_ORG;

	public PurchaseRequestParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

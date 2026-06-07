package com.company.IntelligentPlatform.salesDistribution.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocInvolveParty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "SalesReturnOrderParty", catalog = "sales")
public class SalesReturnOrderParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.SalesReturnOrderParty;

	public static final String SENAME = SalesReturnOrder.SENAME;

	public static final int ROLE_SOLD_TO_PARTY = 1;

	public static final int ROLE_SOLD_FROM_PARTY = 2;

	public static final String PARTY_NODEINST_SOLD_ORG = DocInvolveParty.PARTY_NODEINST_SOLD_ORG;

	public static final String PARTY_NODEINST_SOLD_CUSTOMER = DocInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER;

	public SalesReturnOrderParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

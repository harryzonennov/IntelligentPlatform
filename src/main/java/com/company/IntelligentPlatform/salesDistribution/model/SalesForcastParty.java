package com.company.IntelligentPlatform.salesDistribution.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocInvolveParty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "SalesForcastParty", catalog = "sales")
public class SalesForcastParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.SalesForcastParty;

	public static final String SENAME = SalesForcast.SENAME;

	public static final int ROLE_SOLD_TO_PARTY = 1;

	public static final int ROLE_SOLD_FROM_PARTY = 2;

	public static final String PARTY_NODEINST_SOLD_ORG = DocInvolveParty.PARTY_NODEINST_SOLD_ORG;

	public static final String PARTY_NODEINST_SOLD_CUSTOMER = DocInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER;

	public SalesForcastParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

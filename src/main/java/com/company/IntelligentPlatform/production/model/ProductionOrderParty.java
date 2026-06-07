package com.company.IntelligentPlatform.production.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.DocInvolveParty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.IServiceModelConstants;

@Entity
@Table(name = "ProductionOrderParty", catalog = "production")
public class ProductionOrderParty extends DocInvolveParty {

	public static final String NODENAME = IServiceModelConstants.ProductionOrderParty;

	public static final String SENAME = ProductionOrder.SENAME;

	public static final String PARTY_NODEINST_SOLD_CUSTOMER = DocInvolveParty.PARTY_NODEINST_SOLD_CUSTOMER;

	public static final String PARTY_NODEINST_SOLD_ORG = DocInvolveParty.PARTY_NODEINST_SOLD_ORG;

	public static final String PARTY_NODEINST_PROD_ORG = DocInvolveParty.PARTY_NODEINST_PROD_ORG;

	public static final String PARTY_NODEINST_SUPPORT_ORG = DocInvolveParty.PARTY_NODEINST_SUPPORT_ORG;

	public static final String PARTY_NODEINST_PUR_ORG = DocInvolveParty.PARTY_NODEINST_PUR_ORG;

	public ProductionOrderParty() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

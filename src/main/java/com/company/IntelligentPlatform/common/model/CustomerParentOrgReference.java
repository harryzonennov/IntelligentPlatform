package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;

public class CustomerParentOrgReference extends ReferenceNode {


	public final static String NODENAME = "CustomerParentOrgReference";

	public final static String SENAME = CorporateCustomer.SENAME;
	
	public CustomerParentOrgReference() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;		
	}

}

package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CustomerParentOrgReference", catalog = "platform")
public class CustomerParentOrgReference extends ReferenceNode {

	public final static String NODENAME = "CustomerParentOrgReference";

	public final static String SENAME = CorporateCustomer.SENAME;
	
	public CustomerParentOrgReference() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;		
	}

}

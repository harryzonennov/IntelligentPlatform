package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.platform.model.*;

@Entity
@Table(name = "CorporateCustomerAttachment", catalog = "platform")
public class CorporateCustomerAttachment extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.CorporateCustomerAttachment;

	public static final String SENAME = IServiceModelConstants.CorporateCustomer;
	
	public CorporateCustomerAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}

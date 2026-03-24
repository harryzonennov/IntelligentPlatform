package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class CorporateCustomerAttachment extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.CorporateCustomerAttachment;

	public static final String SENAME = IServiceModelConstants.CorporateCustomer;
	
	public CorporateCustomerAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}

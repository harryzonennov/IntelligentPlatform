package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class IndividualCustomerAttachment extends DocAttachmentNode {

	public static final String NODENAME = IServiceModelConstants.IndividualCustomerAttachment;

	public static final String SENAME = IServiceModelConstants.IndividualCustomer;

	public IndividualCustomerAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}
}

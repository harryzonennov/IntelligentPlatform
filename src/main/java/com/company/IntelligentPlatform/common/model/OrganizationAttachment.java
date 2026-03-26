package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class OrganizationAttachment extends DocAttachmentNode {
	
	public static final String NODENAME = IServiceModelConstants.OrganizationAttachment;

	public static final String SENAME = IServiceModelConstants.Organization;
	
	public OrganizationAttachment() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}

}

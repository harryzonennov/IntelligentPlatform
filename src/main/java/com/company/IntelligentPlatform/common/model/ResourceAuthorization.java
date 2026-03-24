package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ResourceAuthorization extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.ResourceAuthorization;

	public static final String SENAME = SystemResource.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public ResourceAuthorization() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

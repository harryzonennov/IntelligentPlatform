package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

@Deprecated
public class RoleAuthorizationActionCode extends ReferenceNode {


	public final static String NODENAME = IServiceModelConstants.RoleAuthorizationActionCode;

	public final static String SENAME = Role.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public RoleAuthorizationActionCode() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

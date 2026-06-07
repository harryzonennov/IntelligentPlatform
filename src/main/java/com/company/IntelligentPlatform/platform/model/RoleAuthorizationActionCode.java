package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Deprecated
@Entity
@Table(name = "RoleAuthorizationActionCode", catalog = "platform")
public class RoleAuthorizationActionCode extends ReferenceNode {

	public final static String NODENAME = IServiceModelConstants.RoleAuthorizationActionCode;

	public final static String SENAME = Role.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public RoleAuthorizationActionCode() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

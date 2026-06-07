package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ResourceAuthorization", catalog = "platform")
public class ResourceAuthorization extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.ResourceAuthorization;

	public static final String SENAME = SystemResource.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public ResourceAuthorization() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
	}

}

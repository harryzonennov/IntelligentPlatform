package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class RoleMessageCategory extends ReferenceNode {

	public static final String NODENAME = IServiceModelConstants.RoleMessageCategory;

	public static final String SENAME = Role.SENAME;

	protected int messageCategory;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public RoleMessageCategory() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.nodeLevel = ServiceEntityNode.NODELEVEL_NODE;
	}

	public int getMessageCategory() {
		return messageCategory;
	}

	public void setMessageCategory(int messageCategory) {
		this.messageCategory = messageCategory;
	}

}

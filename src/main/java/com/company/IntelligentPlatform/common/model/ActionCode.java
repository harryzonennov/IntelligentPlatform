package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ActionCode extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ActionCode;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	public ActionCode() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

}

package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class Province extends Location {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.Province;
	
	public static int nodeCategory = NODE_CATEGORY_SYS;

	public Province() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		super.locationLevel = LOC_LEVEL_PROVINCE;
	}

}

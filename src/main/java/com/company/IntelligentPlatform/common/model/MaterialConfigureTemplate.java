package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class MaterialConfigureTemplate extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.MaterialConfigureTemplate;

	public MaterialConfigureTemplate() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
	}

}

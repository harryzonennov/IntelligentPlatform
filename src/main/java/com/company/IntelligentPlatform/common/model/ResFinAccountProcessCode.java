package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ResFinAccountProcessCode extends ServiceEntityNode{
	
	protected int processCode;
	
	public static final String NODENAME = IServiceModelConstants.ResFinAccountProcessCode;

	public static final String SENAME = SystemResource.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public ResFinAccountProcessCode() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;		
	}

	public int getProcessCode() {
		return processCode;
	}

	public void setProcessCode(int processCode) {
		this.processCode = processCode;
	}

}

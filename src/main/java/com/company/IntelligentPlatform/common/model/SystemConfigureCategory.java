package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemConfigureCategory extends ServiceEntityNode {
	
	public static final int SCENARIO_MODE_OFF = 1;
	
	public static final int SCENARIO_MODE_SLIM = 2;
	
	public static final int SCENARIO_MODE_STANDARD = 3;
	
	public static final int SCENARIO_MODE_ENTERPRISE = 4;
	
	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.SystemConfigureCategory;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public SystemConfigureCategory() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;		
	}
	
    protected int scenarioMode;
    
    protected int standardSystemCategory;

	public int getScenarioMode() {
		return scenarioMode;
	}

	public void setScenarioMode(int scenarioMode) {
		this.scenarioMode = scenarioMode;
	}

	public int getStandardSystemCategory() {
		return standardSystemCategory;
	}

	public void setStandardSystemCategory(int standardSystemCategory) {
		this.standardSystemCategory = standardSystemCategory;
	}

}

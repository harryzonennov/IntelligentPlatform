package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemConfigureResource extends ServiceEntityNode {
	
	public static final String NODENAME = IServiceModelConstants.SystemConfigureResource;

	public static final String SENAME = SystemConfigureCategory.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public SystemConfigureResource() {
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

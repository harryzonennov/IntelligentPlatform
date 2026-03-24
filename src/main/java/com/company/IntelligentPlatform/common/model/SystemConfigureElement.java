package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SystemConfigureElement extends ReferenceNode {
	
	public static final String FIELD_ELETYPE = "elementType";
	
	public static final int ELETYPE_NAVI_ELEMENT = 1;
	
	public static final int ELETYPE_PAGE = 2;
	
	public static final int ELETYPE_BUTTON = 3;
	
	public static final int ELETYPE_CONTR_METHOD = 4;
	
	public static final int ELETYPE_MIXED_BLOCK = 5;
	
	public static final String NODENAME = IServiceModelConstants.SystemConfigureElement;

	public static final String SENAME = SystemConfigureCategory.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;
	
	public SystemConfigureElement() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}
	
	protected int scenarioMode;
	
	protected int elementType;
	
	protected int standardSystemCategory;
	
	protected int subScenarioMode;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String scenarioModeSwitchProxy;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String subScenarioModeSwitchProxy;

	public int getScenarioMode() {
		return scenarioMode;
	}

	public void setScenarioMode(int scenarioMode) {
		this.scenarioMode = scenarioMode;
	}

	public int getElementType() {
		return elementType;
	}

	public void setElementType(int elementType) {
		this.elementType = elementType;
	}

	public int getStandardSystemCategory() {
		return standardSystemCategory;
	}

	public void setStandardSystemCategory(int standardSystemCategory) {
		this.standardSystemCategory = standardSystemCategory;
	}

	public int getSubScenarioMode() {
		return subScenarioMode;
	}

	public void setSubScenarioMode(int subScenarioMode) {
		this.subScenarioMode = subScenarioMode;
	}

	public String getScenarioModeSwitchProxy() {
		return scenarioModeSwitchProxy;
	}

	public void setScenarioModeSwitchProxy(String scenarioModeSwitchProxy) {
		this.scenarioModeSwitchProxy = scenarioModeSwitchProxy;
	}

	public String getSubScenarioModeSwitchProxy() {
		return subScenarioModeSwitchProxy;
	}

	public void setSubScenarioModeSwitchProxy(String subScenarioModeSwitchProxy) {
		this.subScenarioModeSwitchProxy = subScenarioModeSwitchProxy;
	}

}

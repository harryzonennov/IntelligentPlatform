package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class SystemConfigureCategoryUIModel extends SEUIComModel {

	
	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureCategory_scenarioMode", valueFieldName = "scenarioModeValue")
	protected int scenarioMode;

	protected String scenarioModeValue;

	protected int standardSystemCategory;
	
	protected String standardSystemCategoryValue;

	public int getScenarioMode() {
		return scenarioMode;
	}

	public void setScenarioMode(int scenarioMode) {
		this.scenarioMode = scenarioMode;
	}

	public String getScenarioModeValue() {
		return scenarioModeValue;
	}

	public void setScenarioModeValue(String scenarioModeValue) {
		this.scenarioModeValue = scenarioModeValue;
	}

	public int getStandardSystemCategory() {
		return standardSystemCategory;
	}

	public void setStandardSystemCategory(int standardSystemCategory) {
		this.standardSystemCategory = standardSystemCategory;
	}

	public String getStandardSystemCategoryValue() {
		return standardSystemCategoryValue;
	}

	public void setStandardSystemCategoryValue(String standardSystemCategoryValue) {
		this.standardSystemCategoryValue = standardSystemCategoryValue;
	}

}

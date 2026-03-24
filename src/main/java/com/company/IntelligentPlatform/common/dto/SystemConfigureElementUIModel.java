package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.SystemConfigureCategory;
import com.company.IntelligentPlatform.common.model.SystemConfigureElement;

public class SystemConfigureElementUIModel extends SEUIComModel {

	public static final String NODEID_SUBCONFIG_ELE = "subConfigureElement";

	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureCategory_scenarioMode", valueFieldName = "scenarioModeValue")
	protected int scenarioMode;
	
	protected String scenarioModeValue;
	
	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureCategory_scenarioMode", valueFieldName = "subScenarioModeValue")
	protected int subScenarioMode;
	
	protected String subScenarioModeValue;
	
	protected String scenarioModeSwitchProxy;
	
	protected String subScenarioModeSwitchProxy;
	
	@ISEDropDownResourceMapping(resouceMapping = "SystemConfigureElement_elementType", valueFieldName = "elementTypeValue")
	protected int elementType;
	
	protected String elementTypeValue;
	
	protected int standardSystemCategory;
	
	protected String standardSystemCategoryValue;	
	
	protected String refUUID;

	protected String parentNodeId;

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

	public int getSubScenarioMode() {
		return subScenarioMode;
	}

	public void setSubScenarioMode(int subScenarioMode) {
		this.subScenarioMode = subScenarioMode;
	}

	public String getSubScenarioModeValue() {
		return subScenarioModeValue;
	}

	public void setSubScenarioModeValue(String subScenarioModeValue) {
		this.subScenarioModeValue = subScenarioModeValue;
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

	public int getElementType() {
		return elementType;
	}

	public void setElementType(int elementType) {
		this.elementType = elementType;
	}

	public String getElementTypeValue() {
		return elementTypeValue;
	}

	public void setElementTypeValue(String elementTypeValue) {
		this.elementTypeValue = elementTypeValue;
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

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	

}

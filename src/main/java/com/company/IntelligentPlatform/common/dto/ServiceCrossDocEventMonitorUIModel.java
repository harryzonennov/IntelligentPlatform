package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceCrossDocEventMonitorUIModel extends SEUIComModel {

	protected String targetDocType;

	protected String homeDocType;

	protected int crossDocRelationType;

	protected String targetDocTypeValue;

	protected String homeDocTypeValue;

	protected String crossDocRelationTypeValue;

	protected int targetActionCode;

	protected int triggerHomeActionCode;

	@ISEDropDownResourceMapping(resouceMapping = "ServiceCrossDoc_ActScenario", valueFieldName = "statusValue")
	protected int triggerDocActionScenario;

	@ISEDropDownResourceMapping(resouceMapping = "ServiceCrossDoc_TriggerParentMode", valueFieldName = "statusValue")
	protected int triggerParentMode;

	protected String targetActionCodeValue;

	protected String triggerHomeActionCodeValue;

	protected String triggerDocActionScenarioValue;

	protected String triggerParentModeValue;

	public int getTargetActionCode() {
		return targetActionCode;
	}

	public void setTargetActionCode(int targetActionCode) {
		this.targetActionCode = targetActionCode;
	}

	public int getTriggerHomeActionCode() {
		return triggerHomeActionCode;
	}

	public void setTriggerHomeActionCode(int triggerHomeActionCode) {
		this.triggerHomeActionCode = triggerHomeActionCode;
	}

	public int getTriggerDocActionScenario() {
		return triggerDocActionScenario;
	}

	public void setTriggerDocActionScenario(int triggerDocActionScenario) {
		this.triggerDocActionScenario = triggerDocActionScenario;
	}

	public int getTriggerParentMode() {
		return triggerParentMode;
	}

	public void setTriggerParentMode(int triggerParentMode) {
		this.triggerParentMode = triggerParentMode;
	}

	public String getTargetActionCodeValue() {
		return targetActionCodeValue;
	}

	public void setTargetActionCodeValue(String targetActionCodeValue) {
		this.targetActionCodeValue = targetActionCodeValue;
	}

	public String getTriggerHomeActionCodeValue() {
		return triggerHomeActionCodeValue;
	}

	public void setTriggerHomeActionCodeValue(String triggerHomeActionCodeValue) {
		this.triggerHomeActionCodeValue = triggerHomeActionCodeValue;
	}

	public String getTriggerDocActionScenarioValue() {
		return triggerDocActionScenarioValue;
	}

	public void setTriggerDocActionScenarioValue(String triggerDocActionScenarioValue) {
		this.triggerDocActionScenarioValue = triggerDocActionScenarioValue;
	}

	public String getTriggerParentModeValue() {
		return triggerParentModeValue;
	}

	public void setTriggerParentModeValue(String triggerParentModeValue) {
		this.triggerParentModeValue = triggerParentModeValue;
	}

	public String getTargetDocType() {
		return targetDocType;
	}

	public void setTargetDocType(String targetDocType) {
		this.targetDocType = targetDocType;
	}

	public String getHomeDocType() {
		return homeDocType;
	}

	public void setHomeDocType(String homeDocType) {
		this.homeDocType = homeDocType;
	}

	public String getHomeDocTypeValue() {
		return homeDocTypeValue;
	}

	public void setHomeDocTypeValue(String homeDocTypeValue) {
		this.homeDocTypeValue = homeDocTypeValue;
	}

	public int getCrossDocRelationType() {
		return crossDocRelationType;
	}

	public void setCrossDocRelationType(int crossDocRelationType) {
		this.crossDocRelationType = crossDocRelationType;
	}

	public String getTargetDocTypeValue() {
		return targetDocTypeValue;
	}

	public void setTargetDocTypeValue(String targetDocTypeValue) {
		this.targetDocTypeValue = targetDocTypeValue;
	}

	public String getCrossDocRelationTypeValue() {
		return crossDocRelationTypeValue;
	}

	public void setCrossDocRelationTypeValue(String crossDocRelationTypeValue) {
		this.crossDocRelationTypeValue = crossDocRelationTypeValue;
	}
}

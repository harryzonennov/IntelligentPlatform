package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.model.ServiceAccountDuplicateCheckResource;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class ServiceAccountDuplicateCheckResourceUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "refAccountType", seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnList = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResource_refAccountType", valueFieldName = "refAccountTypeValue")
	protected int refAccountType;

	@ISEUIModelMapping(seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnEditor = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	protected String refAccountTypeValue;

	@ISEUIModelMapping(fieldName = "executedOrder", seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnList = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	protected int executedOrder;

	@ISEUIModelMapping(fieldName = "switchFlag", seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnList = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResource_switchFlag", valueFieldName = "switchFlagValue")
	protected int switchFlag;

	@ISEUIModelMapping(seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnEditor = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	protected String switchFlagValue;

	@ISEUIModelMapping(fieldName = "implementClassName", seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	protected String implementClassName;

	@ISEUIModelMapping(fieldName = "implementType", seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnList = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResource_implementType", valueFieldName = "implementTypeValue")
	protected int implementType;

	@ISEUIModelMapping(seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnEditor = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	protected String implementTypeValue;

	@ISEUIModelMapping(fieldName = "logicRelationship", seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnList = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ServiceAccountDuplicateCheckResource_logicRelationship", valueFieldName = "logicRelationshipValue")
	protected int logicRelationship;

	@ISEUIModelMapping(seName = ServiceAccountDuplicateCheckResource.SENAME, nodeName = ServiceAccountDuplicateCheckResource.NODENAME, nodeInstID = ServiceAccountDuplicateCheckResource.SENAME, showOnEditor = false, secId = ServiceAccountDuplicateCheckResource.SENAME, tabId = TABID_BASIC)
	protected String logicRelationshipValue;

	public int getRefAccountType() {
		return refAccountType;
	}

	public void setRefAccountType(int refAccountType) {
		this.refAccountType = refAccountType;
	}

	public String getRefAccountTypeValue() {
		return refAccountTypeValue;
	}

	public void setRefAccountTypeValue(String refAccountTypeValue) {
		this.refAccountTypeValue = refAccountTypeValue;
	}

	public int getExecutedOrder() {
		return executedOrder;
	}

	public void setExecutedOrder(int executedOrder) {
		this.executedOrder = executedOrder;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getSwitchFlagValue() {
		return switchFlagValue;
	}

	public void setSwitchFlagValue(String switchFlagValue) {
		this.switchFlagValue = switchFlagValue;
	}

	public String getImplementClassName() {
		return implementClassName;
	}

	public void setImplementClassName(String implementClassName) {
		this.implementClassName = implementClassName;
	}

	public int getImplementType() {
		return implementType;
	}

	public void setImplementType(int implementType) {
		this.implementType = implementType;
	}

	public String getImplementTypeValue() {
		return implementTypeValue;
	}

	public void setImplementTypeValue(String implementTypeValue) {
		this.implementTypeValue = implementTypeValue;
	}

	public int getLogicRelationship() {
		return logicRelationship;
	}

	public void setLogicRelationship(int logicRelationship) {
		this.logicRelationship = logicRelationship;
	}

	public String getLogicRelationshipValue() {
		return logicRelationshipValue;
	}

	public void setLogicRelationshipValue(String logicRelationshipValue) {
		this.logicRelationshipValue = logicRelationshipValue;
	}

}

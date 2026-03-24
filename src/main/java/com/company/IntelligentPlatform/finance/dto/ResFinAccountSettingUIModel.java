package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;

public class ResFinAccountSettingUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "coreSettleId", seName = IServiceModelConstants.FinAccountTitle, nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, 
			secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String coreSettleId;
	
	@ISEUIModelMapping(fieldName = "settleUIModelName", seName = IServiceModelConstants.FinAccountTitle, 
			nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String settleUIModelName;
	
	@ISEUIModelMapping(fieldName = "allAmountFieldName", seName = IServiceModelConstants.FinAccountTitle, 
			nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String allAmountFieldName;
	
	@ISEUIModelMapping(fieldName = "toSettleFieldName", seName = IServiceModelConstants.FinAccountTitle,
			nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String toSettleFieldName;
	
	@ISEUIModelMapping(fieldName = "settledFieldName", seName = IServiceModelConstants.FinAccountTitle, 
			nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String settledFieldName;

	@ISEUIModelMapping(fieldName = "refFinAccObjectKey", seName = ResFinAccountSetting.SENAME, 
			nodeName = ResFinAccountSetting.NODENAME, nodeInstID = ResFinAccountSetting.NODENAME, showOnList = false, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC)
	protected int refFinAccObjectKey;
	
	@ISEUIModelMapping(fieldName = "refFinAccObjectProxyClass", seName = ResFinAccountSetting.SENAME, 
			nodeName = ResFinAccountSetting.NODENAME, nodeInstID = ResFinAccountSetting.NODENAME, showOnList = false, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC)
	protected String refFinAccObjectProxyClass;
	
	protected String refFinAccObjectValue;
	
	protected String refFinAccObjectKeyLabel;

	@ISEUIModelMapping(fieldName = "switchFlag", seName = ResFinAccountSetting.SENAME, nodeName = ResFinAccountSetting.NODENAME, nodeInstID = ResFinAccountSetting.NODENAME, showOnList = false, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "ResFinAccountSetting_switchFlag", valueFieldName = "switchFlagValue")
	protected int switchFlag;

	@ISEUIModelMapping(seName = ResFinAccountSetting.SENAME, nodeName = ResFinAccountSetting.NODENAME, nodeInstID = ResFinAccountSetting.NODENAME, showOnEditor = false, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC)
	protected String switchFlagValue;
	
	@ISEUIModelMapping(fieldName = "refUUID", seName = ResFinAccountSetting.SENAME, nodeName = ResFinAccountSetting.NODENAME, nodeInstID = ResFinAccountSetting.SENAME, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC)
	protected String refUUID;
	
	@ISEUIModelMapping(fieldName = "id", seName = IServiceModelConstants.FinAccountTitle, nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String finAccountTitleId;
	
	@ISEUIModelMapping(fieldName = "name", seName = IServiceModelConstants.FinAccountTitle, nodeName = ServiceEntityNode.NODENAME_ROOT, nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC)
	protected String finAccountTitleName;
	
	protected String parentNodeId;
	
	protected String parentNodeName;
	public int getRefFinAccObjectKey() {
		return refFinAccObjectKey;
	}

	public void setRefFinAccObjectKey(int refFinAccObjectKey) {
		this.refFinAccObjectKey = refFinAccObjectKey;
	}

	public String getRefFinAccObjectProxyClass() {
		return refFinAccObjectProxyClass;
	}

	public void setRefFinAccObjectProxyClass(String refFinAccObjectProxyClass) {
		this.refFinAccObjectProxyClass = refFinAccObjectProxyClass;
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

	public String getRefFinAccObjectKeyLabel() {
		return refFinAccObjectKeyLabel;
	}

	public void setRefFinAccObjectKeyLabel(String refFinAccObjectKeyLabel) {
		this.refFinAccObjectKeyLabel = refFinAccObjectKeyLabel;
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
	}

	public String getFinAccountTitleId() {
		return finAccountTitleId;
	}

	public void setFinAccountTitleId(String finAccountTitleId) {
		this.finAccountTitleId = finAccountTitleId;
	}

	public String getFinAccountTitleName() {
		return finAccountTitleName;
	}

	public void setFinAccountTitleName(String finAccountTitleName) {
		this.finAccountTitleName = finAccountTitleName;
	}

	public String getRefFinAccObjectValue() {
		return refFinAccObjectValue;
	}

	public void setRefFinAccObjectValue(String refFinAccObjectValue) {
		this.refFinAccObjectValue = refFinAccObjectValue;
	}

	public String getCoreSettleId() {
		return coreSettleId;
	}

	public void setCoreSettleId(String coreSettleId) {
		this.coreSettleId = coreSettleId;
	}

	public String getSettleUIModelName() {
		return settleUIModelName;
	}

	public void setSettleUIModelName(String settleUIModelName) {
		this.settleUIModelName = settleUIModelName;
	}

	public String getAllAmountFieldName() {
		return allAmountFieldName;
	}

	public void setAllAmountFieldName(String allAmountFieldName) {
		this.allAmountFieldName = allAmountFieldName;
	}

	public String getToSettleFieldName() {
		return toSettleFieldName;
	}

	public void setToSettleFieldName(String toSettleFieldName) {
		this.toSettleFieldName = toSettleFieldName;
	}

	public String getSettledFieldName() {
		return settledFieldName;
	}

	public void setSettledFieldName(String settledFieldName) {
		this.settledFieldName = settledFieldName;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getParentNodeName() {
		return parentNodeName;
	}

	public void setParentNodeName(String parentNodeName) {
		this.parentNodeName = parentNodeName;
	}

}

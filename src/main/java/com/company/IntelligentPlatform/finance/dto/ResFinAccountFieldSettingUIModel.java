package com.company.IntelligentPlatform.finance.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ResFinAccountFieldSetting;
import com.company.IntelligentPlatform.common.model.ResFinAccountSetting;

public class ResFinAccountFieldSettingUIModel extends SEUIComModel {

	/**
	 * Area for Tab:[basic]
	 **/

	/**
	 * Section:[resFinAccountFieldSetting]
	 **/
	@ISEUIModelMapping(fieldName = "uuid", seName = ResFinAccountFieldSetting.SENAME, nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstID = ResFinAccountFieldSetting.NODENAME, hiddenFlag = true, searchFlag = false, secId = ResFinAccountFieldSetting.NODENAME, tabId = TABID_BASIC)
	protected String uuid;
	
	@ISEUIModelMapping(fieldName = "parentNodeUUID", seName = ResFinAccountFieldSetting.SENAME, nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstID = ResFinAccountFieldSetting.NODENAME, hiddenFlag = true, searchFlag = false, secId = ResFinAccountFieldSetting.NODENAME, tabId = TABID_BASIC)
	protected String parentNodeUUID;

	@ISEUIModelMapping(fieldName = "fieldName", seName = ResFinAccountFieldSetting.SENAME, nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstID = ResFinAccountFieldSetting.NODENAME, secId = ResFinAccountFieldSetting.NODENAME, tabId = TABID_BASIC)
	protected String fieldName;

	@ISEUIModelMapping(fieldName = "weightFactor", seName = ResFinAccountFieldSetting.SENAME, nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstID = ResFinAccountFieldSetting.NODENAME,  secId = ResFinAccountFieldSetting.NODENAME, tabId = TABID_BASIC)
	protected int weightFactor;
	
	@ISEUIModelMapping(fieldName = "finAccProxyClassName", seName = ResFinAccountFieldSetting.SENAME, nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstID = ResFinAccountFieldSetting.NODENAME, secId = ResFinAccountFieldSetting.NODENAME, tabId = TABID_BASIC)
	protected String finAccProxyClassName;
	
	protected String finAccProSimClassName;
	
	@ISEUIModelMapping(fieldName = "finAccProxyMethodName", seName = ResFinAccountFieldSetting.SENAME, nodeName = ResFinAccountFieldSetting.NODENAME, nodeInstID = ResFinAccountFieldSetting.NODENAME, secId = ResFinAccountFieldSetting.NODENAME, tabId = TABID_BASIC)
	protected String finAccProxyMethodName;
	
	@ISEUIModelMapping(fieldName = "id", seName = IServiceModelConstants.FinAccountTitle, nodeName = ServiceEntityNode.NODENAME_ROOT, 
			nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC, readOnlyFlag = true)
	protected String finAccountTitleId;
	
	@ISEUIModelMapping(fieldName = "name", seName =IServiceModelConstants.FinAccountTitle, nodeName = ServiceEntityNode.NODENAME_ROOT,
			nodeInstID = IServiceModelConstants.FinAccountTitle, secId = IServiceModelConstants.FinAccountTitle, tabId = TABID_BASIC, readOnlyFlag = true)
	protected String finAccountTitleName;
	
	@ISEUIModelMapping(fieldName = "id", seName = ResFinAccountSetting.SENAME, nodeName = ResFinAccountSetting.NODENAME, 
			nodeInstID = ResFinAccountSetting.NODENAME, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC, readOnlyFlag = true)
	protected String baseId;

	@ISEUIModelMapping(fieldName = "name", seName = ResFinAccountSetting.SENAME, nodeName = ResFinAccountSetting.NODENAME, 
			nodeInstID = ResFinAccountSetting.NODENAME, secId = ResFinAccountSetting.NODENAME, tabId = TABID_BASIC, readOnlyFlag = true)
	protected String baseName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentNodeUUID() {
		return parentNodeUUID;
	}

	public void setParentNodeUUID(String parentNodeUUID) {
		this.parentNodeUUID = parentNodeUUID;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getWeightFactor() {
		return weightFactor;
	}

	public void setWeightFactor(int weightFactor) {
		this.weightFactor = weightFactor;
	}

	public String getFinAccProxyClassName() {
		return finAccProxyClassName;
	}

	public void setFinAccProxyClassName(String finAccProxyClassName) {
		this.finAccProxyClassName = finAccProxyClassName;
	}

	public String getFinAccProxyMethodName() {
		return finAccProxyMethodName;
	}

	public void setFinAccProxyMethodName(String finAccProxyMethodName) {
		this.finAccProxyMethodName = finAccProxyMethodName;
	}

	public String getFinAccountTitleName() {
		return finAccountTitleName;
	}

	public void setFinAccountTitleName(String finAccountTitleName) {
		this.finAccountTitleName = finAccountTitleName;
	}

	public String getFinAccountTitleId() {
		return finAccountTitleId;
	}

	public void setFinAccountTitleId(String finAccountTitleId) {
		this.finAccountTitleId = finAccountTitleId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getFinAccProSimClassName() {
		return finAccProSimClassName;
	}

	public void setFinAccProSimClassName(String finAccProSimClassName) {
		this.finAccProSimClassName = finAccProSimClassName;
	}

}

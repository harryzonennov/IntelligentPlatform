package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.NavigationSystemSetting;

public class NavigationItemSettingSearchModel extends SEUIComModel {

	@BSearchFieldConfig(fieldName = "name", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
	protected String systemName;

	@BSearchFieldConfig(fieldName = "status", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
	protected int status;

	@BSearchFieldConfig(fieldName = "id", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
	protected String systemId;

	@BSearchFieldConfig(fieldName = "id", nodeName = NavigationGroupSetting.NODENAME, seName = NavigationGroupSetting.SENAME, nodeInstID = NavigationGroupSetting.NODENAME)
	protected String groupId;

	@BSearchFieldConfig(fieldName = "name", nodeName = NavigationGroupSetting.NODENAME, seName = NavigationGroupSetting.SENAME, nodeInstID = NavigationGroupSetting.NODENAME)
	protected String groupName;

	@BSearchFieldConfig(fieldName = "applicationLevel", nodeName = NavigationSystemSetting.NODENAME, seName = NavigationSystemSetting.SENAME, nodeInstID = NavigationSystemSetting.SENAME)
	protected int applicationLevel;

	@BSearchFieldConfig(fieldName = "targetUrl", nodeName = NavigationItemSetting.NODENAME, seName = NavigationItemSetting.SENAME, nodeInstID = NavigationItemSetting.NODENAME)
	protected String targetUrl;

	@BSearchFieldConfig(fieldName = "id", nodeName = NavigationItemSetting.NODENAME, seName = NavigationItemSetting.SENAME, nodeInstID = NavigationItemSetting.NODENAME)
	protected String id;

	@BSearchFieldConfig(fieldName = "name", nodeName = NavigationItemSetting.NODENAME, seName = NavigationItemSetting.SENAME, nodeInstID = NavigationItemSetting.NODENAME)
	protected String name;

	@BSearchFieldConfig(fieldName = "displayFlag", nodeName = NavigationItemSetting.NODENAME, seName = NavigationItemSetting.SENAME, nodeInstID = NavigationItemSetting.NODENAME)
	protected int displayFlag;

	@BSearchFieldConfig(fieldName = "keywords", nodeName = NavigationItemSetting.NODENAME, seName = NavigationItemSetting.SENAME, nodeInstID = NavigationItemSetting.NODENAME)
	protected String keywords;

	@ISEUIModelMapping(fieldName = "layer", seName = NavigationItemSetting.SENAME, nodeName = NavigationItemSetting.NODENAME, nodeInstID = NavigationItemSetting.NODENAME)
	protected int layer;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public int getApplicationLevel() {
		return applicationLevel;
	}

	public void setApplicationLevel(int applicationLevel) {
		this.applicationLevel = applicationLevel;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(int displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

}

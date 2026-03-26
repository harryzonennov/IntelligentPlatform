package com.company.IntelligentPlatform.sales.dto;

import com.company.IntelligentPlatform.sales.model.SalesArea;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;

public class SalesAreaUIModel extends SEUIComModel {

	public static final String SEC_PARENTAREA = "parentArea";
	
	public static final String SEC_ROOTAREA = "rootArea";

	@ISEUIModelMapping(fieldName = "level", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SalesArea.SENAME, secId = "basic")
	@ISEDropDownResourceMapping(resouceMapping = "SalesArea_level", valueFieldName = "levelValue")
	protected int level;
	
	@ISEUIModelMapping(seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SalesArea.SENAME, showOnList = false, searchFlag = false, secId = SalesArea.SENAME, tabId = TABID_BASIC)
	protected String levelValue;

	@ISEUIModelMapping(fieldName = "parentAreaUUID", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SalesArea.SENAME, hiddenFlag = true, secId = SalesArea.SENAME, tabId = TABID_BASIC)
	protected String parentAreaUUID;
	
	@ISEUIModelMapping(fieldName = "id", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SEC_PARENTAREA, secId = SEC_PARENTAREA, tabId = TABID_BASIC)
	protected String parentAreaID;
	
	@ISEUIModelMapping(fieldName = "name", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SEC_PARENTAREA, secId = SEC_PARENTAREA, tabId = TABID_BASIC)
	protected String parentAreaName;

	@ISEUIModelMapping(fieldName = "rootAreaUUID", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SalesArea.SENAME, hiddenFlag = true,  secId = SalesArea.SENAME, tabId = TABID_BASIC)
	protected String rootAreaUUID;
	
	@ISEUIModelMapping(fieldName = "id", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SEC_ROOTAREA, secId = SEC_ROOTAREA, tabId = TABID_BASIC)
	protected String rootAreaID;
	
	@ISEUIModelMapping(fieldName = "name", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SEC_ROOTAREA, secId = SEC_ROOTAREA, tabId = TABID_BASIC)
	protected String rootAreaName;
	
	@ISEUIModelMapping(fieldName = "convLabelType", seName = SalesArea.SENAME, nodeName = SalesArea.NODENAME, nodeInstID = SEC_ROOTAREA, secId = SEC_ROOTAREA, tabId = TABID_BASIC)
	@ISEDropDownResourceMapping(resouceMapping = "SalesArea_convLabelType", valueFieldName = "")
	protected int convLabelType;
	
	protected String nameLabel;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

	public String getParentAreaUUID() {
		return parentAreaUUID;
	}

	public void setParentAreaUUID(String parentAreaUUID) {
		this.parentAreaUUID = parentAreaUUID;
	}

	public String getParentAreaID() {
		return parentAreaID;
	}

	public void setParentAreaID(String parentAreaID) {
		this.parentAreaID = parentAreaID;
	}

	public String getParentAreaName() {
		return parentAreaName;
	}

	public void setParentAreaName(String parentAreaName) {
		this.parentAreaName = parentAreaName;
	}

	public String getRootAreaUUID() {
		return rootAreaUUID;
	}

	public void setRootAreaUUID(String rootAreaUUID) {
		this.rootAreaUUID = rootAreaUUID;
	}

	public String getRootAreaID() {
		return rootAreaID;
	}

	public void setRootAreaID(String rootAreaID) {
		this.rootAreaID = rootAreaID;
	}

	public String getRootAreaName() {
		return rootAreaName;
	}

	public void setRootAreaName(String rootAreaName) {
		this.rootAreaName = rootAreaName;
	}

	public int getConvLabelType() {
		return convLabelType;
	}

	public void setConvLabelType(int convLabelType) {
		this.convLabelType = convLabelType;
	}

	public String getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(String nameLabel) {
		this.nameLabel = nameLabel;
	}
	
}
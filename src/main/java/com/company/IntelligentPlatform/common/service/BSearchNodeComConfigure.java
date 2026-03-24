package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;

public class BSearchNodeComConfigure implements Cloneable {

	/**
	 * base node is the parent node of current node
	 */
	public static final int TOBASENODE_TO_PARENT = 1;

	/**
	 * base node is the root node of current node
	 */
	public static final int TOBASENODE_TO_ROOT = 2;

	/**
	 * base node is the child node of current node
	 */
	public static final int TOBASENODE_TO_CHILD = 3;

	/**
	 * current node is the root node of base node
	 */
	public static final int TOBASENODE_FROM_ROOT = 4;

	/**
	 * base node is target reference node of current node
	 */
	public static final int TOBASENODE_REFTO_TARGET = 5;

	/**
	 * current node is target reference node of base node
	 */
	public static final int TOBASENODE_REFTO_SOURCE = 6;

	/**
	 * other relationship
	 */
	public static final int TOBASENODE_OTHERS = 7;

	protected boolean filterFlag;

	protected boolean startNodeFlag;

	protected String seName;

	protected String nodeName;

	protected String nodeInstID;

	protected String baseNodeInstID;

	protected String mapSourceFieldName;

	protected String mapBaseFieldName;

	protected List<SearchConfigConnectCondition> connectionConditions = new ArrayList<>();

	protected List<SearchConfigPreCondition> preConditions = new ArrayList<>();

	protected List<ServiceEntityNode> rawSearchList = new ArrayList<>();

	protected String tablename;

	protected String rawSearchCommand;

	protected List<SEFieldSearchConfig> fieldConfigList = new ArrayList<>();

	protected int toBaseNodeType;
	
	protected List<String> tableNameList = new ArrayList<>();
	
	protected boolean tableSplitFlag = false;

	/**
	 * Indicate local node inst id inside search group
	 */
	protected String subNodeInstId;

	public boolean isFilterFlag() {
		return filterFlag;
	}

	public void setFilterFlag(boolean filterFlag) {
		this.filterFlag = filterFlag;
	}

	public boolean isStartNodeFlag() {
		return startNodeFlag;
	}

	public void setStartNodeFlag(boolean startNodeFlag) {
		this.startNodeFlag = startNodeFlag;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeInstID() {
		return nodeInstID;
	}

	public void setNodeInstID(String nodeInstID) {
		this.nodeInstID = nodeInstID;
	}

	public String getBaseNodeInstID() {
		return baseNodeInstID;
	}

	public void setBaseNodeInstID(String baseNodeInstID) {
		this.baseNodeInstID = baseNodeInstID;
	}

	public String getMapSourceFieldName() {
		return mapSourceFieldName;
	}

	public void setMapSourceFieldName(String mapSourceFieldName) {
		this.mapSourceFieldName = mapSourceFieldName;
	}

	public String getMapBaseFieldName() {
		return mapBaseFieldName;
	}

	public void setMapBaseFieldName(String mapBaseFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
	}

	public List<SearchConfigConnectCondition> getConnectionConditions() {
		return connectionConditions;
	}

	public void setConnectionConditions(
			List<SearchConfigConnectCondition> connectionConditions) {
		this.connectionConditions = connectionConditions;
	}

	public List<SearchConfigPreCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<SearchConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
	}

	public List<ServiceEntityNode> getRawSearchList() {
		return rawSearchList;
	}

	public void setRawSearchList(List<ServiceEntityNode> rawSearchList) {
		this.rawSearchList = rawSearchList;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getRawSearchCommand() {
		return rawSearchCommand;
	}

	public void setRawSearchCommand(String rawSearchCommand) {
		this.rawSearchCommand = rawSearchCommand;
	}

	public List<SEFieldSearchConfig> getFieldConfigList() {
		return fieldConfigList;
	}

	public void setFieldConfigList(List<SEFieldSearchConfig> fieldConfigList) {
		this.fieldConfigList = fieldConfigList;
	}

	public int getToBaseNodeType() {
		return toBaseNodeType;
	}

	public void setToBaseNodeType(int toBaseNodeType) {
		this.toBaseNodeType = toBaseNodeType;
	}
	
	public List<String> getTableNameList() {
		return tableNameList;
	}

	public void setTableNameList(List<String> tableNameList) {
		this.tableNameList = tableNameList;
	}

	public boolean isTableSplitFlag() {
		return tableSplitFlag;
	}

	public void setTableSplitFlag(boolean tableSplitFlag) {
		this.tableSplitFlag = tableSplitFlag;
	}

	public String getSubNodeInstId() {
		return subNodeInstId;
	}

	public void setSubNodeInstId(String subNodeInstId) {
		this.subNodeInstId = subNodeInstId;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			// Should raise exception
			return null;
		}
		return o;
	}

}

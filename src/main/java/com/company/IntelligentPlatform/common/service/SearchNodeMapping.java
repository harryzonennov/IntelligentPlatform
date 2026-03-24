package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public class SearchNodeMapping {

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

	/**
	 * Indicate whether this is the start node in search
	 */
	protected boolean startNodeFlag;

	/**
	 * Indicate whether on this node has search filter effect
	 */
	protected boolean filterFlag;

	protected String nodeName;

	protected String seName;

	protected int ToBaseNodeType;

	protected String mapSourceFieldName;

	protected String mapBaseFieldName;

	protected String baseNodeName;

	protected String baseSEName;

	protected String baseExtralID;

	protected List<ServiceEntityNode> rawSearchList;

	protected List<SearchConfigPreCondition> preConditions;

	protected List<SearchConfigConnectCondition> connectConditions;

	/**
	 * <code>extralID</code> is used for the case to indicate different SE
	 * instance in dynamic search, value should be mapped to
	 * <code>extralID</code> in annotation <code>ISearchFieldConfig</code>
	 */
	protected String extralID;

	public boolean isStartNodeFlag() {
		return startNodeFlag;
	}

	public void setStartNodeFlag(boolean startNodeFlag) {
		this.startNodeFlag = startNodeFlag;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public int getToBaseNodeType() {
		return ToBaseNodeType;
	}

	public void setToBaseNodeType(int toBaseNodeType) {
		ToBaseNodeType = toBaseNodeType;
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

	public List<ServiceEntityNode> getRawSearchList() {
		return rawSearchList;
	}

	public void setRawSearchList(List<ServiceEntityNode> rawSearchList) {
		this.rawSearchList = rawSearchList;
	}

	public boolean isFilterFlag() {
		return filterFlag;
	}

	public void setFilterFlag(boolean filterFlag) {
		this.filterFlag = filterFlag;
	}

	public String getBaseNodeName() {
		return baseNodeName;
	}

	public void setBaseNodeName(String baseNodeName) {
		this.baseNodeName = baseNodeName;
	}

	public String getBaseSEName() {
		return baseSEName;
	}

	public void setBaseSEName(String baseSEName) {
		this.baseSEName = baseSEName;
	}

	public List<SearchConfigPreCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<SearchConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
	}

	public List<SearchConfigConnectCondition> getConnectConditions() {
		return connectConditions;
	}

	public void setConnectConditions(
			List<SearchConfigConnectCondition> connectConditions) {
		this.connectConditions = connectConditions;
	}

	public String getExtralID() {
		return extralID;
	}

	public void setExtralID(String extralID) {
		this.extralID = extralID;
	}

	public String getBaseExtralID() {
		return baseExtralID;
	}

	public void setBaseExtralID(String baseExtralID) {
		this.baseExtralID = baseExtralID;
	}

}

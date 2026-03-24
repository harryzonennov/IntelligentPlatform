package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.SearchConfigPreCondition;
import com.company.IntelligentPlatform.common.model.*;

public class SENodeFieldSearchInfo {

	protected String searchStatement;

	protected String nodeName;

	protected String seName;

	protected String tableName;

	protected String extralID;

	protected List<SEFieldSearchConfig> fieldConfigList;

	protected List<ServiceEntityNode> result;

	protected List<SearchConfigPreCondition> preConditions;

	protected boolean filterFlag;

	public SENodeFieldSearchInfo() {
		fieldConfigList = new ArrayList<SEFieldSearchConfig>();
	}

	public String getSearchStatement() {
		return searchStatement;
	}

	public void setSearchStatement(String searchStatement) {
		this.searchStatement = searchStatement;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SEFieldSearchConfig> getFieldConfigList() {
		return fieldConfigList;
	}

	public void setFieldConfigList(List<SEFieldSearchConfig> fieldConfigList) {
		this.fieldConfigList = fieldConfigList;
	}

	public void addNewFieldConfig(SEFieldSearchConfig nodeSearchConfig) {
		this.fieldConfigList.add(nodeSearchConfig);
	}

	public List<ServiceEntityNode> getResult() {
		return result;
	}

	public void setResult(List<ServiceEntityNode> result) {
		this.result = result;
	}

	public boolean isFilterFlag() {
		return filterFlag;
	}

	public void setFilterFlag(boolean filterFlag) {
		this.filterFlag = filterFlag;
	}

	public String getExtralID() {
		return extralID;
	}

	public void setExtralID(String extralID) {
		this.extralID = extralID;
	}

	public List<SearchConfigPreCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<SearchConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
	}

}

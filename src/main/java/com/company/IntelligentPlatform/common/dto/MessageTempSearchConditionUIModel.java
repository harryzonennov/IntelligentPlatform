package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class MessageTempSearchConditionUIModel extends SEUIComModel {

	protected String fieldName;

	protected int logicOperator;
	
	protected String logicOperatorValue;

	protected int searchOperator;

	protected String fieldValue;
	
	protected String parentNodeId;
	
	protected String searchModelName;

	protected String dataSourceProviderId;

	protected int dataOffsetDirection;

	protected double dataOffsetValue;

	protected String dataOffsetUnit;

	protected int switchFlag;

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getLogicOperator() {
		return this.logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
	}

	public int getSearchOperator() {
		return this.searchOperator;
	}

	public void setSearchOperator(int searchOperator) {
		this.searchOperator = searchOperator;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getLogicOperatorValue() {
		return logicOperatorValue;
	}

	public void setLogicOperatorValue(String logicOperatorValue) {
		this.logicOperatorValue = logicOperatorValue;
	}

	public String getSearchModelName() {
		return searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public String getDataSourceProviderId() {
		return dataSourceProviderId;
	}

	public void setDataSourceProviderId(String dataSourceProviderId) {
		this.dataSourceProviderId = dataSourceProviderId;
	}

	public int getDataOffsetDirection() {
		return dataOffsetDirection;
	}

	public void setDataOffsetDirection(int dataOffsetDirection) {
		this.dataOffsetDirection = dataOffsetDirection;
	}

	public double getDataOffsetValue() {
		return dataOffsetValue;
	}

	public void setDataOffsetValue(double dataOffsetValue) {
		this.dataOffsetValue = dataOffsetValue;
	}

	public String getDataOffsetUnit() {
		return dataOffsetUnit;
	}

	public void setDataOffsetUnit(String dataOffsetUnit) {
		this.dataOffsetUnit = dataOffsetUnit;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}
}

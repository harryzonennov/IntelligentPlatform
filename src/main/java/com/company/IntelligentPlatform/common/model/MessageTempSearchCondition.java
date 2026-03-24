package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class MessageTempSearchCondition extends ReferenceNode {
	
	public static final String NODENAME = IServiceModelConstants.MessageTempSearchCondition;

	public static final String SENAME = IServiceModelConstants.MessageTemplate;
	
	public MessageTempSearchCondition() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;
		this.switchFlag = StandardSwitchProxy.SWITCH_ON;
	}
	
	protected String fieldName;
	
	protected String fieldValue;
	
	protected int searchOperator;
	
	protected int logicOperator;

	protected String dataSourceProviderId;

	protected int dataOffsetDirection;

	protected double dataOffsetValue;

	protected String dataOffsetUnit;

	protected int switchFlag;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getSearchOperator() {
		return searchOperator;
	}

	public void setSearchOperator(int searchOperator) {
		this.searchOperator = searchOperator;
	}

	public int getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(int logicOperator) {
		this.logicOperator = logicOperator;
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

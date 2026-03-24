package com.company.IntelligentPlatform.common.service;

/**
 * This is for cofigure the dynamic search, how to connect from uppers stream to
 * down stream in search configure map
 * 
 * @author Zhang,Hang
 * 
 */
public class SearchConfigConnectCondition {

	protected String sourceFieldName;

	protected Object targetFieldName;

	public SearchConfigConnectCondition() {
	}

	public SearchConfigConnectCondition(String sourceFieldName, Object targetFieldName) {
		this.sourceFieldName = sourceFieldName;
		this.targetFieldName = targetFieldName;
	}

	public String getSourceFieldName() {
		return sourceFieldName;
	}

	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}

	public Object getTargetFieldName() {
		return targetFieldName;
	}

	public void setTargetFieldName(Object targetFieldName) {
		this.targetFieldName = targetFieldName;
	}

}

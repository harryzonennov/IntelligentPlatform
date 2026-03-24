package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ResFinAccountFieldSetting extends ServiceEntityNode{
	
	protected String fieldName;
	
	protected int weightFactor;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String finAccProxyClassName;
	
	protected String finAccProxyMethodName;
	
	public static final String NODENAME = IServiceModelConstants.ResFinAccountFieldSetting;

	public static final String SENAME = SystemResource.SENAME;
	
	public static int nodeCategory = NODE_CATEGORY_CONFIG;

	public ResFinAccountFieldSetting() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.weightFactor = 1;
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
	

}

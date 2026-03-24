package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class SerExtendDoubleField extends ReferenceNode{
	
	public static final String NODENAME = IServiceModelConstants.SerExtendDoubleField;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;
	
	public SerExtendDoubleField() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}
	
	protected String fieldName;

	protected double fieldValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public double getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(double fieldValue) {
		this.fieldValue = fieldValue;
	}

}

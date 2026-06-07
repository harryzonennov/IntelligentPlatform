package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SerExtendIntField", catalog = "platform")
public class SerExtendIntField extends ReferenceNode{
	
	public static final String NODENAME = IServiceModelConstants.SerExtendIntField;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;
	
	public SerExtendIntField() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}
	
	protected String fieldName;

	protected int fieldValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(int fieldValue) {
		this.fieldValue = fieldValue;
	}

}

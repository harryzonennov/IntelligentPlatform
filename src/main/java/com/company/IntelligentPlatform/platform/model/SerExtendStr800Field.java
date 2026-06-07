package com.company.IntelligentPlatform.platform.model;

import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import com.company.IntelligentPlatform.platform.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SerExtendStr800Field", catalog = "platform")
public class SerExtendStr800Field extends ReferenceNode{
	
	public static final String NODENAME = IServiceModelConstants.SerExtendStr800Field;

	public static final String SENAME = IServiceModelConstants.ServiceExtensionSetting;
	
	public SerExtendStr800Field() {
		super.serviceEntityName = SENAME;
		super.nodeName = NODENAME;	
	}
	
	protected String fieldName;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_800)
	protected String fieldValue;

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

}

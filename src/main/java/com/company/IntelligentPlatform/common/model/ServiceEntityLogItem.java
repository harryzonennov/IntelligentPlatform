package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Class to record logging for service entity node instance
 * 
 * @author ZhangHang
 * @date Nov 25, 2012
 * 
 */
public class ServiceEntityLogItem extends ReferenceNode {

	public static final String SENAME = IServiceModelConstants.ServiceEntityLogModel;

	public static final String NODENAME = IServiceModelConstants.ServiceEntityLogItem;
	
	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String oldValue;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_3000)
	protected String newValue;
	
	protected String fieldType;

	public ServiceEntityLogItem() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}

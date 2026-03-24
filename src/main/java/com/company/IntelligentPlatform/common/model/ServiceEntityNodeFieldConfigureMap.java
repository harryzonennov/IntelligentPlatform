package com.company.IntelligentPlatform.common.model;

/**
 * Class to store each Service Entity Node field Configuration information
 * 
 * @author ZhangHang
 * @date Nov 10, 2012
 * 
 */
public class ServiceEntityNodeFieldConfigureMap {

	protected String fieldName;

	@SuppressWarnings("rawtypes")
	protected Class fieldType;

	public ServiceEntityNodeFieldConfigureMap(String fieldName, Class<?> fieldType) {
		super();
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
	
	public ServiceEntityNodeFieldConfigureMap() {
		super();
	}


	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@SuppressWarnings("rawtypes")
	public Class getFieldType() {
		return fieldType;
	}

	@SuppressWarnings("rawtypes")
	public void setFieldType(Class fieldType) {
		this.fieldType = fieldType;
	}

}

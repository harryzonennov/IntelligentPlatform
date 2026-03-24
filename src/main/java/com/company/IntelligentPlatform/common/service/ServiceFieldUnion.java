package com.company.IntelligentPlatform.common.service;

/**
 * Standard model class to contain field information.
 *
 * @author Zhang, Hang
 */
public class ServiceFieldUnion {

    protected String fieldName;

    protected String fieldLabel;

    protected String simpleFieldType;

    protected Class<?> fieldType;

    public ServiceFieldUnion(String fieldName, String fieldLabel,
                             String simpleFieldType) {
        super();
        this.fieldName = fieldName;
        this.fieldLabel = fieldLabel;
        this.simpleFieldType = simpleFieldType;
    }

    public ServiceFieldUnion(String fieldName, String fieldLabel,
                             String simpleFieldType, Class<?> fieldType) {
        super();
        this.fieldName = fieldName;
        this.fieldLabel = fieldLabel;
        this.simpleFieldType = simpleFieldType;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getSimpleFieldType() {
        return simpleFieldType;
    }

    public void setSimpleFieldType(String simpleFieldType) {
        this.simpleFieldType = simpleFieldType;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

}

package com.company.IntelligentPlatform.common.model;

import java.io.Serializable;

/**
 * Field Meta data for Serializable
 */
public class ServiceFieldMeta implements Serializable {

    protected String fieldName;

    protected String fieldTypeStr;

    private boolean listAssign;

    public ServiceFieldMeta() {
    }

    public ServiceFieldMeta(String fieldName, String fieldTypeStr, boolean listAssign) {
        this.fieldName = fieldName;
        this.fieldTypeStr = fieldTypeStr;
        this.listAssign = listAssign;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTypeStr() {
        return fieldTypeStr;
    }

    public void setFieldTypeStr(String fieldTypeStr) {
        this.fieldTypeStr = fieldTypeStr;
    }

    public boolean isListAssign() {
        return listAssign;
    }

    public void setListAssign(boolean listAssign) {
        this.listAssign = listAssign;
    }
}

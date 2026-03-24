package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;

public class ServiceBasicFieldValueUnion {

    protected Field field;

    protected Object value;

    public ServiceBasicFieldValueUnion() {
    }

    public ServiceBasicFieldValueUnion(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

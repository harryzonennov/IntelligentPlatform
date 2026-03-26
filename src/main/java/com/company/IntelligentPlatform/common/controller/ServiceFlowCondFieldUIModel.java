package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

public class ServiceFlowCondFieldUIModel extends SEUIComModel {

    protected String nodeInstId;

    protected String fieldName;

    protected String groupId;

    protected int valueOperator;

    protected String valueOperatorValue;

    protected String fieldType;

    protected String fieldTypeValue;

    protected String targetValue;

    protected String serviceUIModuleId;

    protected String rootDocId;

    protected String rootDocName;

    public String getNodeInstId() {
        return nodeInstId;
    }

    public void setNodeInstId(String nodeInstId) {
        this.nodeInstId = nodeInstId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getValueOperator() {
        return valueOperator;
    }

    public void setValueOperator(int valueOperator) {
        this.valueOperator = valueOperator;
    }

    public String getValueOperatorValue() {
        return valueOperatorValue;
    }

    public void setValueOperatorValue(String valueOperatorValue) {
        this.valueOperatorValue = valueOperatorValue;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTypeValue() {
        return fieldTypeValue;
    }

    public void setFieldTypeValue(String fieldTypeValue) {
        this.fieldTypeValue = fieldTypeValue;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getServiceUIModuleId() {
        return serviceUIModuleId;
    }

    public void setServiceUIModuleId(String serviceUIModuleId) {
        this.serviceUIModuleId = serviceUIModuleId;
    }

    public String getRootDocId() {
        return rootDocId;
    }

    public void setRootDocId(String rootDocId) {
        this.rootDocId = rootDocId;
    }

    public String getRootDocName() {
        return rootDocName;
    }

    public void setRootDocName(String rootDocName) {
        this.rootDocName = rootDocName;
    }
}

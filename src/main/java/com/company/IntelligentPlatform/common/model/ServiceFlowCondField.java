package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.StandardValueComparatorProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceFlowCondField extends ServiceEntityNode{

    public static final String NODENAME = IServiceModelConstants.ServiceFlowCondField;

    public static final String SENAME = IServiceModelConstants.ServiceFlowModel;

    public ServiceFlowCondField(){
        this.nodeName = NODENAME;
        this.serviceEntityName = SENAME;
        this.valueOperator = StandardValueComparatorProxy.OPERATOR_EQUAL;
    }

    protected String nodeInstId;

    protected String fieldName;

    protected int valueOperator;

    protected String fieldType;

    protected String targetValue;

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

    public int getValueOperator() {
        return valueOperator;
    }

    public void setValueOperator(int valueOperator) {
        this.valueOperator = valueOperator;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }
}

package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

import jakarta.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemLogicValueCalculator {

    @Autowired
    protected StandardLogicOperatorProxy standardLogicOperatorProxy;

    @Autowired
    protected StandardValueComparatorProxy standardValueComparatorProxy;

    protected Logger logger = LoggerFactory.getLogger(SystemLogicValueCalculator.class);

    public boolean executeBatch(List<InputGroup> inputGroupList){
        if(ServiceCollectionsHelper.checkNullList(inputGroupList)){
            return false;
        }
        // Group by serial & parallel
        List<InputGroup> parallelGroupList = inputGroupList.stream().filter(inputGroup -> {
            return inputGroup.getExternalLogicOperator() == StandardLogicOperatorProxy.OPERATOR_OR;
        }).collect(Collectors.toList());
        List<InputGroup> serialGroupList = inputGroupList.stream().filter(inputGroup -> {
            return inputGroup.getExternalLogicOperator() == StandardLogicOperatorProxy.OPERATOR_AND;
        }).collect(Collectors.toList());
        // Process ParallelGroup
        if(!ServiceCollectionsHelper.checkNullList(parallelGroupList)){
            for(InputGroup inputGroup: parallelGroupList){
                boolean result = processInGroup(inputGroup);
                if(result){
                    return true;
                }
            }
        }
        if(!ServiceCollectionsHelper.checkNullList(serialGroupList)){
            for(InputGroup inputGroup: serialGroupList){
                boolean result = processInGroup(inputGroup);
                if(!result){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean processInGroup(InputGroup inputGroup){
        if(ServiceCollectionsHelper.checkNullList(inputGroup.getInputFieldList())){
            return true;
        }
        for(InputField inputField: inputGroup.getInputFieldList()){
            if(inputField.getValue() == null || inputField.getTargetValue() == null){
                continue;
            }
            boolean result = processInField(inputField);
            if(inputGroup.getInnerLogicOperator() == StandardLogicOperatorProxy.OPERATOR_AND){
                if(!result){
                    return false;
                }
            }
            if(inputGroup.getInnerLogicOperator() == StandardLogicOperatorProxy.OPERATOR_OR){
                if(result){
                    return true;
                }
            }
        }
        return true;
    }

    private boolean processInField(InputField inputField){
        if(ServiceEntityFieldsHelper.checkListTypeByValue(inputField.getValue())){
            // in case value is list type
            List rawValueList = (List) inputField.getValue();
            if(ServiceCollectionsHelper.checkNullList(rawValueList)){
                return false;
            }
            for(Object valueObject: rawValueList){
                InputField tempInputField = new InputField(valueObject, inputField.getTargetValue(),
                        inputField.valueOperator, inputField.getField());
                boolean eachResult = StandardValueComparatorProxy.valueCompareField(tempInputField);
                if(eachResult){
                    // In case one member in list hit
                    return true;
                }
            }
            return false;
        } else {
            return StandardValueComparatorProxy.valueCompareField(inputField);
        }
    }

    public static class InputGroup{

        private String groupUUID;

        private int innerLogicOperator;

        private int externalLogicOperator;

        private List<InputField> inputFieldList = new ArrayList<>();

        public InputGroup() {
        }

        public InputGroup(String groupUUID, int innerLogicOperator, int externalLogicOperator) {
            this.groupUUID = groupUUID;
            this.innerLogicOperator = innerLogicOperator;
            this.externalLogicOperator = externalLogicOperator;
        }

        public String getGroupUUID() {
            return groupUUID;
        }

        public void setGroupUUID(String groupUUID) {
            this.groupUUID = groupUUID;
        }

        public int getInnerLogicOperator() {
            return innerLogicOperator;
        }

        public void setInnerLogicOperator(int innerLogicOperator) {
            this.innerLogicOperator = innerLogicOperator;
        }

        public int getExternalLogicOperator() {
            return externalLogicOperator;
        }

        public void setExternalLogicOperator(int externalLogicOperator) {
            this.externalLogicOperator = externalLogicOperator;
        }

        public List<InputField> getInputFieldList() {
            return inputFieldList;
        }

        public void setInputFieldList(List<InputField> inputFieldList) {
            this.inputFieldList = inputFieldList;
        }
    }

    public static class InputField{

        private Object value;

        private Object targetValue;

        private int valueOperator;

        private Field field;

        public InputField() {
        }

        public InputField(Object value, Object targetValue, int valueOperator, Field field) {
            this.value = value;
            this.targetValue = targetValue;
            this.valueOperator = valueOperator;
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getTargetValue() {
            return targetValue;
        }

        public void setTargetValue(Object targetValue) {
            this.targetValue = targetValue;
        }

        public int getValueOperator() {
            return valueOperator;
        }

        public void setValueOperator(int valueOperator) {
            this.valueOperator = valueOperator;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }

}

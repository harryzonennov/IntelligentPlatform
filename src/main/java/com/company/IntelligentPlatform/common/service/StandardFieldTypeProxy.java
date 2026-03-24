package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceFieldMeta;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StandardFieldTypeProxy {

    private Map<String, String> fieldTypeMap;

    public Map<String, String> getFieldTypeMap(){
        if (this.fieldTypeMap == null) {
            this.fieldTypeMap = new HashMap<>();
            this.fieldTypeMap.put(int.class.getSimpleName(),
                    int.class.getSimpleName());
            this.fieldTypeMap.put(String.class.getSimpleName(),
                    String.class.getSimpleName());
            this.fieldTypeMap.put(double.class.getSimpleName(),
                    double.class.getSimpleName());
            this.fieldTypeMap.put(Date.class.getSimpleName(),
                    double.class.getSimpleName());
        }
        return this.fieldTypeMap;
    }


    public Map<String, String> getFormatFieldTypeMap(){
        if (this.fieldTypeMap == null) {
            this.fieldTypeMap = new HashMap<>();
            this.fieldTypeMap.put(Integer.class.getSimpleName(),
                    int.class.getSimpleName());
            this.fieldTypeMap.put(String.class.getSimpleName(),
                    String.class.getSimpleName());
            this.fieldTypeMap.put(Double.class.getSimpleName(),
                    double.class.getSimpleName());
            this.fieldTypeMap.put(Date.class.getSimpleName(),
                    double.class.getSimpleName());
            this.fieldTypeMap.put(Boolean.class.getSimpleName(),
                    double.class.getSimpleName());
        }
        return this.fieldTypeMap;
    }

    /**
     * Core Logic to format Field Type from primitive type to Package type
     * @param rawFieldClass
     * @return
     */
    public static Class<?> formatFieldType(Class<?> rawFieldClass){
        if(rawFieldClass == null){
            return null;
        }
        if(rawFieldClass.equals(int.class)){
            return Integer.class;
        }
        if(rawFieldClass.equals(double.class)){
            return Double.class;
        }
        if(rawFieldClass.equals(boolean.class)){
            return Boolean.class;
        }
        if(rawFieldClass.equals(long.class)){
            return Long.class;
        }
        return rawFieldClass;
    }

    /**
     * Core logic to construct the Service field meta data
     * @param field
     * @return
     */
    public static ServiceFieldMeta buildServiceFieldMeta(Field field){
        Class<?> formatType = field.getType();
        return new ServiceFieldMeta(field.getName(), formatType.getSimpleName(), field.getType().isAssignableFrom(List.class));

    }
}

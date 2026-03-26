package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;

@Service
public class StandardValueComparatorProxy {
    
    public static final String PROPERTIES_RESOURCE = "StandardValueComparator";
	
    public static final int OPERATOR_EQUAL = SEFieldSearchConfig.OPERATOR_EQUAL;
	
    public static final int OPERATOR_BETWEEN = SEFieldSearchConfig.OPERATOR_BETWEEN;
    
    public static final int OPERATOR_NOT_BETWEEN = SEFieldSearchConfig.OPERATOR_NOT_BETWEEN;
    
	public static final int OPERATOR_GREATER_EQ = SEFieldSearchConfig.OPERATOR_GREATER_EQ;

	public static final int OPERATOR_GREATER = SEFieldSearchConfig.OPERATOR_GREATER;

	public static final int OPERATOR_LESS_EQ = SEFieldSearchConfig.OPERATOR_LESS_EQ;
	
	public static final int OPERATOR_LESS = SEFieldSearchConfig.OPERATOR_LESS;

	public static final int OPERATOR_NOT_EQ = SEFieldSearchConfig.OPERATOR_NOT_EQ;

	public static final int OPERATOR_CONTAINS = SEFieldSearchConfig.OPERATOR_CONTAINS;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
    protected Map<Integer, String> valueComparatorMap;
    
	protected Map<String, Map<Integer, String>> valueComparatorMapLan;
			
	public Map<Integer, String> getValueComparatorMap(String languageCode)
			throws ServiceEntityInstallationException {	
		if(this.valueComparatorMapLan == null){
			this.valueComparatorMapLan = new HashMap<>();
		}
		return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.valueComparatorMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<Integer, String> tempSwitchMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
				return tempSwitchMap;
			} catch (IOException e) {
				return null;
			}
		});
	}
	
	public Map<Integer, String> getValueComparatorMap()
			throws ServiceEntityInstallationException {
		if (this.valueComparatorMap == null) {
			try {
				String path = this.getClass().getResource("").getPath();
				this.valueComparatorMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE);
			} catch (IOException e) {
				throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
						e.getMessage());
			}
		}
		return this.valueComparatorMap;
	}
	
	public String getValueComparatorValue(int key) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getValueComparatorMap();
		return switchMap.get(key);		
	}
	
	public String getValueComparatorValue(int key, String languageCode) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getValueComparatorMap(languageCode);
		return switchMap.get(key);		
	}

	// TODO seems useless to process list type here
	private static Class<?> parseFieldValueType(SystemLogicValueCalculator.InputField inputField){
		Class<?> rawClass = inputField.getField().getType();
		if(rawClass.isAssignableFrom(List.class)){
			return ServiceEntityFieldsHelper.getListSubType(inputField.getField());
		} else {
			return rawClass;
		}
	}

	/**
	 * Core Logic to execute value comparator, used for non-list type field
	 * @param inputField
	 * @return
	 */
	public static boolean valueCompareField(SystemLogicValueCalculator.InputField inputField){
		Class<?> valueClass = parseFieldValueType(inputField);
		int basicType = ServiceReflectiveHelper.getBasicType(valueClass);
		if(inputField.getValue() == null || inputField.getTargetValue() == null){
			return false;
		}
		if (basicType == ServiceReflectiveHelper.BASIC_TYPE_STR){
			String strValue = (String) inputField.getValue();
			String strTargetValue = (String)inputField.getTargetValue();
			if(inputField.getValueOperator() == OPERATOR_CONTAINS){
				return strValue.contains(strTargetValue);
			}
		}
		if (basicType == ServiceReflectiveHelper.BASIC_TYPE_STR || basicType == ServiceReflectiveHelper.BASIC_TYPE_OBJ || basicType == ServiceReflectiveHelper.BASIC_TYPE_BOOLEAN) {

			// Only Equal NOT Equal make sense
			if(inputField.getValueOperator() == OPERATOR_EQUAL){
				return inputField.getTargetValue().equals(inputField.getValue());
			}
			if(inputField.getValueOperator() == OPERATOR_NOT_EQ){
				return !inputField.getTargetValue().equals(inputField.getValue());
			}
		}
		if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER) {
			// Convert to Double
			Double doubleTargetValue = Double.valueOf(inputField.getTargetValue().toString());
			Double doubleValue = Double.valueOf(inputField.getValue().toString());
			if(inputField.getValueOperator() == OPERATOR_EQUAL){
				return doubleValue.equals(doubleTargetValue);
			}
			if(inputField.getValueOperator() == OPERATOR_GREATER){
				return doubleValue > doubleTargetValue;
			}
			if(inputField.getValueOperator() == OPERATOR_GREATER_EQ){
				return doubleValue >= doubleTargetValue;
			}
			if(inputField.getValueOperator() == OPERATOR_LESS){
				return doubleValue < doubleTargetValue;
			}
			if(inputField.getValueOperator() == OPERATOR_LESS_EQ){
				return doubleValue <= doubleTargetValue;
			}
		}
		return false;
	}

}

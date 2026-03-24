package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class SimpleDataProviderFactory {

	@Autowired
	protected CurrentTimeDataProvider currentTimeDataProvider;

	@Autowired
	protected CurrentLogonUserDataProvider currentLogonUserDataProvider;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	private Map<String, Map<String, String>> dataProviderLabelMapLan = new HashMap<>();

	/**
	 * Data Provider input information for generate proper data provider
	 */
	public static class DataProviderInput{

		protected Class<?> fieldType;

		protected String fieldName;

		protected String targetModelName;

		public DataProviderInput(){

		}

		public DataProviderInput(Class<?> fieldType, String fieldName, String targetModelName) {
			this.fieldType = fieldType;
			this.fieldName = fieldName;
			this.targetModelName = targetModelName;
		}

		public Class<?> getFieldType() {
			return fieldType;
		}

		public void setFieldType(Class<?> fieldType) {
			this.fieldType = fieldType;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	public ServiceSimpleDataProviderTemplate getSimpleDataProvider(
			String providerId) throws ServiceSimpleDataProviderException {
		if (ServiceEntityStringHelper.checkNullString(providerId)) {
			return null;
		}
		if(providerId.equals(ServiceEntityStringHelper.headerToLowerCase(CurrentTimeDataProvider.class.getSimpleName()))){
			return currentTimeDataProvider;
		}
		if(providerId.equals(ServiceEntityStringHelper.headerToLowerCase(CurrentLogonUserDataProvider.class.getSimpleName()))){
			return currentLogonUserDataProvider;
		}
		throw new ServiceSimpleDataProviderException(
				ServiceSimpleDataProviderException.TYPE_SYSTEM_WRONG);
	}

	/**
	 * Core Logic to return possible data provider according to some input information
	 * @param dataProviderInput
	 * @return
	 */
	public List<ServiceSimpleDataProviderTemplate> getDefSimpleDataProvider(DataProviderInput dataProviderInput){
		/*
		 * [Step1] Retrieve by field type
		 */
		List<ServiceSimpleDataProviderTemplate> dataProviderTemplateList = new ArrayList<>();
		if(dataProviderInput.getFieldType() != null){
			// In case Date type
			if (Date.class.getSimpleName().equals(dataProviderInput.getFieldType().getSimpleName())){
				dataProviderTemplateList.add(currentTimeDataProvider);
				return dataProviderTemplateList;
			}
		}
		/*
		 * [Step2] Retrieve by field name
		 */
		if(!ServiceEntityStringHelper.checkNullString(dataProviderInput.getFieldName())){
			// In case Date type
			String fieldName = dataProviderInput.getFieldName();
			if (fieldName.equals(IServiceEntityNodeFieldConstant.LASTUPDATEBY) ||
					fieldName.equals(IServiceEntityNodeFieldConstant.CREATEDBY) ||
					fieldName.equals(IServiceEntityCommonFieldConstant.EXECUTEDBYUUID)){
				dataProviderTemplateList.add(currentLogonUserDataProvider);
			}
		}
		return dataProviderTemplateList;
	};
	
	public Map<String, String> getDataProviderLabelMap(String languageCode) throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefaultLanguageStrMap(languageCode, this.dataProviderLabelMapLan, lanCode->{
			try {
				String path = SimpleDataProviderFactory.class.getResource("").getPath();
				String resFileName = "SimpleDataProviderLabel";
				Map<String, String> dataProviderMap = serviceDropdownListHelper
						.getStrStaticDropDownMap(path + resFileName, lanCode);
				return dataProviderMap;
			} catch (IOException e) {
				return null;
			}
		});
	}
	
    public String getDataProviderLabel(String languageCode, String key){
    	try {
			Map<String,String> dataProviderLabelMap = getDataProviderLabelMap(languageCode);
			return dataProviderLabelMap.get(key);
		} catch (ServiceEntityInstallationException e) {
			// Do nothing
			return null;
		}
    }
    
    /**
     * Helper method:Push data provider key & label to data provider Hashmap.
     * @param key
     * @return
     */
    public void pushToDataProviderMap(String key, Map<String, String> dataProviderMap){
    	try {
			Map<String,String> dataProviderLabelMap = getDataProviderLabelMap(null);
			String label = dataProviderLabelMap.get(key);
			dataProviderMap.put(key, label);
		} catch (ServiceEntityInstallationException e) {
			// Do nothing			
		}
    }

}

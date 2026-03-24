package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class StandardSystemCategoryProxy {
	
    public static final int CATE_SYSTEM_STANDARD = 1;
	
    public static final int CATE_SELF_DEFINED = 2;
    
    public static final String PROPERTIES_RESOURCE = "StandardSystemCategory";
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

    protected Map<Integer, String> systemCategoryMap;
    
    protected Map<String, Map<Integer, String>> systemCategoryMapLan;
	
	public Map<Integer, String> getSystemCategoryMap(String languageCode)
			throws ServiceEntityInstallationException {	
		if(this.systemCategoryMapLan == null){
			this.systemCategoryMapLan = new HashMap<>();
		}
		return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.systemCategoryMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<Integer, String> tempSystemCategoryMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
				return tempSystemCategoryMap;
			} catch (IOException e) {
				return null;
			}
		});
	}
	

	public Map<Integer, String> getSystemCategoryMap()
			throws ServiceEntityInstallationException {
		if (this.systemCategoryMap == null) {
			try {
				String path = this.getClass().getResource("").getPath();
				this.systemCategoryMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE);
			} catch (IOException e) {
				throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
						e.getMessage());
			}
		}
		return this.systemCategoryMap;
	}
		
	public Map<Integer, String> getSearchSystemCategoryMap() throws ServiceEntityInstallationException{
		Map<Integer, String> categoryMap = getSystemCategoryMap();
		categoryMap.put(0, ServiceEntityStringHelper.EMPTYSTRING);
		return categoryMap;
	}
	
	public String getSystemCategoryValue(int key) throws ServiceEntityInstallationException{
		Map<Integer, String> categoryMap = getSystemCategoryMap();
		return categoryMap.get(key);		
	}

}

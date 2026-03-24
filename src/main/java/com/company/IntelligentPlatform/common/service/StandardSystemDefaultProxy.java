package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

@Service
public class StandardSystemDefaultProxy {
	
    public static final int DEFAULT_OFF = 1;
	
    public static final int DEFAULT_ON = 2;
    
    public static final String PROPERTIES_RESOURCE = "StandardSystemDefault";
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	protected Map<Integer, String> systemDefaultMap;
	
	protected Map<String, Map<Integer, String>> systemDefaultMapLan;
			
	public Map<Integer, String> getSystemDefaultMap(String languageCode)
			throws ServiceEntityInstallationException {	
		if(this.systemDefaultMapLan == null){
			this.systemDefaultMapLan = new HashMap<>();
		}
		return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.systemDefaultMapLan, lanCode->{
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

	public Map<Integer, String> getSystemDefaultMap()
			throws ServiceEntityInstallationException {
		if (this.systemDefaultMap == null) {
			try {
				String path = this.getClass().getResource("").getPath();
				this.systemDefaultMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE);
			} catch (IOException e) {
				throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
						e.getMessage());
			}
		}
		return this.systemDefaultMap;
	}

}

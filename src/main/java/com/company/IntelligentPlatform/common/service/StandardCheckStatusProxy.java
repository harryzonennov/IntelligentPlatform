package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.util.Map;

@Service
public class StandardCheckStatusProxy {
	
    public static final int OK = 1;
	
    public static final int WARNING = 2;
    
    public static final int ERROR = 3;
    
	protected Map<String, Map<Integer, String>> checkStatusMapLan;
    
    public static final String PROPERTIES_RESOURCE = "StandardCheckStatus";
			
	public Map<Integer, String> getCheckStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.checkStatusMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
	}
	
	public String getCheckStatusValue(int key, String languageCode) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getCheckStatusMap(languageCode);
		return switchMap.get(key);		
	}

}

package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.util.Map;


@Service
public class ServiceItemUpdateModelProxy {
    
    public static final String PROPERTIES_RESOURCE = "ServiceItemUpdateModel";
	
    public static final int UPDATE_MODE_NOCHANGE = 1;
	
    public static final int UPDATE_MODE_CREATE = 2;
    
    public static final int UPDATE_MODE_DELETE = 3;
    
	public static final int UPDATE_MODE_INCREASE = 4;

	public static final int UPDATE_MODE_DECREASE = 5;

	public static final int UPDATE_MODE_UPDATE = 6;
    
	protected Map<String, Map<Integer, String>> updateModeMapLan;
			
	public Map<Integer, String> getUpdateModeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.updateModeMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
	}
	
	public String getUpdateModeValue(int key, String languageCode) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getUpdateModeMap(languageCode);
		return switchMap.get(key);		
	}



}

package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageLevelCodeProxy {

	public static final int MESSAGELEVEL_INFO = SimpleSEMessageResponse.MESSAGELEVEL_INFO;

	public static final int MESSAGELEVEL_WARN = SimpleSEMessageResponse.MESSAGELEVEL_WARN;

	public static final int MESSAGELEVEL_ERROR = SimpleSEMessageResponse.MESSAGELEVEL_ERROR;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	protected Map<String, Map<Integer, String>> messageLevelMapLan;
    
    public static final String PROPERTIES_RESOURCE = "MessageLevelCode";
			
	public Map<Integer, String> getMessageLevelMap(String languageCode)
			throws ServiceEntityInstallationException {	
		if(this.messageLevelMapLan == null){
			this.messageLevelMapLan = new HashMap<>();
		}
		return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.messageLevelMapLan, lanCode->{
			try {
				String path = this.getClass().getResource("").getPath();
				Map<Integer, String> messageLevelMap = serviceDropdownListHelper
						.getDropDownMap(path + PROPERTIES_RESOURCE, lanCode);
				return messageLevelMap;
			} catch (IOException e) {
				return null;
			}
		});
	}
	public String getMessageLevelValue(int key, String languageCode) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getMessageLevelMap(languageCode);
		return switchMap.get(key);		
	}

}

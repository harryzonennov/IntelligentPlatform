package com.company.IntelligentPlatform.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.util.Map;


@Service
public class ServiceFlowInvolveTaskProxy {
    
    public static final String PROPERTIES_RESOURCE = "ServiceFlowInvolveTask";
	
    public static final int STATUS_NONE = 1;
	
    public static final int STATUS_BLOCK_OTHER = 2;

	public static final int STATUS_HIT = 3;
	
	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;
    
	protected Map<String, Map<Integer, String>> flowInvolveTaskStatusMapLan;
			
	public Map<Integer, String> getSerialParallelMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.flowInvolveTaskStatusMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
	}
	
	public String getgetSerialParallelValue(int key, String languageCode) throws ServiceEntityInstallationException{
		Map<Integer, String> switchMap = getSerialParallelMap(languageCode);
		return switchMap.get(key);		
	}

}

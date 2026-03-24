package com.company.IntelligentPlatform.common.service;


import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.util.Map;


@Service
public class SystemSerialParallelProxy {
    
    public static final String PROPERTIES_RESOURCE = "SystemSerialParallel";
	
    public static final int OP_SERIAL = 1;
	
    public static final int OP_PARALLEL = 2;
    
	protected Map<String, Map<Integer, String>> serialParallelMapLan;
			
	public Map<Integer, String> getSerialParallelMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.serialParallelMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
	}


}

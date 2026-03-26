package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.util.Map;

@Service
public class SystemMandatoryModeProxy {
    
    public static final String PROPERTIES_RESOURCE = "SystemMandatoryModel";
	
    public static final int MODE_MANDATORY = 1;
	
    public static final int MODE_SELECTIVE = 2;
    
	protected Map<String, Map<Integer, String>> mandatoryModelMapLan;
			
	public Map<Integer, String> getMandatoryModelMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapResource(languageCode,
				this.mandatoryModelMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
	}

}

package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

@Service
public class StandardPriorityProxy {

    public static final int LOW = 1;

    public static final int MEDIUM = 2;

    public static final int HIGH = 3;

    public static final int VERY_HIGH = 4;

    protected Map<Integer, String> priorityMap;

    protected Map<String, Map<Integer, String>> priorityMapLan;

    public static final String PROPERTIES_RESOURCE = "StandardPriorityCode";

    public Map<Integer, String> getPriorityMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapResource(languageCode,
                this.priorityMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
    }


    public String getPriorityValue(int key, String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> switchMap = getPriorityMap(languageCode);
        return switchMap.get(key);
    }

}

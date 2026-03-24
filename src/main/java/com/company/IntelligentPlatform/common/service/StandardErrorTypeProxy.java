package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

@Service
public class StandardErrorTypeProxy {

    public static int MESSAGE_TYPE_NOTIFICATION = 1;

    public static int MESSAGE_TYPE_WARNING = 2;

    public static int MESSAGE_TYPE_ERROR = 3;

    public static final String PROPERTIES_RESOURCE = "StandardErrorType";

    private Map<Integer, String> errorTypeMap;

    protected Map<String, Map<Integer, String>> errorTypeMapLan;

    public Map<Integer, String> getErrorTypeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapResource(languageCode,
                this.errorTypeMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);
    }


    public String getErrorTypeValue(int key, String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> errorTypeMap = getErrorTypeMap(languageCode);
        return errorTypeMap.get(key);
    }

}

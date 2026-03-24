package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

@Service
public class StandardGenderProxy {

    public static final int GENDER_MALE = 1;

    public static final int GENDER_FEMALE = 2;

    public static final String PROPERTIES_RESOURCE = "StandardGender";

    protected Map<String, Map<Integer, String>> genderMapLan;

    public Map<Integer, String> getGenderMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapResource(languageCode,
                this.genderMapLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);

    }

}

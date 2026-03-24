package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.util.Map;

@Service
public class StandardDocFlowDirectionProxy {

    public static final int DOCFLOW_DIREC_PREV = 1;

    public static final int DOCFLOW_DIREC_NEXT = 2;

    public static final int DOCFLOW_DIREC_PREV_PROF = 3;

    public static final int DOCFLOW_DIREC_NEXT_PROF = 4;

    public static final int DOCFLOW_TO_RESERVED_BY = 5;

    public static final int DOCFLOW_TO_RESERVE_TARGET = 6;

    public static final String PROPERTIES_RESOURCE = "StandardDocFlowDirectionProxy";

    protected Map<String, Map<Integer, String>> docFlowDirectLan;

    public Map<Integer, String> getDocFlowDirectMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapResource(languageCode,
                this.docFlowDirectLan, this.getClass().getResource("").getPath() + PROPERTIES_RESOURCE);

    }

    public String getDocFlowDirectValue(int key, String languageCode) throws ServiceEntityInstallationException{
        Map<Integer, String> tempFlowDirectMap = getDocFlowDirectMap(languageCode);
        return tempFlowDirectMap.get(key);
    }

}

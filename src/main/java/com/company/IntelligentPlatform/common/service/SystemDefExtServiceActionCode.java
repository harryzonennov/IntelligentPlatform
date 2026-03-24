package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provide the extend common action code
 */
@Service
public class SystemDefExtServiceActionCode {

    public static final String ACTION_SEARCH = "search";

    public static final String ACTION_NEW = "new";

    public static final String ACTION_BATCH_INBOUND = "inbound";

    public static final String ACTION_BATCH_OUTBOUND = "outbound";

    public static final String ACTION_START_PROCESS = "startProcess";

    public static final String PROPERTIES_RESOURCE = "SystemDefExtServiceActionCode";

    protected Map<String, Map<String, String>> actionCodeMapLan = new HashMap<>();

    public Map<String, String> getActionCodeMap(String languageCode)
            throws ServiceEntityInstallationException {
        if(this.actionCodeMapLan == null){
            this.actionCodeMapLan = new HashMap<>();
        }
        return ServiceLanHelper.initDefaultLanguageStrMap(languageCode, this.actionCodeMapLan, lanCode->{
            try {
                String path = this.getClass().getResource("").getPath();
                Map<String, String> tempActionCodeMap = ServiceDropdownListHelper
                        .getStrStaticDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
                return tempActionCodeMap;
            } catch (IOException e) {
                return null;
            }
        });
    }
}

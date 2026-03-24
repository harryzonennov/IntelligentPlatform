package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class StandardKeyFlagProxy {

    public static final int KEY = 1;

    public static final int NON_KEY = 2;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    protected Map<Integer, String> keyFlagMap;

    protected Map<String, Map<Integer, String>> keyFlagMapLan;

    public static final String PROPERTIES_RESOURCE = "StandardKeyFlag";

    public Map<Integer, String> getKeyFlagMap(String languageCode)
            throws ServiceEntityInstallationException {
        if (this.keyFlagMapLan == null) {
            this.keyFlagMapLan = new HashMap<>();
        }
        return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.keyFlagMapLan, lanCode -> {
            try {
                String path = this.getClass().getResource("").getPath();
                Map<Integer, String> tempSwitchMap = serviceDropdownListHelper
                        .getDropDownMap(path + PROPERTIES_RESOURCE, languageCode);
                return tempSwitchMap;
            } catch (IOException e) {
                return null;
            }
        });
    }

    public Map<Integer, String> getKeyFlagMap() throws ServiceEntityInstallationException {
        if (this.keyFlagMap == null) {
            try {
                String path = this.getClass().getResource("").getPath();
                this.keyFlagMap = serviceDropdownListHelper
                        .getDropDownMap(path + PROPERTIES_RESOURCE);
            } catch (IOException e) {
                throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
                        e.getMessage());
            }
        }
        return this.keyFlagMap;
    }

    public Map<Integer, String> getSearchKeyFlagMap() throws ServiceEntityInstallationException {
        Map<Integer, String> keyFlagMap = getKeyFlagMap();
        keyFlagMap.put(0, ServiceEntityStringHelper.EMPTYSTRING);
        return keyFlagMap;
    }

    public String getSwitchValue(int key) throws ServiceEntityInstallationException {
        Map<Integer, String> keyFlagMap = getKeyFlagMap();
        return keyFlagMap.get(key);
    }

}

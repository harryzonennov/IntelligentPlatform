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
public class StandardSwitchProxy {

    public static final int SWITCH_ON = 1;

    public static final int SWITCH_OFF = 2;

    public static final int SWITCH_INITIAL = 3;

    public static final int SWITCH_DELETE = 4;

    public static final String PROPERTIES_RESOURCE = "StandardSwitch";

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    protected Map<Integer, String> switchMap;

    protected Map<String, Map<Integer, String>> switchMapLan;

    public Map<Integer, String> getSwitchMap()
            throws ServiceEntityInstallationException {
        if (this.switchMap == null) {
            try {
                String path = this.getClass().getResource("").getPath();
                this.switchMap = serviceDropdownListHelper
                        .getDropDownMap(path + PROPERTIES_RESOURCE);
            } catch (IOException e) {
                throw new ServiceEntityInstallationException(ServiceEntityInstallationException.PARA_SYSTEM_WRONG,
                        e.getMessage());
            }
        }
        return this.switchMap;
    }

    public Map<Integer, String> getSwitchMap(String languageCode)
            throws ServiceEntityInstallationException {
        if (this.switchMapLan == null) {
            this.switchMapLan = new HashMap<>();
        }
        return ServiceLanHelper.initDefaultLanguageMap(languageCode, this.switchMapLan, lanCode -> {
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

    public Map<Integer, String> getSimpleSwitchMap(String languageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> switchMap = getSwitchMap(languageCode);
        Map<Integer, String> resultMap = new HashMap<>();
        resultMap.put(SWITCH_ON, switchMap.get(SWITCH_ON));
        resultMap.put(SWITCH_OFF, switchMap.get(SWITCH_OFF));
        return resultMap;
    }

    public Map<Integer, String> getSimpleSwitchMap() throws ServiceEntityInstallationException {
        Map<Integer, String> switchMap = getSwitchMap();
        Map<Integer, String> resultMap = new HashMap<>();
        resultMap.put(SWITCH_ON, switchMap.get(SWITCH_ON));
        resultMap.put(SWITCH_OFF, switchMap.get(SWITCH_OFF));
        return resultMap;
    }

    public Map<Integer, String> getSearchSwitchMap() throws ServiceEntityInstallationException {
        Map<Integer, String> switchMap = getSwitchMap();
        switchMap.put(0, ServiceEntityStringHelper.EMPTYSTRING);
        return switchMap;
    }

    public String getSwitchValue(int key) throws ServiceEntityInstallationException {
        Map<Integer, String> switchMap = getSwitchMap();
        return switchMap.get(key);
    }

    public String getSwitchValue(int key, String lanuageCode) throws ServiceEntityInstallationException {
        Map<Integer, String> switchMap = getSwitchMap(lanuageCode);
        return switchMap.get(key);
    }

}
